package seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/*
 *default Port : 8682 
 *http://localhost:8682/APIGateway/api/v2/seller/*
*/
@Path("/seller") 
public class Seller {
	
	public boolean validate(String email, String token) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8082/AuthenticationService/api/v2/auth/sellertokenvertify");

	        ClientResponse response = webResource.accept("application/json")
	        	.header("token", token)
	        	.header("email", email)
	            .post(ClientResponse.class);

	        output = response.getEntity(Object.class);
	        
	        if (response.getStatus() == 403) {
	        	return false;
		    }else if(response.getStatus() == 200){
		    	return true;
		    }else {
		    	return false;
		    }

	      } catch (Exception e) {
	    	  return false;
	      }
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(HashMap<String, ?> Data) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8082/AuthenticationService/api/v2/auth/sellerlogin");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, Data);

	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpHeaders httpheaders) {
		Object output = null;
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map<String, Object> Data = new HashMap<String, Object>();

		if(token == null || email == null) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("No token provided")
    		        .build();
		}
		boolean isValid = validate(email.get(0),token.get(0));
		Data.put("email", email.get(0));
		Data.put("token", token.get(0));
		
		if(!isValid) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("Unauthrized access")
    		        .build();
		}
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8082/AuthenticationService/api/v2/auth/sellerlogout");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, Data);

	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}

	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSeller(HashMap<String, ?> sellerData) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/register");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, sellerData);

	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.CREATED)
			        .entity(output)
			        .build();
	}
	
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellers(@Context HttpHeaders httpheaders) {
		Object output = null;
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map<String, Object> Data = new HashMap<String, Object>();

		if(token == null || email == null) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("No token provided")
    		        .build();
		}
		boolean isValid = validate(email.get(0),token.get(0));
		Data.put("email", email.get(0));
		Data.put("token", token.get(0));
		
		if(!isValid) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("Unauthrized access")
    		        .build();
		}

		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/getsellers");

	        ClientResponse response = webResource.accept("application/json")
	          .get(ClientResponse.class);

	        if (response.getStatus() != 200) {
	          throw new RuntimeException("Failed : HTTP error code : " +
	            response.getStatus());
	        }
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	@GET
	@Path("/getseller/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerById(@PathParam("sellerid") Integer sellerid, @Context HttpHeaders httpheaders) {
		Object output = null;
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map<String, Object> Data = new HashMap<String, Object>();

		if(token == null || email == null) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("No token provided")
    		        .build();
		}
		boolean isValid = validate(email.get(0),token.get(0));
		Data.put("email", email.get(0));
		Data.put("token", token.get(0));
		
		if(!isValid) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("Unauthrized access")
    		        .build();
		}
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/getseller/"+sellerid);

	        ClientResponse response = webResource.accept("application/json")
	          .get(ClientResponse.class);

	        if (response.getStatus() != 200) {
	          throw new RuntimeException("Failed : HTTP error code : " +
	            response.getStatus());
	        }
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	
	@DELETE
	@Path("/deletebyid/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("sellerid") Integer sellerid, @Context HttpHeaders httpheaders) {
		Object output = null;
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map<String, Object> Data = new HashMap<String, Object>();

		if(token == null || email == null) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("No token provided")
    		        .build();
		}
		boolean isValid = validate(email.get(0),token.get(0));
		Data.put("email", email.get(0));
		Data.put("token", token.get(0));
		
		if(!isValid) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("Unauthrized access")
    		        .build();
		}

		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/deletebyid/"+sellerid);

	        ClientResponse response = webResource.accept("application/json")
	          .delete(ClientResponse.class);

	        if (response.getStatus() != 200) {
	          throw new RuntimeException("Failed : HTTP error code : " +
	            response.getStatus());
	        }
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	
	@GET
	@Path("/getsellercat/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerWithCat(@PathParam("sellerid") Integer sellerid) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/getsellercat/"+sellerid);

	        ClientResponse response = webResource.accept("application/json")
	          .get(ClientResponse.class);

	        if (response.getStatus() != 200) {
	          throw new RuntimeException("Failed : HTTP error code : " +
	            response.getStatus());
	        }
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}

	
	@GET
	@Path("/getsellercat/all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerWithCatAll() {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/getsellercat/all");

	        ClientResponse response = webResource.accept("application/json")
	          .get(ClientResponse.class);

	        if (response.getStatus() != 200) {
	          throw new RuntimeException("Failed : HTTP error code : " +
	            response.getStatus());
	        }
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	
	@PUT
	@Path("/update/{sellerid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSeller(HashMap<String, ?> sellerData, @PathParam("sellerid") Integer sellerid, @Context HttpHeaders httpheaders) {
		Object output = null;
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map<String, Object> Data = new HashMap<String, Object>();

		if(token == null || email == null) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("No token provided")
    		        .build();
		}
		boolean isValid = validate(email.get(0),token.get(0));
		Data.put("email", email.get(0));
		Data.put("token", token.get(0));
		
		if(!isValid) {
			return Response
    		        .status(Response.Status.FORBIDDEN)
    		        .entity("Unauthrized access")
    		        .build();
		}

		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/update/"+sellerid);

	        ClientResponse response = webResource.accept("application/json")
	          .put(ClientResponse.class, sellerData);

	       
	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	@POST
	@Path("/addproduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(HashMap<String, ?> productData) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8180/ProductService/api/v2/product/addproduct");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, productData);

	        output = response.getEntity(Object.class);

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.INTERNAL_SERVER_ERROR)
	    		        .entity(e)
	    		        .build();
	      }
			return Response
			        .status(Response.Status.CREATED)
			        .entity(output)
			        .build();
	}
	
}
