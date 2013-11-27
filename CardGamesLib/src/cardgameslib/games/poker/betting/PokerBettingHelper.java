package cardgameslib.games.poker.betting;

import cardgameslib.utilities.BettingPlayer;
import java.util.*;

public class PokerBettingHelper {
    private List<BettingPlayer> activePlayers;
    private BettingPlayer firstToAct;
    private BettingPlayer lastToAct;
    
    private int potSize;
    private int activeBet;
    private int bigBlind;
    
    private boolean preflop;
    private boolean allActed;

    public PokerBettingHelper(List<BettingPlayer> inPlayers, int bb) {
        activePlayers = inPlayers;
        bigBlind = bb;
        potSize = 0;
        activeBet = 0;
        allActed = false;
    }
    
    public int getBigBlind() {
        return bigBlind;
    }
    
    public int getCurrentBet() {
        return activeBet;
    }
    
    public int getPotSize() {
        return potSize;
    }
    
    public void startNewRound(boolean preFlop) {
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
        
        if(action != Action.FOLD) {
            Collections.rotate(activePlayers, -1);
        }
    }
    
    public boolean isWinner() {
        return activePlayers.size() == 1;
    }
    public void awardPot(BettingPlayer player) {
        player.incrementChips(potSize);
        potSize = 0;     
    }
    
    public void bet(int chipAmount) {
        if(chipAmount < bigBlind && !preflop) {
            throw new IllegalArgumentException("Invalid bet");
        }
        
        potSize += chipAmount;
        activePlayers.get(0).decrementChips(chipAmount);
        activePlayers.get(0).setCurrentBet(chipAmount);
        activeBet = chipAmount;
    }
    
    public void call() {
        potSize += activeBet;
        activePlayers.get(0).decrementChips(activeBet);
        activePlayers.get(0).setCurrentBet(activeBet);
    }
    
    public void raise(int chipAmount) {
        if(chipAmount < activeBet)
            throw new IllegalArgumentException("Invalid raise");
        activeBet += chipAmount;
        potSize += activeBet;
        activePlayers.get(0).decrementChips(activeBet);
        activePlayers.get(0).setCurrentBet(activeBet);
    }
    
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
