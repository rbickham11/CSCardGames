package cardgamesdesktop.receivers;

import cardgamesdesktop.controllers.EuchreGUIController;
import cardgameslib.receivers.IEuchreReceiver;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Andrew Haeger
 */
public class EuchreReceiver extends UnicastRemoteObject implements IEuchreReceiver {
    private final EuchreGUIController controller;
    private final int playerId;
    
    public EuchreReceiver(EuchreGUIController controller, int playerId) throws RemoteException {
        this.controller = controller;
        this.playerId = playerId;
    }
    
    @Override
    public void initializePlayers() throws RemoteException {
        controller.initializePlayers();
    }
    
    @Override
    public void displayCards() throws RemoteException {
        controller.dealHands();
    }
    
    @Override
    public void showDealerOption() throws RemoteException {
        controller.showDealSequencePanel();
    }
    
    @Override
    public void hidePlayerOptions() throws RemoteException {
        controller.hidePlayerOptions();
    }
    
    @Override
    public void showPassCallOption() throws RemoteException {
        controller.showGameChoicesPanel();
    }
    
    @Override
    public void showCardActions(String action) throws RemoteException {
        controller.showCardActions(action);
    }
    
    @Override
    public void showAvailableTrump() throws RemoteException {
        controller.showAvailableTrump();
    }
    
    @Override
     public void showTopCard(int dealer, String topCard) throws RemoteException {
         controller.showTopCard(dealer, topCard);
     }
     
     @Override
     public void downTopCard(int dealer) throws RemoteException {
         controller.downTopCard(dealer);
     }
    
     
     @Override
     public void showTrump(String trump) throws RemoteException {
         controller.showTrump(trump);
     }
    @Override
    public int getPlayerId() throws RemoteException {
        return playerId;
    }
    
    @Override
    public void showActivePlayer(int currentSeat, int lastSeat) {
       controller.showActivePlayer(currentSeat, lastSeat);
    }
    
    @Override
    public void setCanPlayCard(boolean can) throws RemoteException {
        controller.setCanPlayCard(can);
    }
}