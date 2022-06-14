package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import model.Category;
import model.Seller;
import repository.DBConnection;

public class SellerService {
	
	private DBConnection connection = new DBConnection();

	public Response addSeller(HashMap<String, ?> sellerData) throws SQLException {
		Seller seller = new Seller();
		seller.setName((String) sellerData.get("name"));
		seller.setEmail((String) sellerData.get("email"));
		seller.setPassword((String) sellerData.get("password"));
		seller.setSellerCategory((new Long((long) sellerData.get("sellerCategory"))).intValue());
		seller.setCreatedAt("Just now");
		seller.setUpdatedAt("Just yet updated");
		seller.setToken("not logged in");
		seller.setId(1);
		

		      Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();

		      String query = "INSERT INTO seller(name,email,password,sellerCategory) VALUES (?, ?, ?, ?)";
		      PreparedStatement preparedStmt = con.prepareStatement(query);

		      preparedStmt.setString(1, (String) sellerData.get("name"));
		      preparedStmt.setString(2, (String) sellerData.get("email"));
		      preparedStmt.setString(3, (String) sellerData.get("password"));
		      preparedStmt.setInt(4, (new Long((long) sellerData.get("sellerCategory")).intValue()));


		      preparedStmt.execute();
		      con.close();

		   
		    return Response
		      .status(Response.Status.CREATED)
		      .entity(seller)
		      .build();
	}

	public Response getAllSellers() throws SQLException {
		List <Seller> sellers = new ArrayList<Seller> ();


	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	        int id = rs.getInt("id");
	        String name = rs.getString("name");   
	        String email = rs.getString("email");
	        String password = rs.getString("password");
	        String createdAt = rs.getString("createdAt");
	        String updatedAt = rs.getString("updatedAt");
	        int sellerCategory = rs.getInt("sellerCategory");
	        
	        Seller seller = new Seller(name, email, password, sellerCategory);
	        seller.setId(id);
	        seller.setCreatedAt(createdAt);
	        seller.setUpdatedAt(updatedAt);
	        
	        sellers.add(seller);
	      }
	      con.close();

	

