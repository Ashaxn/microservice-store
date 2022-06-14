package controller;

import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Reserver;
import service.ReserverService;

/*
 *default Port : 8282 
 *http://localhost:8282/ReserverService/api/v2/reserver/*
*/
@Path("/reserver") 
public class ReserverController {
	
	private Reserver reserver;
	private ReserverService reserverService = new ReserverService();
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(HashMap<String, ?> reserverData) {
		String userName = (String) reserverData.get("userName");
		String password = (String) reserverData.get("password");
		String email = (String) reserverData.get("email");
		Reserver reserver  = new Reserver(userName, password, email);
		return reserverService.register(reserver);
	}
	
	@GET
	@Path("/getreservers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllreservers() {
		return reserverService.getAllreservers();
	}
	
	@GET
	@Path("/getreserverbyid/{reserveridId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReserverById(@PathParam("reserveridId") Integer reserveridId) {
		return reserverService.getReserverById(reserveridId);
	}
	
	
	@PUT
	@Path("/update/{reserveridId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBuyer(HashMap<String, ?> reserverData, @PathParam("reserveridId") Integer reserveridId) {
		String userName = (String) reserverData.get("userName");
		String password = (String) reserverData.get("password");
		String email = (String) reserverData.get("email");
		reserver = new Reserver(userName, password, email);
		reserver.setId(reserveridId);
		return reserverService.updateReserver(reserver);
	}
	
	@DELETE
	@Path("/deletebyid/{reserveridId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("reserveridId") Integer reserveridId) {
		return reserverService.deleteReserver(reserveridId);
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(HashMap<String, ?> Data) {
		String email = (String) Data.get("email");
		String password = (String) Data.get("password");
		return reserverService.login(email, password);
	}
	
	@POST
	@Path("/reservertokenvertify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserverTokenVertify(HashMap<String, ?> Data) {
		String token = (String) Data.get("token");
		String email = (String) Data.get("email");
		return reserverService.vertify(email, token);
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(HashMap<String, ?> Data) {
		String email = (String) Data.get("email");
		String token = (String) Data.get("token");
		return reserverService.logout(email, token);
	}
	
}
