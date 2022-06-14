package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import model.Product;
import model.ProductReservers;
import repository.DBConnection;

public class ProductReserverService {
  private DBConnection connection = new DBConnection();

  public Response addReserver(ProductReservers productReserver) {
    try {
      Connection con = connection.getConnection();
      if (con == null) return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("DataBase connectivity Error")
        .build();

      String query = "INSERT INTO reservers(id,reserver_id) VALUES (?, ?)";
      PreparedStatement preparedStmt = con.prepareStatement(query);

      preparedStmt.setInt(1, productReserver.getId());
      preparedStmt.setInt(2, productReserver.getReserverId());

      preparedStmt.execute();
      con.close();

    } catch (Exception e) {
      return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(e)
        .build();
    }
    return Response
      .status(Response.Status.CREATED)
      .entity(productReserver)
      .build();
  }

  public Response getProductWithReserver(Integer productid) {
    Map < String, Object > res = new HashMap < String, Object > ();
    List < Object > reservers = new ArrayList < > ();
    Product product = null;

    try {
      Connection con = connection.getConnection();
      if (con == null) return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("DataBase connectivity Error")
        .build();

      String queryForGetProductData = "SELECT * FROM product WHERE id = " + productid;
      Statement statement = con.createStatement();
      ResultSet result = statement.executeQuery(queryForGetProductData);

      while (result.next()) {
        int id = result.getInt("id");
        String product_name = result.getString("product_name");
        double product_price = result.getDouble("product_price");
        int owner_id = result.getInt("owner_id");
        String created_at = result.getString("created_at");
        String updated_at = result.getString("updated_at");
        boolean is_completed = result.getBoolean("is_completed");
        int category_id = result.getInt("category_id");
        product = new Product(product_name, product_price, owner_id, is_completed, category_id);
        product.setCreated_at(created_at);
        product.setId(id);
        product.setUpdated_at(updated_at);
      }

      String query = "SELECT reservers.id, reservers.reserver_id FROM reservers WHERE reservers.id = " + productid;
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("id");
        int reserver_id = rs.getInt("reserver_id");

        try {

          Client client = Client.create();

          WebResource webResource = client
            .resource("http://localhost:8282/ReserverService/api/v2/reserver/getreserverbyid/" + id);

          ClientResponse response = webResource.accept("application/json")
            .get(ClientResponse.class);

          if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " +
              response.getStatus());
          }

          Object output = response.getEntity(Object.class);

          reservers.add(output);

        } catch (Exception e) {

          e.printStackTrace();

        }
      }

      res.put("product", product);
      res.put("reservers", reservers);
      con.close();

    } catch (Exception e) {
      return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(e)
        .build();
    }

    return Response
      .status(Response.Status.OK)
      .entity(res)
      .build();

  }

	public Response getAllProductWithReservers() {
		 List<Map < String, Object >> res = new ArrayList <Map < String, Object >> ();
		
		 
		 try {
		      Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();

		      String queryForGetProductData = "SELECT * FROM product";
		      Statement statement = con.createStatement();
		      ResultSet result = statement.executeQuery(queryForGetProductData);

		      while (result.next()) {
		        int id = result.getInt("id");
		        String product_name = result.getString("product_name");
		        double product_price = result.getDouble("product_price");
		        int owner_id = result.getInt("owner_id");
		        String created_at = result.getString("created_at");
		        String updated_at = result.getString("updated_at");
		        boolean is_completed = result.getBoolean("is_completed");
		        int category_id = result.getInt("category_id");
		        Product product = new Product(product_name, product_price, owner_id, is_completed, category_id);
		        product.setCreated_at(created_at);
		        product.setId(id);
		        product.setUpdated_at(updated_at);
		        Map < String, Object > data = new HashMap<>();
		          
		        data.put("product", product);
		        
		        
		        String queryNext = "SELECT reservers.id, reservers.reserver_id FROM reservers WHERE reservers.id = " + id;
			      Statement stmtNExt = con.createStatement();
			      ResultSet rset = stmtNExt.executeQuery(queryNext);

			      List<Object> reserver = new ArrayList<>();
			      
			      while (rset.next()) {
			        int tid = rset.getInt("id");
			        int reserver_id = rset.getInt("reserver_id");

			        try {

			          Client client = Client.create();

			          WebResource webResource = client
			            .resource("http://localhost:8282/ReserverService/api/v2/reserver/getreserverbyid/" + reserver_id);

			          ClientResponse response = webResource.accept("application/json")
			            .get(ClientResponse.class);

			          if (response.getStatus() != 200) {
			            throw new RuntimeException("Failed : HTTP error code : " +
			              response.getStatus());
			          }

			          Object output = response.getEntity(Object.class);
			          reserver.add(output);

			        } catch (Exception e) {

			          e.printStackTrace();

			        }
			        
			        data.put("reservers", reserver);
			        res.add(data);
			      }

		      }
 
		      con.close();
		    } catch (Exception e) {
		      return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity(e)
		        .build();
		    }

		    return Response
		      .status(Response.Status.OK)
		      .entity(res)
		      .build();
	}

	public Response getProductWithBuyer(Integer productid) {
		Map < String, Object > res = new HashMap < String, Object > ();
	    Product product = null;
	    int buyerId = -99;

	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String queryForGetProductData = "SELECT * FROM product WHERE id = " + productid;
	      Statement statement = con.createStatement();
	      ResultSet result = statement.executeQuery(queryForGetProductData);

	      while (result.next()) {
	        int id = result.getInt("id");
	        String product_name = result.getString("product_name");
	        double product_price = result.getDouble("product_price");
	        int owner_id = result.getInt("owner_id");
	        String created_at = result.getString("created_at");
	        String updated_at = result.getString("updated_at");
	        boolean is_completed = result.getBoolean("is_completed");
	        int category_id = result.getInt("category_id");
	        buyerId = result.getInt("buyer_id");
	        product = new Product(product_name, product_price, owner_id, is_completed, category_id);
	        product.setCreated_at(created_at);
	        
	        product.setId(id);
	        product.setUpdated_at(updated_at);
	      }

	      Object output = null;
		
		        Client client = Client.create();

		        WebResource webResource = client
		          .resource("http://localhost:8080/BuyerService/api/v2/buyer/getbuyerbyid"+buyerId);

		        ClientResponse response = webResource.accept("application/json")
		          .get(ClientResponse.class);

		        if (response.getStatus() != 200) {
		          throw new RuntimeException("Failed : HTTP error code : " +
		            response.getStatus());
		        }
		        output = response.getEntity(Object.class);

		
				
	      res.put("product", product);
	      res.put("buyer", output);
	      con.close();

	    } catch (Exception e) {
	      return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity(e)
	        .build();
	    }

	    return Response
	      .status(Response.Status.OK)
	      .entity(res)
	      .build();
	}

}