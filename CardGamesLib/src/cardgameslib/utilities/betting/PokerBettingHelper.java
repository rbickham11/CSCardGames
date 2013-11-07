package cardgameslib.utilities.betting;

import cardgameslib.utilities.BettingPlayer;

public class PokerBettingHelper {
    private int potSize;
    private int currentBet;
    
    public PokerBettingHelper() {
        potSize = 0;
        currentBet = 0;
    }
    
    public int getCurrentBet() {
        return currentBet;
    }
    
    public void awardPot(BettingPlayer player) {
        player.incrementChips(potSize);
        potSize = 0;     
    }
    
    public void bet(BettingPlayer player, int chipAmount) {
        potSize += chipAmount;
        player.decrementChips(chipAmount);
        currentBet = chipAmount;
    }
    
    public void call(BettingPlayer player) {
        potSize += currentBet;
        player.decrementChips(currentBet);
    }
    
    public void raise(BettingPlayer player, int chipAmount) {
        if(chipAmount < currentBet * 2)
            throw new IllegalArgumentException("Invalid raise");
        potSize += chipAmount;
        player.decrementChips(chipAmount);
        currentBet = chipAmount;
    }
}
