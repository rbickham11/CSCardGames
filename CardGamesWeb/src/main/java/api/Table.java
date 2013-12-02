
package api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.sun.jersey.spi.resource.Singleton;

import cardgameslib.games.poker.holdem.*;
import json.*;

@Singleton
@Path("/table")
public class Table {
	private HoldemDealer dealer;
	
	public Table() {
		dealer = new HoldemDealer(20000, 200);
	}
	
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public BigBlind getBigBlind() {
    	BigBlind bb = new BigBlind();
    	bb.setBigBlind(Integer.toString(dealer.getBigBlind()));
        return bb;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBigBlind(BigBlind bigBlind) {
    	dealer.setBigBlind(Integer.parseInt(bigBlind.getBigBlind()));
    	return Response.status(200).build();
    }
}
