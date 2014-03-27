package cardgameslib;

import java.io.Serializable;

/**
 * This class represents a serializable table description to be sent to the
 * client side for table listing and table connection.
 * @author Ryan
 */
public class TableDescription implements Serializable {
    private String tableId;
    private String chatId;
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    
    public TableDescription(String tableId, String chatId, String col1, String col2, String col3, String col4) {
        this.tableId = tableId;
        this.chatId = chatId;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
    }
    
    public String getTableId() { return tableId; }
    public String getChatId() { return chatId; }
    public String getCol1() { return col1; }
    public String getCol2() { return col2; }
    public String getCol3() { return col3; }
    public String getCol4() { return col4; }
}