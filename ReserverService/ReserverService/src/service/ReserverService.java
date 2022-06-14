package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import model.Reserver;
import repository.DBConnection;

public class ReserverService {

  private DBConnection connection = new DBConnection();

  public Response register(Reserver reserver) {
    try {
      Connection con = connection.getConnection();
      
      if (con == null) { 
    	  return Response
    			  	.status(Response.Status.INTERNAL_SERVER_ERROR)
    		        .entity("DataBase connectivity Error")
    		        .build();
      }
        

      String query = "INSERT INTO reserver(userName,password, email) VALUES (?, ?, ?)";
      PreparedStatement preparedStmt = con.prepareStatement(query);

      preparedStmt.setString(1, reserver.getUserName());
      preparedStmt.setString(2, reserver.getPassword());
      preparedStmt.setString(3, reserver.getEmail());
      
      preparedStmt.execute();
      con.close();
      
      reserver.setRegiteredAt("A few seconds ago");
    } catch (Exception e) {
      return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("Error while inserting data")
        .build();
    }
    return Response
      .status(Response.Status.CREATED)
      .entity(reserver)
      .build();
  
}

public Response getAllreservers() {
    List <Reserver> reserver = new ArrayList <Reserver> ();

    try {
      Connection con = connection.getConnection();
      if (con == null) return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("DataBase connectivity Error")
        .build();

      String query = "select * from reserver";
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("id");
        String userName = rs.getString("userName");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String regiteredAt = rs.getString("regiteredAt");
        Reserver freserver = new Reserver(userName, password, email);
        freserver.setId(id);
        freserver.setRegiteredAt(regiteredAt);
        reserver.add(freserver);
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
      .entity(reserver)
      .build();

  }

  public Response getReserverById(int reserverId) {
    Reserver reserver = null;
    
    try {
      Connection con = connection.getConnection();
      if (con == null) return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("DataBase connectivity Error")
        .build();

      String query = "select * from reserver where id = " + reserverId;
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("id");
        String userName = rs.getString("userName");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String regiteredAt = rs.getString("regiteredAt");
        String updatedAt = rs.getString("updatedAt");
        reserver = new Reserver(userName, password, email);
        reserver.setId(id);
        reserver.setRegiteredAt(regiteredAt);
        reserver.setUpdatedAt(updatedAt);
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
      .entity(reserver)
      .build();

  }
  
  public Response updateReserver(Reserver reserver) {
	  try
	  {
		  Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();
	  
	  String query = "UPDATE reserver SET userName=?,password=?,email=?,updatedAt=CURRENT_TIMESTAMP WHERE id=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	  preparedStmt.setString(1, reserver.getUserName());
	  preparedStmt.setString(2, reserver.getPassword());
	  preparedStmt.setString(3, reserver.getEmail());
	  preparedStmt.setInt(4, reserver.getId());
	  
	  preparedStmt.execute();
	  con.close();
	  reserver.setUpdatedAt("A few seconds ago");
	  }
	  catch (Exception e)
	  {
		  return Response
			        .status(Response.Status.INTERNAL_SERVER_ERROR)
			        .entity("Error while updating the item")
			        .build();
	  }
	  
	  return Response
		      .status(Response.Status.CREATED)
		      .entity(reserver)
		      .build();
  }
  
  public Response deleteReserver(int userId) {
	  try
	  {
		  Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();
	  
	  String query = "DELETE from reserver WHERE id=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	  preparedStmt.setInt(1, userId);
	  
	  preparedStmt.execute();
	  con.close();

	  }
	  catch (Exception e)
	  {
		  return Response
			        .status(Response.Status.INTERNAL_SERVER_ERROR)
			        .entity("Error while updating the item")
			        .build();
	  }
	  
	  return Response
		      .status(Response.Status.OK)
		      .entity("Succesfully Deleted the reserver")
		      .build();
  }

	public Response login(String email, String password) {
		String currentPassword = "";
	    
	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from reserver where email = '"+ email +"'";
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
	    	  double int_random = rand.nextDouble() * 7532359F; 
	    	  
	    	  String createToken = "UPDATE reserver SET token = ?,updatedAt=CURRENT_TIMESTAMP WHERE email=?";
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
	      
	    } catch (Exception e) {
	      return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity(e)
	        .build();
	   }
	}

	public Response vertify(String email, String token) {
		String tokenFromDB = "";
	    
	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.FORBIDDEN)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from reserver where email = '"+ email +"'";
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

	    } catch (Exception e) {
	      return Response
	        .status(Response.Status.FORBIDDEN)
	        .entity(e)
	        .build();
	   }
	}

	public Response logout(String email, String token) {
		String currentToken = "";
	    
	    try {
	      Connection con = connection.getConnection();
	      if (con == null) return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity("DataBase connectivity Error")
	        .build();

	      String query = "select * from reserver where email = '"+ email +"'";
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
	    	  String createToken = "UPDATE reserver SET token = ?,updatedAt=CURRENT_TIMESTAMP WHERE email=?";
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

	    } catch (Exception e) {
	      return Response
	        .status(Response.Status.INTERNAL_SERVER_ERROR)
	        .entity(e)
	        .build();
	   }
	}
  
  
}