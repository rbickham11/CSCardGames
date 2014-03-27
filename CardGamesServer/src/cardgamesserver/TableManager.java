package cardgamesserver;

import cardgamesserver.chatserver.ChatServer;
import cardgamesserver.games.blackjack.BlackjackDealer;
import cardgamesserver.games.euchre.EuchreDealer;
import java.util.*;
import cardgamesserver.games.poker.holdem.HoldemDealer;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import cardgameslib.*;

/**
 * This class manages all active game tables
 * @author Ryan
 */
public class TableManager implements ITableManager {
    private static TableManager instance;
    
    private static final String ALPHANUM_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RAND_LENGTH = 5;
        
    private final List<TableDescription> holdemTables = new ArrayList<>();
    private final List<TableDescription> euchreTables = new ArrayList<>();
    private final List<TableDescription> blackjackTables = new ArrayList<>();
        
    private TableManager() { }
    
    public static TableManager getInstance() {
        if(instance == null) {
            instance = new TableManager();
        }
        return instance;
    }
    
    public void addHoldemTable(int maxChips, int bb, String description) {
        String randString = getRandomIdString();
        String tableId = "holdem" + randString;
        String chatId = "chat" + randString;
        
        for(TableDescription t : holdemTables) {
            if((tableId).equals(t.getTableId())) {
                addHoldemTable(maxChips, bb, description);
            }
        }
        
        try {
            CardGamesServer.registerObject(tableId, new HoldemDealer(maxChips, bb));
            CardGamesServer.registerObject(chatId, new ChatServer());
        }
        catch(AlreadyBoundException ex) {
            System.out.println("The table or chat object was already bound");
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }

        holdemTables.add(new TableDescription(tableId, chatId, description, 
                String.format("Blinds: %d / %d", bb / 2, bb), String.format("Max Buy-in: %d", maxChips), 
                String.format("%d / %d", 0, HoldemDealer.MAX_PLAYERS)));
    }
    
    public void addEuchreTable(String description1, String description2) {
        String randString = getRandomIdString();
        String tableId = "euchre" + randString;
        String chatId = "chat" + randString;
        
        for(TableDescription t : holdemTables) {
            if((tableId).equals(t.getTableId())) {
                addEuchreTable(description1, description2);
            }
        }
        
        try {
            CardGamesServer.registerObject(tableId, new EuchreDealer());
            CardGamesServer.registerObject(chatId, new ChatServer());
        }
        catch(AlreadyBoundException ex) {
            System.out.println("The table or chat object was already bound");
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }

        euchreTables.add(new TableDescription(tableId, chatId, description1, 
                description2, "", String.format("%d / %d", 0, EuchreDealer.MIN_MAX_PLAYERS)));
    }
    
    public void addBlackjackTable(int lowLimit, int highLimit, String description) {
        String randString = getRandomIdString();
        String tableId = "blackjack" + randString;
        String chatId = "chat" + randString;
        
        for(TableDescription t : holdemTables) {
            if((tableId).equals(t.getTableId())) {
                addBlackjackTable(lowLimit, highLimit, description);
            }
        }
        
        try {
            CardGamesServer.registerObject(tableId, new BlackjackDealer(lowLimit, highLimit));
            CardGamesServer.registerObject(chatId, new ChatServer());
        }
        catch(AlreadyBoundException ex) {
            System.out.println("The table or chat object was already bound");
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }

        blackjackTables.add(new TableDescription(tableId, chatId, description, 
                String.format("Minimum Bet: %d", lowLimit), String.format("Maximum Bet: %d", highLimit), 
                String.format("%d / %d", 0, BlackjackDealer.MAX_PLAYERS)));
    }
        
    public String getRandomIdString() {
        Random r = new Random();
        String randString = "";
        for(int i = 0; i < RAND_LENGTH; i++) {
            randString += ALPHANUM_CHARS.charAt(r.nextInt(ALPHANUM_CHARS.length()));
        }
        return randString;
    }

    @Override
    public List<TableDescription> getHoldemTables() {
        return holdemTables;
    }

    @Override
    public List<TableDescription> getEuchreTables() {
        return euchreTables;
    }

    @Override
    public List<TableDescription> getBlackjackTables() {
        return blackjackTables;
    }
}
