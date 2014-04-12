package cardgameslib.receivers;

import cardgameslib.utilities.BettingPlayer;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ryan
 */
public interface IHoldemReceiver extends Remote {
    public void initializePlayers() throws RemoteException;
    public void displayCards() throws RemoteException;
    public void updateChipValues(BettingPlayer player) throws RemoteException;

}
