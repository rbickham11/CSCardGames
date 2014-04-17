package cardgameslib.games;

import cardgameslib.utilities.BettingPlayer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ryan Bickham
 */
public interface IBlackjackDealer extends Remote {
    public ArrayList<BettingPlayer> getPlayers() throws RemoteException;
}
