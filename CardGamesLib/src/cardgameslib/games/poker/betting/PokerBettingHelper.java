package cardgameslib.games.poker.betting;

import cardgameslib.utilities.BettingPlayer;
import java.util.*;

/**
 * This class is used to assist the poker type games in handling how bets work
 * @author Ryan Bickham
 *
 */
public class PokerBettingHelper {
    private List<BettingPlayer> activePlayers;
    private BettingPlayer firstToAct;
    private BettingPlayer lastToAct;
    
    private int potSize;
    private int activeBet;
    private int bigBlind;
    
    private Action lastAction;
    private boolean preflop;
    private boolean allActed;
    private boolean firstActed;  //Whether or not the first player has acted

    /**
     * Constructor for PokerBettingHelper
     * @param inPlayers List<BettingPlayer> to hold which players are betting
     * @param bb int holding the size of the big blind
     */
    public PokerBettingHelper(List<BettingPlayer> inPlayers, int bb) {
        activePlayers = inPlayers;
        bigBlind = bb;
        potSize = 0;
        activeBet = 0;
        allActed = false;
        firstActed = false;
    }
    
    /**
     * Getter to return the big blind
     * @return int
     */
    public int getBigBlind() {
        return bigBlind;
    }
    
    /**
     * Getter to return the current bet
     * @return int
     */
    public int getCurrentBet() {
        return activeBet;
    }
    
    /**
     * Getter to return the size of the pot
     * @return int
     */
    public int getPotSize() {
        return potSize;
    }
    
    /**
     * Getter to return the last action taken
     * @return Action
     */
    public Action getLastAction() {
    	return lastAction;
    }
    
    /**
     * Getter to return if the first player in a round has acted
     * @return Action
     */
    public boolean getFirstActed() {
    	return firstActed;
    }
    /**
     * Starts a new round of betting
     * @param preFlop boolean to determine whether or not it is the beginning of a hand
     */
    public void startNewRound(boolean preFlop) {
    	lastAction = Action.CHECK;
    	firstActed = false;
        preflop = preFlop;
        
        if(preflop){
            firstToAct = activePlayers.get(0);
            lastToAct = activePlayers.get(activePlayers.size() - 1);
        }
        activeBet = 0;
        allActed = false;
        
        while(activePlayers.get(0) != firstToAct) {
            Collections.rotate(activePlayers, -1);
        }
        
        for(BettingPlayer player : activePlayers) {
            player.resetCurrentBet();
        }
        
        if(preflop) {
            bet(bigBlind / 2);
            Collections.rotate(activePlayers, -1);
            bet(bigBlind);
            Collections.rotate(activePlayers, -1);
        }
    }
    
    /**
     * This function handles the player's action
     * @param action Action that holds tha player's chosen Action
     * @param chipAmount int to hold the amount the player is betting
     */
    public void takeAction(Action action, int chipAmount) {
        if(!allActed) {
            if(activePlayers.get(0).equals(lastToAct)) {
                allActed = true;
            }
        }
        
        switch(action) {
            case BET:
                if(activeBet != 0) {
                    throw new IllegalArgumentException("You cannot place an initial bet with one already active");
                }
                bet(chipAmount);
                break;
            case CALL:
                if(activeBet == 0) {
                    throw new IllegalArgumentException("You cannot call when there has been no bet placed");
                }
                call();
                break;
            case CHECK: 
                if(activeBet != 0) {
                    throw new IllegalArgumentException("You cannot check when a bet has been placed");
                }
                break;
            case FOLD:
                if(activePlayers.get(0).equals(firstToAct)) {
                    firstToAct = activePlayers.get(1);
                }
                activePlayers.remove(0);
                break;
            case RAISE:
                if(activeBet == 0) {
                    throw new IllegalArgumentException("You cannot place a raise without an initial bet being placed");
                }
                raise(chipAmount);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        
        lastAction = action;
        firstActed = true;
        if(action != Action.FOLD) {
        	System.out.println("activePlayers rotated");
            Collections.rotate(activePlayers, -1);
        }
    }
    
    /**
     * Returns whether or not someone has won from other players folding
     * @return boolean
     */
    public boolean isWinner() {
        return activePlayers.size() == 1;
    }
    
    /**
     * Awards the pot to the winning player
     * @param player BettingPlayer that is the one who won the round
     */
    public void awardPot(BettingPlayer player) {
        player.incrementChips(potSize);
        potSize = 0;     
    }
    
    /**
     * Handles player choosing to bet
     * @param chipAmount int holding the amount the player wishes to bet
     */
    public void bet(int chipAmount) {
        if(chipAmount < bigBlind && !preflop) {
            throw new IllegalArgumentException("Invalid bet");
        }
        
        potSize += chipAmount;
        activePlayers.get(0).decrementChips(chipAmount);
        activePlayers.get(0).setCurrentBet(chipAmount);
        activeBet = chipAmount;
    }
    
    /**
     * Handles a player calling another player's raise or bet
     */
    public void call() {
        potSize += activeBet;
        activePlayers.get(0).decrementChips(activeBet);
        activePlayers.get(0).setCurrentBet(activeBet);
    }
    
    /**
     * Handles player choosing to raise
     * @param chipAmount int amount holding the size of the raise
     */
    public void raise(int chipAmount) {
        if(chipAmount < activeBet)
            throw new IllegalArgumentException("Invalid raise");
        activeBet += chipAmount;
        potSize += activeBet;
        activePlayers.get(0).decrementChips(activeBet);
        activePlayers.get(0).setCurrentBet(activeBet);
    }
    /**
     * Handles whether or not the betting is done for the round
     * @return boolean
     */
    public boolean bettingComplete() {
        if(isWinner()) {
            awardPot(activePlayers.get(0));
            return true;
        }
        
        if(!allActed) {
            return false;
        }
        for(BettingPlayer player : activePlayers) {
            if(player.getCurrentBet() != activeBet) {
                return false;
            }
        }
        return true;
    }
}