	    return Response
	      .status(Response.Status.OK)
	      .entity(sellers)
	      .build();
	}

	public Response getSellerById(Integer sellerid) throws SQLException {
		Seller seller = null;

	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller where id = " + sellerid;
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	    	  	int id = rs.getInt("id");
		        String name = rs.getString("name");   
		        String email = rs.getString("email");
		        String password = rs.getString("password");
		        String createdAt = rs.getString("createdAt");
		        String updatedAt = rs.getString("updatedAt");
		        int sellerCategory = rs.getInt("sellerCategory");
		        
		        seller = new Seller(name, email, password, sellerCategory);
		        seller.setId(id);
		        seller.setCreatedAt(createdAt);
		        seller.setUpdatedAt(updatedAt);
		        seller.setId(id);

	      }
	      con.close();

	

	    return Response
	      .status(Response.Status.OK)
	      .entity(seller)
	      .build();
	}

	public Response deleteById(Integer sellerid) throws SQLException {
	
		      Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();

		      String query = "DELETE from seller WHERE id=?";
		      PreparedStatement preparedStmt = con.prepareStatement(query);

		      preparedStmt.setInt(1, sellerid);

		      preparedStmt.execute();
		      con.close();

		

		    return Response
		      .status(Response.Status.OK)
		      .entity("Succesfully Deleted the Seller data")
		      .build();
	}

	public Response getSellerWithCat(Integer sellerid) throws SQLException {
		Seller seller = null;
		Category category = null;
		Map < String, Object > res = new HashMap < String, Object > ();
		int sellerCategory = -99;

	
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller where id = " + sellerid;
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	    	  	int id = rs.getInt("id");
		        String name = rs.getString("name");   
		        String email = rs.getString("email");
		        String password = rs.getString("password");
		        String createdAt = rs.getString("createdAt");
		        String updatedAt = rs.getString("updatedAt");
		        sellerCategory = rs.getInt("sellerCategory");
		        
		        seller = new Seller(name, email, password, sellerCategory);
		        seller.setId(id);
		        seller.setCreatedAt(createdAt);
		        seller.setUpdatedAt(updatedAt);
		        seller.setId(id);

		        res.put("seller", seller);
	      }
	      
	      
	      String queryForGetCategory = "select * from category where id = " + sellerCategory;
	      Statement stmtForCategory = con.createStatement();
	      ResultSet resultSet = stmtForCategory.executeQuery(queryForGetCategory);

	      while (resultSet.next()) {
	    	  	int id = resultSet.getInt("id");
		        String categoryName = resultSet.getString("categoryName");   
		        String description = resultSet.getString("description");
		        
		        category = new Category(categoryName, description);
		        category.setId(id);
		        category.setId(id);

	      }
	      
	      res.put("category", category);
	      con.close();

	 

	    return Response
	      .status(Response.Status.OK)
	      .entity(res)
	      .build();
	}

	public Response getSellerWithCatAll() throws SQLException {
		Seller seller = null;
		Category category = null;
		List<Map < String, Object >> finalResul = new ArrayList<>();
		
		Map < String, Object > res = new HashMap < String, Object > ();
		int sellerCategory = -99;

	
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	    	  	int id = rs.getInt("id");
		        String name = rs.getString("name");   
		        String email = rs.getString("email");
		        String password = rs.getString("password");
		        String createdAt = rs.getString("createdAt");
		        String updatedAt = rs.getString("updatedAt");
		        sellerCategory = rs.getInt("sellerCategory");
		        
		        seller = new Seller(name, email, password, sellerCategory);
		        seller.setId(id);
		        seller.setCreatedAt(createdAt);
		        seller.setUpdatedAt(updatedAt);
		        seller.setId(id);

		        res.put("seller", seller);
		        
		        String queryForGetCategory = "select * from category where id = " + sellerCategory;
			      Statement stmtForCategory = con.createStatement();
			      ResultSet resultSet = stmtForCategory.executeQuery(queryForGetCategory);

			      while (resultSet.next()) {
			    	  	int idT = resultSet.getInt("id");
				        String categoryName = resultSet.getString("categoryName");   
				        String description = resultSet.getString("description");
				        
				        category = new Category(categoryName, description);
				        category.setId(idT);
				        
			      }
			      
			      res.put("category", category);
			      finalResul.add(res);
	      }
	      
	      
	      
	      con.close();

	    return Response
	      .status(Response.Status.OK)
	      .entity(finalResul)
	      .build();
	}

	public Response updateSeller(HashMap<String, ?> sellerData, Integer sellerid) throws SQLException {
			  Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();
		  
		  String query = "UPDATE seller SET name=?,email=?,password=?,updatedAt=CURRENT_TIMESTAMP,sellerCategory=? WHERE id=?";
		  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		  preparedStmt.setString(1, (String) sellerData.get("name"));
		  preparedStmt.setString(2, (String) sellerData.get("email"));
		  preparedStmt.setString(3, (String) sellerData.get("password"));
		  preparedStmt.setInt(4, (new Long((long) sellerData.get("sellerCategory"))).intValue());
		  preparedStmt.setInt(5, sellerid);
		  
		  preparedStmt.execute();
		  con.close();

		  
		  return Response
			      .status(Response.Status.CREATED)
			      .entity("Seller updated")
			      .build();
	}

	public Response login(String email, String password) throws SQLException {
		String currentPassword = "";
	    
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller where email = '"+ email +"'";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	         currentPassword = rs.getString("password");
	      }
 
	      
	      if(!currentPassword.equals(password)) {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("Invalid credetials")
	    		        .build();
	      }else {
	    	  Random rand = new Random();
	    	  double int_random = rand.nextDouble() * 6543793F; 
	    	  
	    	  String createToken = "UPDATE seller SET token = ?,updatedAt=CURRENT_TIMESTAMP WHERE email=?";
	    	  PreparedStatement preparedStmt = con.prepareStatement(createToken);
	    	 
	    	  preparedStmt.setString(1, String.valueOf(int_random));
	    	  preparedStmt.setString(2, email);

	    	  preparedStmt.execute();
	    	  con.close();
	    	  
	    	  Map<String, String> tokenResult = new HashMap<String, String>(); 
	    	  tokenResult.put("token",  String.valueOf(int_random)+email);
	    	  tokenResult.put("metadata",  "Add email and token in seperate headers when making requests.");
	    	  
	    	  return Response
	    		        .status(Response.Status.OK)
	    		        .entity(tokenResult)
	    		        .build(); 
	      }
	     
	}

	public Response vertify(String email, String token) throws SQLException {
		String tokenFromDB = "";
	    
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.FORBIDDEN)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller where email = '"+ email +"'";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	    	  tokenFromDB = rs.getString("token");
	      }
 
	      if(tokenFromDB.equals(token)) {
	    	  return Response
	    		        .status(Response.Status.OK)
	    		        .entity("authenticated")
	    		        .build(); 
	      }else {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("Invalid token")
	    		        .build();
	      }

	}

	public Response logout(String email, String token) throws SQLException {
		  String currentToken = "";
	    
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from seller where email = '"+ email +"'";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	         currentToken = rs.getString("token");
	      }
 
	      
	      if(!currentToken.equals(token)) {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("Invalid credetials")
	    		        .build();
	      }else {	  
	    	  String createToken = "UPDATE seller SET token = ?,updatedAt=CURRENT_TIMESTAMP WHERE email=?";
	    	  PreparedStatement preparedStmt = con.prepareStatement(createToken);
	    	 
	    	  preparedStmt.setString(1, null);
	    	  preparedStmt.setString(2, email);

	    	  preparedStmt.execute();
	    	  con.close();
	    	  
	    	  Map<String, String> tokenResult = new HashMap<String, String>(); 
	    	  tokenResult.put("status",  "logout succesfully");
	    	  
	    	  return Response
	    		        .status(Response.Status.OK)
	    		        .entity(tokenResult)
	    		        .build(); 
	      }
	}

}
