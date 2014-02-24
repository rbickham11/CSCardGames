package cardgameslib.chatserver;

import java.rmi.*;
/**
 * RMI remote interface that defines ChatServer class structure. 
 * @author Ryan Bickham
 */
public interface ChatServer extends Remote {
    public void addChatListener(ChatListener cl) throws RemoteException;
    public void removeChatListener(ChatListener cl) throws RemoteException;
    public void sendChatMessage(String message) throws RemoteException;
}