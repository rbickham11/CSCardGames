package cardgameslib;

import java.rmi.Remote;
import java.util.List;

/**
 *  
 * @author Ryan
 */
public interface ITableManager extends Remote {
    public List<TableDescription> getHoldemTables();
    public List<TableDescription> getEuchreTables();
    public List<TableDescription> getBlackjackTables();
}
