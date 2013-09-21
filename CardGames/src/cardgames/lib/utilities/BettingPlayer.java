package cardgames.lib.utilities;

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
}
