package cardgameslib.games;

import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Ryan Bickham
 */
public interface IHoldemDealer extends Remote {
    public void addPlayer(int id, String username, int seatNum, int startingChips) throws RemoteException;
    public void removePlayer(int id) throws RemoteException;
    public void takeAction(PokerAction action, int chipAmount) throws RemoteException;
    public int getPotSize() throws RemoteException;
    public List<BettingPlayer> getActivePlayers() throws RemoteException;
}
