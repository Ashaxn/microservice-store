package controller;

import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.ProductReservers;
import service.ProductReserverService;

/*
 *default Port : 8180 
 *http://localhost:8180/ProductService/api/v2/productreservers/*
*/
@Path("/productreservers") 
public class ProductReserverController {
	
	private ProductReservers productReserver;
	private ProductReserverService productreserversService = new ProductReserverService();
	
	@POST
	@Path("/addreserver")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReserver(HashMap<String, ?> reserverData) {

		Long ownerIdTemp = new Long((long) reserverData.get("id"));
		int ownerId = ownerIdTemp.intValue();
		
		Long reserverIdTemp = new Long((long) reserverData.get("reserver_id"));
		int reserver = reserverIdTemp.intValue();
		
		productReserver = new ProductReservers(ownerId, reserver);
		
		return productreserversService.addReserver(productReserver);
	}
	
	@GET
	@Path("/getproductwithreserver/{productid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductWithReserver(@PathParam("productid") Integer productid) {
		return productreserversService.getProductWithReserver(productid);
	}
	
	@GET
	@Path("/getallwithreserver")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProductWithReservers() {
		return productreserversService.getAllProductWithReservers();
	}
	
	
	@GET
	@Path("/getproductwithbuyer/{productid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductWithBuyer(@PathParam("productid") Integer productid) {
		return productreserversService.getProductWithBuyer(productid);
	}
}
