package cardgameslib.utilities.betting;

import cardgameslib.utilities.BettingPlayer;
import java.util.*;

public class PokerBettingHelper {
    private List<BettingPlayer> activePlayers;
    private int potSize;
    private int activeBet;
    private int bigBlind;
    
    public PokerBettingHelper(List<BettingPlayer> inPlayers, int bb) {
        activePlayers = inPlayers;
        bigBlind = bb;
        potSize = 0;
        activeBet = 0;
    }
    
    public int getBigBlind() {
        return bigBlind;
    }
    
    public int getCurrentBet() {
        return activeBet;
    }
    
    public void takeAction(Action action, int chipAmount) {
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
                activePlayers.remove(0);
            case RAISE:
                raise(chipAmount);
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        
        if(action != Action.FOLD) {
            Collections.rotate(activePlayers, 1);
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
        for(BettingPlayer player : activePlayers) {
            if(player.getCurrentBet() != activeBet) {
                return false;
            }
        }
        return true;
    }
}
