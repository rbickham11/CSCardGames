package cardgameslib.games;

import cardgameslib.receivers.IHoldemReceiver;
import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ryan Bickham
 */
public interface IHoldemDealer extends Remote {
    public void addPlayer(int id, String username, int seatNum, int startingChips) throws RemoteException;
    public void removePlayer(int id) throws RemoteException;
    public void takeAction(PokerAction action, int chipAmount) throws RemoteException;
    public int getPotSize() throws RemoteException;
    public ArrayList<Integer> getAvailableSeats() throws RemoteException;
    public ArrayList<BettingPlayer> getActivePlayers() throws RemoteException;
    public ArrayList<BettingPlayer> getPlayers() throws RemoteException;
    public void addClient(IHoldemReceiver client) throws RemoteException;
    public void removeClient(IHoldemReceiver client) throws RemoteException;
}
