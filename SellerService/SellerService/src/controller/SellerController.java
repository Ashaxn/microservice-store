package controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Seller;
import service.SellerService;

/*
 *default Port : 8090 
 *http://localhost:8090/SellerService/api/v2/seller/*
*/
@Path("/seller") 
public class SellerController {
	private SellerService sellerService = new SellerService();
	
	@GET
	@Path("/getsellers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSellers() throws SQLException {
		return sellerService.getAllSellers();
	}
	
	@GET
	@Path("/getseller/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerById(@PathParam("sellerid") Integer sellerid) throws SQLException {
		return sellerService.getSellerById(sellerid);
	}
	
	@DELETE
	@Path("/deletebyid/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("sellerid") Integer sellerid) throws SQLException {
		return sellerService.deleteById(sellerid);
	}
	
	@PUT
	@Path("/update/{sellerid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSeller(HashMap<String, ?> sellerData, @PathParam("sellerid") Integer sellerid) throws SQLException {
		return sellerService.updateSeller(sellerData, sellerid);
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSeller(HashMap<String, ?> sellerData) throws SQLException {
		return sellerService.addSeller(sellerData);
	}
	
	@GET
	@Path("/getsellercat/{sellerid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerWithCat(@PathParam("sellerid") Integer sellerid) throws SQLException {
		return sellerService.getSellerWithCat(sellerid);
	}

	
	@GET
	@Path("/getsellercat/all")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSellerWithCatAll() throws SQLException {
		return sellerService.getSellerWithCatAll();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(HashMap<String, ?> Data) throws SQLException {
		String email = (String) Data.get("email");
		String password = (String) Data.get("password");
		return sellerService.login(email, password);
	}
	
	@POST
	@Path("/sellertokenvertify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sellerTokenVertify(HashMap<String, ?> Data) throws SQLException {
		String token = (String) Data.get("token");
		String email = (String) Data.get("email");
		return sellerService.vertify(email, token);
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(HashMap<String, ?> Data) throws SQLException {
		String email = (String) Data.get("email");
		String token = (String) Data.get("token");
		return sellerService.logout(email, token);
	}
}
