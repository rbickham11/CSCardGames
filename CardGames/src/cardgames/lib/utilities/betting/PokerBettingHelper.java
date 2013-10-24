package cardgames.lib.utilities.betting;

import java.util.*;
import cardgames.lib.utilities.BettingPlayer;

public class PokerBettingHelper {
    private List<BettingPlayer> players;
    private Action action;
    
    private int potSize;
    private int currentBet;
    
    public PokerBettingHelper(List<BettingPlayer> inPlayers) {
        players = inPlayers;
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
    
    public void call(BettingPlayer player)
    {
        
    }
}
