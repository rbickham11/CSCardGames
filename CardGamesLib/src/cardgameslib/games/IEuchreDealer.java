package cardgameslib.games;

import cardgameslib.receivers.IEuchreReceiver;
import cardgameslib.utilities.Player;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ryan Bickham
 */
public interface IEuchreDealer extends Remote {
    public void addPlayer(int id , String username, int seat) throws RemoteException;
    public ArrayList<Integer> getAvailableSeats() throws RemoteException;
    public ArrayList<Player> getPlayers() throws RemoteException;
    public boolean isCardUp() throws RemoteException;
    public String getTopCard() throws RemoteException;
    public void addClient(IEuchreReceiver client) throws RemoteException;
    public void removeClient(IEuchreReceiver client) throws RemoteException;
    public void dealHands(String sequence) throws RemoteException;
    public void passOnCallingTrump() throws RemoteException;
    public void callTrump(int trump, boolean alone) throws RemoteException;
    public void getCardToReplace(int card) throws RemoteException;
    public void cardPlayed(int card) throws RemoteException;
    public int getTeamOneTricks() throws RemoteException;
    public int getTeamTwoTricks() throws RemoteException;
    public int getTeamOneScore() throws RemoteException;
    public int getTeamTwoScore() throws RemoteException;
}