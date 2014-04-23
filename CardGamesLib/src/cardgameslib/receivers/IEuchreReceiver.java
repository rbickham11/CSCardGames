package cardgameslib.receivers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
    public void showPlayedCard(int player, String card) throws RemoteException;
    public int getPlayerId() throws RemoteException;
    public void resetPlayersCards() throws RemoteException;
    public void showActivePlayer(int currentSeat, int lastSeat) throws RemoteException;
    public void setCanPlayCard(boolean can) throws RemoteException;
    public void showFollowSuit(List<String> canNotPlay) throws RemoteException;
    public void resetAfterTrick() throws RemoteException;
    public void resetAfterHand() throws RemoteException;
    public void resetAfterGame() throws RemoteException;
}