package cardgameslib.chatserver;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

/**
 *
 * @author Ryan Bickham
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer{
    List<ChatListener> chatListeners = new ArrayList<>();
    
    public ChatServerImpl() throws RemoteException{}
    
    @Override
    public void addChatListener(ChatListener cl) {
        System.out.println("Adding Listener");
        chatListeners.add(cl);
    }

    @Override
    public void removeChatListener(ChatListener cl) {
        System.out.println("Removing Listener");
        chatListeners.remove(cl);
    }

    @Override
    public synchronized void sendChatMessage(String message) throws RemoteException {
        try {
            for(ChatListener cl : chatListeners) {
                cl.chatEventSent(new ChatEvent(this, message));
            }
        } catch (RemoteException ex) {
            ex.printStackTrace(System.out);
        } 
    }  
}
