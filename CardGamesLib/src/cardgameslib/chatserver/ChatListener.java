package cardgameslib.chatserver;

import java.rmi.*;;
import java.util.EventListener;

/**
 *
 * @author Ryan Bickham
 */
public interface ChatListener extends Remote, EventListener {
    public void chatEventSent(ChatEvent ev) throws RemoteException;
}

