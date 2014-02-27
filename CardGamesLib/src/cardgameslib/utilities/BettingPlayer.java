package cardgameslib.utilities;

import java.util.*;

/**
 * Class to handle player who is able to bet
 * @author Ryan Bickham
 *
 */
public class BettingPlayer extends Player {   //Used for games with betting, inherits Player
    private int chips;
    private int currentBet;
    /**
     * Constructor for BettingPlayer
     * @param id int value holding player number
     * @param seatNum int holding seat player is at
     * @param startingChips int holding chips player starts with
     */
    public BettingPlayer(int id, int seatNum, int startingChips) {
        super(id, seatNum);
        chips = startingChips;
        currentBet = 0;
    }
    
    /**
     * Getter to return chip count 
     * @return int
     */
    public int getChips() {
        return chips;
    }
    
    /**
     * Getter to return the current bet
     * @return int
     */
    public int getCurrentBet() {
        return currentBet;
    }
    
    /**
     * Function to handle incrementing chips from winning
     * @param amount int value holding amount to increase chip count by
     */
    public void incrementChips(int amount) {
        chips += amount;
    }
    
    /**
     * Function to handle decreases chip count
     * @param amount int value holding amount to decrease chip count by
     */
    public void decrementChips(int amount) {
        if(chips - amount < 0) {
            throw new IllegalArgumentException("You don't have enough chips!");
        }
        chips -= amount;
    }
    
    /**
     * Function to set the current bet
     * @param amount int value to hold amount current bet is
     */
    public void setCurrentBet(int amount) {
        currentBet = amount;
    }
    
    /**
     * Function to reset the current bet to 0
     */
    public void resetCurrentBet() {
        currentBet = 0;
    }
    
    /**
     * Function to determine a player by a card in their hand
     * @param players List of BettingPlayers to look through
     * @param card int value of card you are using to get player
     * @return BettingPlayer
     */
    public static BettingPlayer getPlayerByCard(List<BettingPlayer> players, int card) {
        for(BettingPlayer player : players) {
            if(player.getHand().contains(card)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given card");
    }
    
    /**
     * Function to determine a player by their hand
     * @param players List of BettingPlayers to look through
     * @param hand List of integer values representing a BettingPlayer's hand
     * @return BettingPlayer
     */
    public static BettingPlayer getPlayerByHand(List<BettingPlayer> players, List<Integer> hand) {
        for(BettingPlayer player : players) {
            if(player.getHand().equals(hand)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given hand");
    }
    
    /**
     * Function to get a player by the seat they are sitting at
     * @param players List of BettingPlayers to look through
     * @param seatNumber int holding the seat number whose player you wish to get
     * @return
     */
    public static BettingPlayer getPlayerBySeatNumber(List<BettingPlayer> players, int seatNumber) {
        for(BettingPlayer player : players) {
            if(player.getSeatNumber() == seatNumber) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found with given seat number");
    }
}
