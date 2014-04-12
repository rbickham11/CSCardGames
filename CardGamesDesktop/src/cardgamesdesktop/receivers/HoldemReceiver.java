package cardgamesdesktop.receivers;

import cardgamesdesktop.controllers.HoldEmGUIController;
import cardgameslib.receivers.IHoldemReceiver;
import cardgameslib.utilities.BettingPlayer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ryan
 */
public class HoldemReceiver extends UnicastRemoteObject implements IHoldemReceiver {
    private final HoldEmGUIController controller;
    
    public HoldemReceiver(HoldEmGUIController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void initializePlayers() throws RemoteException {
        controller.initializePlayers();
    }
    
    @Override
    public void displayCards() throws RemoteException {
        controller.displayCards();
    }
    
    @Override
    public void updateChipValues(BettingPlayer player) throws RemoteException {
        controller.updateChipValues(player);
    }
}
