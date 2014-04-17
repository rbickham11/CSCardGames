package cardgameslib.games;

import cardgameslib.utilities.Player;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ryan Bickham
 */
public interface IEuchreDealer extends Remote {
    public ArrayList<Player> getPlayers() throws RemoteException;
}
