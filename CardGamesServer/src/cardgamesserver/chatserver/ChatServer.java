package cardgamesserver.chatserver;

import cardgameslib.chatserver.ChatEvent;
import cardgameslib.chatserver.ChatListener;
import cardgameslib.chatserver.IChatServer;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

/**
 * Implementation of ChatServer that represents the server side of the chat session.
 * @author Ryan Bickham
 */
public class ChatServer extends UnicastRemoteObject implements IChatServer{
    List<ChatListener> chatListeners = new ArrayList<>();
    
    public ChatServer() throws RemoteException{}
    
    /**
     * Adds a client chat listener to the list of active clients
     * @param cl The ChatListener passed from the client
     */
    @Override
    public void addChatListener(ChatListener cl) {
        System.out.println("Adding Listener");
        chatListeners.add(cl);
    }

    /**
     * Removes client chat listener from the list, removing the client from the
     * session.
     * @param cl The client ChatListener to remove
     */
    @Override
    public void removeChatListener(ChatListener cl) {
        System.out.println("Removing Listener");
        chatListeners.remove(cl);
    }

    /**
     * Sends a chat message to each active client ChatListener 
     * @param message The message to send
     * @throws RemoteException 
     */
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
