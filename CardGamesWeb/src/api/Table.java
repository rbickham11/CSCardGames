
package api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

import com.sun.jersey.spi.resource.Singleton;

import cardgameslib.games.poker.holdem.*;
import cardgameslib.utilities.*;
import json.*;

@Singleton
@Path("/holdemtable")
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
    
    @Path("/starthand")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerModel> startHand() {
    	List<PlayerModel> jsonPlayers = new ArrayList<>();
    	dealer.addPlayer(1001, 1, 20000);
    	dealer.addPlayer(1002, 3, 20000);
    	dealer.addPlayer(1008, 8, 20000);
    	dealer.startHand();
    	for(BettingPlayer player : dealer.getActivePlayers()) {
    		jsonPlayers.add(new PlayerModel(player.getUserId(), player.getSeatNumber(), player.getHand()));
    	}
    	return jsonPlayers;
    }
}
