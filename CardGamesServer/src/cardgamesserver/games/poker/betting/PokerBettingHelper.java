package cardgamesserver.games.poker.betting;

import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
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
    private BettingPlayer bbPlayer;
    
    private int potSize;
    private int activeBet;
    private int bigBlind;
    
    private boolean preflop;
    private boolean allActed;
    private ArrayList<PokerAction> availableActions;

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

    public ArrayList<PokerAction> getAvailableActions() {
        return availableActions;
    }
    
    /**
     * Starts a new round of betting
     * @param preFlop boolean to determine whether or not it is the beginning of a hand
     */
    public void startNewRound(boolean preFlop) {
        preflop = preFlop;
        
        if(preflop) {
            firstToAct = activePlayers.get(0);
            lastToAct = activePlayers.get(activePlayers.size() - 1);
            if(activePlayers.size() == 2) {
                bbPlayer = activePlayers.get(0);
            }
            else {
                bbPlayer = activePlayers.get(1);
            }
        }
        
        availableActions = new ArrayList<>(Arrays.asList(PokerAction.BET, PokerAction.CHECK, PokerAction.FOLD));
        activeBet = 0;
        allActed = false;
        
        while(activePlayers.get(0) != firstToAct) {
            Collections.rotate(activePlayers, -1);
        }
        
        for(BettingPlayer player : activePlayers) {
            player.resetCurrentBet();
        }
        
        if(preflop) {
            if(activePlayers.size() == 2) {
                Collections.rotate(activePlayers, -1);
            }
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
    public void takeAction(PokerAction action, int chipAmount) {
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
                //Preflop big blind option
                if(isBigBlindOption()) {
                    break;
                }
                if(activeBet != 0) {
                    throw new IllegalArgumentException("You cannot check when a bet has been placed");
                }
                break;
            case FOLD:
                if(activePlayers.get(0).equals(firstToAct)) {
                    firstToAct = activePlayers.get(1);
                }
                else if(activePlayers.get(0).equals(lastToAct)) {
                    lastToAct = activePlayers.get(activePlayers.size() - 1);
                }
                activePlayers.remove(0);
                break;
            case RAISE:
                if(activeBet == 0) {
                    throw new IllegalArgumentException("You cannot place a raise without an initial bet being placed");
                }
                raise(chipAmount - activeBet);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        
        if(action != PokerAction.FOLD) {
            Collections.rotate(activePlayers, -1);
            if(isBigBlindOption()) {
                availableActions = new ArrayList<>(Arrays.asList(PokerAction.CHECK, PokerAction.RAISE, PokerAction.FOLD));
            }
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
        availableActions = new ArrayList<>(Arrays.asList(PokerAction.CALL, PokerAction.FOLD, PokerAction.RAISE));
    }
    
    /**
     * Handles a player calling another player's raise or bet
     */
    public void call() {
        int additionalChips = activeBet - activePlayers.get(0).getCurrentBet();
        potSize += additionalChips;
        if(additionalChips > activePlayers.get(0).getChips()) {
            activePlayers.get(0).decrementChips(activePlayers.get(0).getChips());
            activePlayers.get(0).setCurrentBet(activePlayers.get(0).getCurrentBet() + activePlayers.get(0).getChips());
        }
        else {
            activePlayers.get(0).decrementChips(additionalChips);
            activePlayers.get(0).setCurrentBet(activeBet);
        }
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
        
        //Special case for preflop big blind option
        if(isBigBlindOption()) {
            return false;
        }
        
        if(!allActed) {
            return false;
        }
        for(BettingPlayer player : activePlayers) {
            if(player.getCurrentBet() != activeBet && player.getChips() != 0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBigBlindOption() {
        return preflop && activePlayers.get(0).equals(bbPlayer) && activeBet == bigBlind;
    }
}
