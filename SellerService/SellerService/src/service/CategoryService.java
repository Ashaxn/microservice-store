package service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import model.Category;
import repository.DBConnection;
import service.CategoryService;


public class CategoryService {

	private DBConnection connection = new DBConnection();
	
	public Response addCategory(HashMap<String, ?> categoryData) {
		try {
		      Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();

		      String query = "INSERT INTO category(categoryName,description) VALUES (?, ?)";
		      PreparedStatement preparedStmt = con.prepareStatement(query);

		      preparedStmt.setString(1, (String) categoryData.get("categoryName"));
		      preparedStmt.setString(2, (String) categoryData.get("description"));


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
		      .entity("Seller category created")
		      .build();
	}

	public Response getAllCategories() {
		List <Category> categories = new ArrayList<Category> ();

	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from category";
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	        int id = rs.getInt("id");
	        String categoryName = rs.getString("categoryName");   
	        String description = rs.getString("description");
	   
	        
	        Category category = new Category(categoryName, description);
	        category.setId(id);
	        categories.add(category);
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
	      .entity(categories)
	      .build();
	}

	public Response getCategoryById(Integer categorytid) {
		Category category = null;

	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from category where id = " + categorytid;
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(query);

	      while (rs.next()) {
	    	  int id = rs.getInt("id");
		        String categoryName = rs.getString("categoryName");   
		        String description = rs.getString("description");
		        category = new Category(categoryName, description);
		        category.setId(id);
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
	      .entity(category)
	      .build();
	}

	public Response deleteCategory(Integer categorytid) {
		try {
		      Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();

		      String query = "DELETE from category WHERE id=?";
		      PreparedStatement preparedStmt = con.prepareStatement(query);

		      preparedStmt.setInt(1, categorytid);

		      preparedStmt.execute();
		      con.close();

		    } catch (Exception e) {
		      return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity(e)
		        .build();
		    }

		    return Response
		      .status(Response.Status.OK)
		      .entity("Succesfully Deleted the category data")
		      .build();
	}

	public Response updateCategory(HashMap<String, ?> categoryData, Integer categoryid) {    
		try
		  {
			  Connection con = connection.getConnection();
		      if (con == null) return Response
		        .status(Response.Status.INTERNAL_SERVER_ERROR)
		        .entity("DataBase connectivity Error")
		        .build();
		  
		  String query = "UPDATE category SET categoryName=?,description=? WHERE id=?";
		  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		  preparedStmt.setString(1, (String) categoryData.get("categoryName"));
		  preparedStmt.setString(2, (String) categoryData.get("description"));
		  preparedStmt.setInt(3, categoryid);
		  
		  preparedStmt.execute();
		  con.close();
		  }
		  catch (Exception e)
		  {
			  return Response
				        .status(Response.Status.INTERNAL_SERVER_ERROR)
				        .entity("Error while updating the category")
				        .build();
		  }
		  
		  return Response
			      .status(Response.Status.CREATED)
			      .entity("Seller category updated")
			      .build();
	}
	
	

	
}
