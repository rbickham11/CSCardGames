package cardgamesdesktop.utilities;

import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 * Provides utility methods used to get access to the remote RMI registry
 * @author Ryan Bickham
 */
public class RMIConnection {
    private static RMIConnection instance;
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    
    private Registry registry;
    
    private RMIConnection() {
        try {
            registry = LocateRegistry.getRegistry(HOST, PORT);
        }
        catch(RemoteException ex) {
            System.out.printf("The remote registry at %s:%d was not found\n", HOST, PORT);
        }
    }
    
    public static RMIConnection getInstance() {
        if(instance == null) {
            instance = new RMIConnection();
        }
        return instance;
    }
    
    public Registry getRegistry() {
        return registry;
    }
}
