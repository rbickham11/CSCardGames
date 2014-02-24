package cardgameslib.chatserver;

import java.rmi.*;;
import java.util.EventListener;

/**
 * Defines the structure for a ChatListener to be implemented on the client side.
 * @author Ryan Bickham
 */
public interface ChatListener extends Remote, EventListener {
    public void chatEventSent(ChatEvent ev) throws RemoteException;
}

