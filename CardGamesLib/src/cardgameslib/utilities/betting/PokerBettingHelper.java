package cardgameslib.utilities.betting;

import cardgameslib.utilities.BettingPlayer;
import java.util.*;

public class PokerBettingHelper {
    private List<BettingPlayer> activePlayers;
    private BettingPlayer firstToAct;
    private BettingPlayer lastToAct;
    
    private int potSize;
    private int activeBet;
    private int bigBlind;
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
    
    public void startNewRound(boolean preFlop) {
        firstToAct = activePlayers.get(0);
        lastToAct = activePlayers.get(activePlayers.size() - 1);
        activeBet = 0;
        
        if(preFlop) {
            bet(bigBlind / 2);
            bet(bigBlind);
            Collections.rotate(activePlayers, -2);
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
                bet(chipAmount);
                break;
            case CALL:
                call();
                break;
            case CHECK: 
                break;
            case FOLD:
                if(activePlayers.get(0).equals(firstToAct)) {
                    firstToAct = activePlayers.get(1);
                }
                activePlayers.remove(0);
                break;
            case RAISE:
                raise(chipAmount);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        
        if(action != Action.FOLD) {
            Collections.rotate(activePlayers, -1);
        }
    }
        
    public void awardPot(BettingPlayer player) {
        player.incrementChips(potSize);
        potSize = 0;     
    }
    
    public void bet(int chipAmount) {
        potSize += chipAmount;
        activePlayers.get(0).decrementChips(chipAmount);
        activeBet = chipAmount;
    }
    
    public void call() {
        potSize += activeBet;
        activePlayers.get(0).decrementChips(activeBet);
    }
    
    public void raise(int chipAmount) {
        if(chipAmount < activeBet * 2)
            throw new IllegalArgumentException("Invalid raise");
        potSize += chipAmount;
        activePlayers.get(0).decrementChips(chipAmount);
        activeBet = chipAmount;
    }
    
    public boolean bettingComplete() {
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
