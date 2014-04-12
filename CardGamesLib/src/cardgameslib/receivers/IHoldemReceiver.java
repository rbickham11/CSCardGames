package cardgameslib.receivers;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ryan
 */
public interface IHoldemReceiver extends Remote {
    public void updatePlayers() throws RemoteException;
}
