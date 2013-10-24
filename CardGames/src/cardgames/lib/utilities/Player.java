package cardgames.lib.utilities;

import java.util.*;

public class Player implements Comparable<Player> {
    protected int userId;
    protected String username;
   
    protected int seatNumber; //Player # on table (Player 1, Player 2, etc.)
    protected String euchreDealSequence;
    protected List<Integer> hand;
    
    public Player(int id, int seatNum) {
        userId = id;
        seatNumber = seatNum;
        hand = new ArrayList<>();
        //Query database using userId to get any other information needed for backend 
        //(Total chip count, username if we need it, etc.)
    }
    
    public Player(int id, int seatNum, String dealSequence) {
        userId = id;
        seatNumber = seatNum;
        hand = new ArrayList<>();
        setDealSequence(dealSequence);
        //Query database using userId to get any other information needed for backend 
        //(Total chip count, username if we need it, etc.)
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public String getDealSequence() {
      return euchreDealSequence;
    }
    
    private void setDealSequence(String dealSequence) {
      String[] sequence = dealSequence.split(", ");
      
      for(int i = 0; i < sequence.length; i++) {
        if(!sequence[i].equals("2") && !sequence[i].equals("3")) {
          dealSequence = "3, 2, 3, 2";
          break;
        }
      }
      euchreDealSequence = dealSequence;
    }
    
    public List<Integer> getHand() {
        return hand;
    }
    
    public void giveCard(int card) {
        hand.add(card);
    }
    
    public void giveCard(List<Integer> cards) {
      for(Integer card : cards) {
        hand.add(card);
      }
    }
    
    @Override
    public int compareTo(Player otherPlayer) {
        return seatNumber - otherPlayer.getSeatNumber();
    }
    
    public static Player getPlayerByHand(List<BettingPlayer> players, List<Integer> hand) {
        for(Player player : players) {
            if(player.getHand().equals(hand)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given hand");
    }
}