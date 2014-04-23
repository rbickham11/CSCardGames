package cardgameslib.receivers;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Andrew Haeger
 */
public interface IEuchreReceiver extends Remote {
    public void initializePlayers() throws RemoteException;
    public void displayCards() throws RemoteException;
    public void showDealerOption() throws RemoteException;
    public void hidePlayerOptions() throws RemoteException;
    public void showPassCallOption() throws RemoteException;
    public void showCardActions(String action) throws RemoteException;
    public void showAvailableTrump() throws RemoteException;
    public void showTopCard(int dealer, String topCard) throws RemoteException;
    public void downTopCard(int dealer) throws RemoteException;
    public void showTrump(String trump) throws RemoteException;
    public int getPlayerId() throws RemoteException;
    public void showActivePlayer(int currentSeat, int lastSeat) throws RemoteException;
    public void setCanPlayCard(boolean can) throws RemoteException;
}