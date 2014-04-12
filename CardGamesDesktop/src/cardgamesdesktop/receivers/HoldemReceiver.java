package cardgamesdesktop.receivers;

import cardgamesdesktop.controllers.HoldEmGUIController;
import cardgameslib.receivers.IHoldemReceiver;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ryan
 */
public class HoldemReceiver extends UnicastRemoteObject implements IHoldemReceiver {
    private HoldEmGUIController controller;
    
    public HoldemReceiver(HoldEmGUIController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void updatePlayers() throws RemoteException {
        controller.updatePlayers();
    }
}
