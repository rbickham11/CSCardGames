package cardgameslib.games;

import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Ryan Bickham
 */
public interface IHoldemDealer extends Remote {
    public void addPlayer(int id, String username, int seatNum, int startingChips);
    public void takeAction(PokerAction action, int chipAmount);
    public int getPotSize();
    public List<BettingPlayer> getActivePlayers();
}
