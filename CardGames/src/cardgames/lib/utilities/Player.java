package cardgames.lib.utilities;

import java.util.*;

public class Player implements Comparable<Player> {
    protected int userId;
    protected String username;
   
    protected int seatNumber; //Player # on table (Player 1, Player 2, etc.)
    protected List<Integer> hand;
    
    public Player(int id, int seatNum) {
        userId = id;
        seatNumber = seatNum;
        hand = new ArrayList<>();
        
        //Query database using userId to get any other information needed for backend 
        //(Total chip count, username if we need it, etc.)
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public List<Integer> getHand() {
        return hand;
    }
    
    public void giveCard(int card) {
        hand.add(card);
    }
    
    @Override
    public int compareTo(Player otherPlayer) {
        return seatNumber - otherPlayer.getSeatNumber();
    }
}