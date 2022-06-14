package auth;

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
 *default Port : 8082 
 *http://localhost:8082/AuthenticationService/api/v2/auth/*
*/
@Path("/auth") 
public class Auth {
	
	@POST
	@Path("/buyerlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buyerLogin(HashMap<String, ?> buyerData) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8080/BuyerService/api/v2/buyer/login");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, buyerData);

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
	@Path("/buyertokenvertify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buyerTokenVertify(@Context HttpHeaders httpheaders) {
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map < String, Object > res = new HashMap < String, Object > ();
		
		res.put("token", token.get(0));
		res.put("email", email.get(0));

		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8080/BuyerService/api/v2/buyer/buyertokenvertify");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, res);

	        output = response.getEntity(Object.class);
	        
	        if (response.getStatus() == 403) {
	        	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }else if(response.getStatus() == 200){
		    	return Response
	    		        .status(Response.Status.OK)
	    		        .entity("authorized")
	    		        .build();
		    }else {
		    	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("unauthorized access")
	    		        .build();
	      }	
	}
	
	@POST
	@Path("/buyerlogout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buyerLogout(HashMap<String, ?> buyerData) {
		Object output = null;
		try {
	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8080/BuyerService/api/v2/buyer/logout");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, buyerData);

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
	@Path("/sellerlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sellerLogin(HashMap<String, ?> sellerData) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/login");

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
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	@POST
	@Path("/sellertokenvertify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sellerTokenVertify(@Context HttpHeaders httpheaders) {
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map < String, Object > res = new HashMap < String, Object > ();
		
		res.put("token", token.get(0));
		res.put("email", email.get(0));

		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/sellertokenvertify");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, res);

	        output = response.getEntity(Object.class);
	        
	        if (response.getStatus() == 403) {
	        	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }else if(response.getStatus() == 200){
		    	return Response
	    		        .status(Response.Status.OK)
	    		        .entity("authorized")
	    		        .build();
		    }else {
		    	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("unauthorized access")
	    		        .build();
	      }	
	}
	
	@POST
	@Path("/sellerlogout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sellerLogout(HashMap<String, ?> sellerData) {
		Object output = null;
		try {
	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8090/SellerService/api/v2/seller/logout");

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
			        .status(Response.Status.OK)
			        .entity(output)
			        .build();
	}
	
	@POST
	@Path("/reserverlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserverLogin(HashMap<String, ?> Data) {
		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8282/ReserverService/api/v2/reserver/login");

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
	@Path("/reservertokenvertify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserverTokenVertify(@Context HttpHeaders httpheaders) {
		List<String> token = httpheaders.getRequestHeader("token");
		List<String> email = httpheaders.getRequestHeader("email");
		Map < String, Object > res = new HashMap < String, Object > ();
		
		res.put("token", token.get(0));
		res.put("email", email.get(0));

		Object output = null;
		try {

	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8282/ReserverService/api/v2/reserver/reservertokenvertify");

	        ClientResponse response = webResource.accept("application/json")
	          .post(ClientResponse.class, res);

	        output = response.getEntity(Object.class);
	        
	        if (response.getStatus() == 403) {
	        	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }else if(response.getStatus() == 200){
		    	return Response
	    		        .status(Response.Status.OK)
	    		        .entity("authorized")
	    		        .build();
		    }else {
		    	return Response
				        .status(Response.Status.FORBIDDEN)
				        .entity("unauthorized access")
				        .build();
		    }

	      } catch (Exception e) {
	    	  return Response
	    		        .status(Response.Status.FORBIDDEN)
	    		        .entity("unauthorized access")
	    		        .build();
	      }	
	}
	
	@POST
	@Path("/reserverlogout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserverLogout(HashMap<String, ?> Data) {
		Object output = null;
		try {
	        Client client = Client.create();

	        WebResource webResource = client
	          .resource("http://localhost:8282/ReserverService/api/v2/reserver/logout");

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
	

	
}
