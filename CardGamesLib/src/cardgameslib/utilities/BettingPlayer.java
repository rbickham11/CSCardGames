package cardgameslib.utilities;

import java.util.*;

public class BettingPlayer extends Player {   //Used for games with betting, inherits Player
    private int chips;
    private int currentBet;
    
    public BettingPlayer(int id, String userName, int seatNum, int startingChips) {
        super(id, userName, seatNum);
        chips = startingChips;
        currentBet = 0;
    }
    
    public int getChips() {
        return chips;
    }
    
    public int getCurrentBet() {
        return currentBet;
    }
    
    public void incrementChips(int amount) {
        chips += amount;
    }
    
    public void decrementChips(int amount) {
        if(chips - amount < 0) {
            throw new IllegalArgumentException("You don't have enough chips!");
        }
        chips -= amount;
    }
    
    public void setCurrentBet(int amount) {
        currentBet = amount;
    }
    
    public void resetCurrentBet() {
        currentBet = 0;
    }
    
    public static BettingPlayer getPlayerByCard(List<BettingPlayer> players, int card) {
        for(BettingPlayer player : players) {
            if(player.getHand().contains(card)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given card");
    }
    
    public static BettingPlayer getPlayerByHand(List<BettingPlayer> players, List<Integer> hand) {
        for(BettingPlayer player : players) {
            if(player.getHand().equals(hand)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given hand");
    }
    
    public static BettingPlayer getPlayerBySeatNumber(List<BettingPlayer> players, int seatNumber) {
        for(BettingPlayer player : players) {
            if(player.getSeatNumber() == seatNumber) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given seat number");
    }
}
