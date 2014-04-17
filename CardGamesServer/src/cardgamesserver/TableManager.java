package cardgamesserver;

import cardgamesserver.chatserver.ChatServer;
import cardgamesserver.games.blackjack.BlackjackDealer;
import cardgamesserver.games.euchre.EuchreDealer;
import java.util.*;
import cardgamesserver.games.poker.holdem.HoldemDealer;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import cardgameslib.*;
import cardgameslib.games.IBlackjackDealer;
import cardgameslib.games.IEuchreDealer;
import cardgameslib.games.IHoldemDealer;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class manages all active game tables
 * @author Ryan
 */
public class TableManager extends UnicastRemoteObject implements ITableManager {
    private static TableManager instance;
    
    private static final String ALPHANUM_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RAND_LENGTH = 5;
        
    private final List<TableDescription> holdemTables = new ArrayList<>();
    private final List<TableDescription> euchreTables = new ArrayList<>();
    private final List<TableDescription> blackjackTables = new ArrayList<>();
        
    private TableManager() throws RemoteException { }
    
    public static TableManager getInstance() throws RemoteException {
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
                String.format("Blinds: %d / %d", bb / 2, bb), String.format("Max Buy-in: %d", maxChips), HoldemDealer.MAX_PLAYERS));
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
                description2, "", EuchreDealer.MIN_MAX_PLAYERS));
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
                String.format("Minimum Bet: %d", lowLimit), String.format("Maximum Bet: %d", highLimit), BlackjackDealer.MAX_PLAYERS));
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
    public List<TableDescription> getHoldemTables() throws RemoteException {
        Registry r = LocateRegistry.getRegistry(CardGamesServer.PORT);
        for(TableDescription desc : holdemTables) {
            try {
                IHoldemDealer d = (IHoldemDealer)r.lookup(desc.getTableId());
                desc.setPlayerCount(d.getPlayers().size());
            }
            catch(NotBoundException | RemoteException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return holdemTables;
    }

    @Override
    public List<TableDescription> getEuchreTables() throws RemoteException {
        Registry r = LocateRegistry.getRegistry(CardGamesServer.PORT);
        for(TableDescription desc : euchreTables) {
            try {
                IEuchreDealer d = (IEuchreDealer)r.lookup(desc.getTableId());
                desc.setPlayerCount(d.getPlayers().size());
            }
            catch(NotBoundException | RemoteException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return euchreTables;
    }

    @Override
    public List<TableDescription> getBlackjackTables() throws RemoteException {
        Registry r = LocateRegistry.getRegistry(CardGamesServer.PORT);
        for(TableDescription desc : blackjackTables) {
            try {
                IBlackjackDealer d = (IBlackjackDealer)r.lookup(desc.getTableId());
                desc.setPlayerCount(d.getPlayers().size());
            }
            catch(NotBoundException | RemoteException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return blackjackTables;
    }
}
