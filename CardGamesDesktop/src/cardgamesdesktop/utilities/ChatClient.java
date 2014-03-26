package cardgamesdesktop.utilities;

import cardgameslib.chatserver.*;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import javafx.beans.property.StringProperty;

/**
 * A class for sending and receiving chat messages from the server.
 * @author Ryan Bickham
 */
public class ChatClient extends UnicastRemoteObject implements ChatListener, Serializable {
    private final static String HOST = "localhost";
    private final static int PORT = 1099;
    
    private ChatServer chatServer;
    private Registry registry;
    private StringProperty chatBoxString;
    
    /**
     * Establishes connection to the server class and adds itself as a chat listener
     * @param remoteServerRef An rmi registry reference name to the remote server
     * @param chatBoxString A reference to the value in chat box to hold messages
     * @throws RemoteException 
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ChatClient(String remoteServerRef, StringProperty chatBoxString) throws RemoteException {
        super();
        try {
            this.chatBoxString = chatBoxString;
            //Connect to the server registry
            registry = LocateRegistry.getRegistry(HOST, PORT);
            
            //Get reference to concrete ChatServer implementation
            chatServer = (ChatServer)registry.lookup(remoteServerRef);
            chatServer.addChatListener(this);
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Sends a chat message to the server
     * @param message The message to be sent
     */
    public void sendChatMessage(String message) {
        try {
            chatServer.sendChatMessage(message);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Event listener implementation that sets the chat box value when
     * a message has been received from the server.
     * @param ev The chat event
     */
    @Override
    public void chatEventSent(ChatEvent ev) { 
        chatBoxString.setValue(chatBoxString.getValue() + "\n" + ev.getMessage());
    }   
    
    /**
     * Closes the connection to the server
     */
    public void closeConnection() {
        try {
            chatServer.removeChatListener(this);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
