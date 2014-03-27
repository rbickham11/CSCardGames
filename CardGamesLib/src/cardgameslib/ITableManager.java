package cardgameslib;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *  
 * @author Ryan
 */
public interface ITableManager extends Remote {
    public List<TableDescription> getHoldemTables() throws RemoteException;
    public List<TableDescription> getEuchreTables() throws RemoteException;
    public List<TableDescription> getBlackjackTables() throws RemoteException;
}
