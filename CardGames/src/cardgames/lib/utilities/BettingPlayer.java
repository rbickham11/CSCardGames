package cardgames.lib.utilities;

import java.util.*;

public class BettingPlayer extends Player {   //Used for games with betting, inherits Player
    private int chips;
    
    public BettingPlayer(int id, int seatNum, int startingChips) {
        super(id, seatNum);
        chips = startingChips;
    }
    
    public int getChips() {
        return chips;
    }
    
    public void incrementChips(int amount) {
        chips += amount;
    }
    
    public void decrementChips(int amount) {
        chips -= amount;
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
}
