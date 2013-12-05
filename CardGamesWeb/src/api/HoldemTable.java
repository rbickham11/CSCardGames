
package api;

import java.sql.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

import com.sun.corba.se.pept.transport.Connection;
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
    		dealer.addPlayer(player.getUserId(), player.getUsername(), player.getSeatNumber(), player.getStartingChips());
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
    public Response getTableInformation(ActionStatusRequest request) {
    	TableInformation response = new TableInformation();
    	boolean playerChanged = false;
    	try{
    		long time = System.currentTimeMillis();
    		while(System.currentTimeMillis() < time + 30000) {
    			if(dealer.getCurrentPlayer().getSeatNumber() != request.getLastPlayer()) {
    				playerChanged = true;
    				response.setActivePlayers(dealer.getActivePlayers());
    				response.setBettingComplete(dealer.bettingComplete());
    				response.setCurrentBet(dealer.getCurrentBet());
    				response.setCurrentPlayer(dealer.getCurrentPlayer().getSeatNumber());
    				response.setLastAction(dealer.getLastAction().toString());
    				if(request.getMySeatNumber() == response.getCurrentPlayer()) {
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
    	if(!playerChanged) {
    		response.setCurrentPlayer(request.getLastPlayer());
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
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String data) { 
    	
    	String url = "jdbc:mysql://localhost:3306/";
    	String dbName = "CardGame";
    	String driver = "com.mysql.jdbc.Driver";
    	String userName = "root";
    	String password = "";
    	
    	String[] nameAndUserHolder = data.split("&");
    	
    	String nameHolder = nameAndUserHolder[0];
    	String userPassHolder = nameAndUserHolder[1];
    	
    	String[] finalName = nameAndUserHolder[0].split("=");
    	String[] finalPass = nameAndUserHolder[1].split("=");
    	
    	String name = finalName[1];
    	String userPass = finalPass[1];
    	
    	String successful = "failure";
    	try {
    		Class.forName(driver).newInstance();
    		java.sql.Connection conn = DriverManager.getConnection(url+dbName,userName,password);
    		
    		java.sql.Statement st = ((java.sql.Connection) conn).createStatement();
    		ResultSet res = st.executeQuery("SELECT * FROM login");
    		
    		while(res.next()) {
    			String user = res.getString("username");
    			String pass = res.getString("password");    		
    			
    			if (user.equals(name)) {  
    				System.out.println("hello");
    				if (pass.equals(userPass)) {
    					successful = "success";
    					System.out.println("Successful login");
    					break;
    				} else {
    					System.out.println("Unsuccessful");
    					break;
    				}
    			}
    			
    			System.out.println(user + "\t" + pass);
    		}
    		    		
    		conn.close();
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	

    	
        String result = "Name: " + name + " --- Pass: " + userPass;
        System.out.println(result);

        return Response.ok().entity(successful).build(); 
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(String data) { 
    	
    	String url = "jdbc:mysql://localhost:3306/";
    	String dbName = "CardGame";
    	String driver = "com.mysql.jdbc.Driver";
    	String userName = "root";
    	String password = "";
    	
    	String[] nameAndUserHolder = data.split("&");
    	
    	String nameHolder = nameAndUserHolder[0];
    	String userPassHolder = nameAndUserHolder[1];
    	
    	String[] finalName = nameAndUserHolder[0].split("=");
    	String[] finalPass = nameAndUserHolder[1].split("=");
    	
    	String name = finalName[1];
    	String userPass = finalPass[1];
    	
    	String successful = "failure";
    	try {
    		Class.forName(driver).newInstance();
    		java.sql.Connection conn = DriverManager.getConnection(url+dbName,userName,password);
    		
    		java.sql.Statement st = ((java.sql.Connection) conn).createStatement();
    		int val = st.executeUpdate("INSERT INTO login (username, password) VALUES ('" + name + "','" + userPass + "')");
    		
    		if(val==1) {
    			successful = "success";
    			System.out.print("Successfully inserted value");
    		}                
    		    		
    		conn.close();
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	

    	
        String result = "Name: " + name + " --- Pass: " + userPass;
        System.out.println(result);

        return Response.ok().entity(successful).build(); 
    }
    
}
