package controller;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Review;
import service.ReviewService;

/*
 *default Port : 8090 
 *http://localhost:8090/SellerService/api/v2/review/*
*/
@Path("/review")
public class ReviewController {
	private Review review;
	private ReviewService reviewService = new ReviewService();
	
	@POST
	@Path("/sumbit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReview(HashMap<String, ?> reviewData) {
		String title = (String) reviewData.get("title");
		String details = (String) reviewData.get("details");
		Long sellerIdTemp = new Long((long) reviewData.get("sellerId"));
		int sellerId = sellerIdTemp.intValue();	
		Long submiterIdTemp = new Long((long) reviewData.get("submiterId"));
		int submiterId = submiterIdTemp.intValue();	
		
		review = new Review(title, details, sellerId, submiterId);
		
		return reviewService.addReview(review);
	}
	
	@GET
	@Path("/getreviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReviews() {
		return reviewService.getAllReviews();
	}
	
	@GET
	@Path("/getreview/{reviewid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getreviewById(@PathParam("reviewid") Integer reviewid) {
		return reviewService.getreviewById(reviewid);
	}
	
	
	@DELETE
	@Path("/deletebyid/{reviewid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("reviewid") Integer reviewid) {
		return reviewService.deleteById(reviewid);
	}
	
	
	@GET
	@Path("/getreiewwithdata/{reviewid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviewwithData(@PathParam("reviewid") Integer reviewid) {
		return reviewService.getReviewwithData(reviewid);
	}
	
	
	@GET
	@Path("/getreiewwithdata/user/{sellerid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviewwithDataUser(@PathParam("sellerid") Integer sellerid) {
		return reviewService.getReviewwithDataUser(sellerid);
	}
	
}
