package cardgamesdesktop.receivers;

import cardgamesdesktop.controllers.HoldEmGUIController;
import cardgameslib.receivers.IHoldemReceiver;
import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.PokerAction;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public class HoldemReceiver extends UnicastRemoteObject implements IHoldemReceiver {
    private final HoldEmGUIController controller;
    private final int playerId;
    
    public HoldemReceiver(HoldEmGUIController controller, int playerId) throws RemoteException {
        this.controller = controller;
        this.playerId = playerId;
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
    
    @Override
    public void offerActions(ArrayList<PokerAction> actions) throws RemoteException {
        controller.setAvailableActions(actions);
    }
    
    @Override
    public void disableActions() throws RemoteException {
        controller.disableActions();
    }
    
    @Override
    public int getPlayerId() throws RemoteException {
        return playerId;
    }
    
    @Override
    public void updateBoardCards(ArrayList<Integer> cards) {
        controller.updateBoardCards(cards);
    }
}
