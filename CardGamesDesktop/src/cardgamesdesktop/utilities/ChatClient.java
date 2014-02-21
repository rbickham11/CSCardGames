package cardgamesdesktop.utilities;

import cardgameslib.chatserver.*;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ryan Bickham
 */
public class ChatClient extends UnicastRemoteObject implements ChatListener, Serializable {
    private final static String HOST = "localhost";
    private final static int PORT = 1099;
    
    private ChatServer chatServer;
    private Registry registry;
    private StringProperty chatBoxString;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ChatClient(StringProperty chatBoxString) throws RemoteException {
        super();
        try {
            this.chatBoxString = chatBoxString;
            registry = LocateRegistry.getRegistry(HOST, PORT);
            chatServer = (ChatServer)registry.lookup(ChatServer.class.getSimpleName());
            chatServer.addChatListener(this);
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void sendChatMessage(String message) {
        try {
            chatServer.sendChatMessage(message);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    public void chatEventSent(ChatEvent ev) { 
        chatBoxString.setValue(chatBoxString.getValue() + "\n" + ev.getMessage());
    }   
    
    public void closeConnection() {
        try {
            chatServer.removeChatListener(this);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
