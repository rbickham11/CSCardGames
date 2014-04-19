package cardgameslib.receivers;

import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public interface IHoldemReceiver extends Remote {
    public void initializePlayers() throws RemoteException;
    public void displayCards() throws RemoteException;
    public void updateChipValues(BettingPlayer player) throws RemoteException;
    public void offerActions(ArrayList<PokerAction> actions) throws RemoteException;
    public void disableActions() throws RemoteException;
    public int getPlayerId() throws RemoteException;
    public void updateBoardCards(ArrayList<Integer> cards) throws RemoteException;
}
