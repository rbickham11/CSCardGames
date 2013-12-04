
package api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.*;

import com.sun.jersey.spi.resource.Singleton;

import cardgameslib.games.poker.betting.Action;
import cardgameslib.games.poker.holdem.*;
import cardgameslib.utilities.*;
import json.*;

@Singleton
@Path("/holdemtable")
public class HoldemTable {
	private HoldemDealer dealer;
	
	public HoldemTable() {
		dealer = new HoldemDealer(20000, 200);
	}
    
    @Path("/addplayer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(SimplePlayer player) {
    	try {
    		dealer.addPlayer(player.getPlayerId(), player.getSeatNumber(), player.getStartingChips());
    	}
    	catch(IllegalArgumentException ex) {
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok().entity("Player added").build();
    }
    
    @Path("/removeplayer/{id}")
    @DELETE    
    public Response removePlayer(@PathParam("id") int id) {
    	try {
    		dealer.removePlayer(id);
    	}
    	catch(IllegalArgumentException ex) {
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok().entity("Player removed").build();
    }
    
    @Path("/starthand")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response startHand() {
    	try {
    		dealer.startHand();
    	}
    	catch(IllegalArgumentException ex) {
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok(dealer.getActivePlayers(), MediaType.APPLICATION_JSON).build();
    }
    
    //The players at the table. Should be used only to check all players on the table for purposes of seating
    @Path("/getallplayers")  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BettingPlayer> getAllPlayers() {
    	return dealer.getPlayers();
    }
    
    //The players currently in a hand.Should be used to retrieve player information during a hand(hand, chip count, current bet, etc)
    @Path("/getactiveplayers") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BettingPlayer> getActivePlayers() {
    	return dealer.getActivePlayers();
    }
    
    @Path("/dealfloptoboard")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Board dealFlop() {
    	Board board = new Board();
    	dealer.dealFlopToBoard();
    	board.setCards(dealer.getBoard());
    	return board;
    }
    
    @Path("/dealcardtoboard")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Board dealCardToBoard() {
    	Board board = new Board();
    	dealer.dealCardToBoard();
    	board.setCards(dealer.getBoard());
    	return board;
    }
    
    @Path("/getaction")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActionStatus(ActionStatusRequest request) {
    	ActionStatusResponse response = new ActionStatusResponse();
    	boolean changed = false;
    	try{
    		long time = System.currentTimeMillis();
    		while(System.currentTimeMillis() < time + 30000) {
    			if(dealer.getCurrentPlayer().getSeatNumber() != request.getLastPlayer()) {
    				changed = true;
    				response.setLastAction(dealer.getLastAction().toString());
    				response.setLastBet(dealer.getCurrentBet());
    				List<BettingPlayer> activePlayers = dealer.getActivePlayers();
    				response.setLastPlayer(activePlayers.get(activePlayers.size() -1).getSeatNumber());
    				if(request.getMySeatNumber() == dealer.getCurrentPlayer().getSeatNumber()) {
    					response.setPlayerActive(true);
    				}
    				else {
    					response.setPlayerActive(false);
    				}
    				break;
    			}
    			Thread.sleep(1000);
    		}
    	}
    	catch(Exception ex) {
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	if(!changed) {
    		response.setLastPlayer(request.getLastPlayer());
    	}
    	return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }
    
    @Path("/takebettingaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response takeBettingAction(ActionRequest action) {
    	try {
    		dealer.takeAction(Action.valueOf(action.getAction()), action.getChipAmount());
    	}
    	catch(Exception ex) {
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	ActionResponse response = new ActionResponse();
    	response.setBettingComplete(dealer.bettingComplete());
    	return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }
}
