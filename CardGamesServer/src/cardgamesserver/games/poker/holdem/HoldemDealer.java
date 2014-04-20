package cardgamesserver.games.poker.holdem;

import cardgameslib.utilities.Deck;
import cardgameslib.utilities.Player;
import cardgameslib.utilities.BettingPlayer;
import cardgamesserver.games.poker.betting.*;
import cardgameslib.utilities.PokerAction;
import cardgameslib.games.IHoldemDealer;
import cardgameslib.receivers.IHoldemReceiver;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
/**
 * This class handles Texas Hold'em style poker
 * @author Ryan Bickham
 *
 */
public class HoldemDealer extends UnicastRemoteObject implements IHoldemDealer {
    public static final int MAX_PLAYERS = 9;
   
    private final Deck deck = new Deck();
    private final List<BettingPlayer> players = new ArrayList<>(MAX_PLAYERS);

    private final int chipLimit;
    private final int bigBlind;
    
    private PokerBettingHelper bettingHelper;
    private ArrayList<BettingPlayer> activePlayers = new ArrayList<>();
    private List<Integer> board;
    
    private final ArrayList<Integer> availableSeats;
    private final List<IHoldemReceiver> clients = new ArrayList<>();
    
   
    /**
     * Constructor for HoldemDealer
     * @param maxChips int holding the chip limit for the table
     * @param bb int holding the value of the big blind for the table
     */
    public HoldemDealer(int maxChips, int bb) throws RemoteException {
        chipLimit = maxChips;
        bigBlind = bb;
        bettingHelper = new PokerBettingHelper(activePlayers, bigBlind);

        availableSeats = new ArrayList<>();
        for(int i = 1; i <= MAX_PLAYERS; i++) {
            availableSeats.add(i);
        }
        Random r = new Random();
        Collections.rotate(players, r.nextInt(MAX_PLAYERS));
    }
  
    /**
     * Getter to return list of players at table
     * @return List<BettingPlayer>
     */
    @Override
    public ArrayList<BettingPlayer> getPlayers() throws RemoteException {
    	return (ArrayList)players;
    }
    
    /**
     * Getter to return list of players at table still playing (have not folded yet)
     * @return List<BettingPlayer>
     */
    @Override
    public ArrayList<BettingPlayer> getActivePlayers() throws RemoteException {
    	return activePlayers;
    }
    
    /**
     * Getter to return the table
     * @return List<Integer>
     */
    public List<Integer> getBoard() {
    	return board;
    }
    
    public boolean isBigBlindOption() {
        return bettingHelper.isBigBlindOption();
    }
    
    /**
     * Function to handle adding a new player to the table
     * @param id int to hold player number
     * @param username The player's username
     * @param seatNum int to hold the seat the player is at
     * @param startingChips int to hold the number of chips the player starts with
     */
    @Override
    public void addPlayer(int id, String username, int seatNum, int startingChips) throws RemoteException {
    	if(seatNum < 1 || seatNum > MAX_PLAYERS) {
    		throw new IllegalArgumentException("Invalid seat number");
    	}
    	
    	for(BettingPlayer player : players) {
            if(player.getUserId() == id) {
                    throw new IllegalArgumentException("Player with id " + id + " already on table");
            }
            if(player.getSeatNumber() == seatNum) {
                    throw new IllegalArgumentException("Seat " + seatNum + " is taken.");
            }
    	}
        
    	if(startingChips <= chipLimit) {
            players.add(new BettingPlayer(id, username, seatNum, startingChips));
            availableSeats.remove(new Integer(seatNum));
            Collections.sort(players);
        } else {
            throw new IllegalArgumentException("Starting chip count exceeds maximum for this table");
        }
        for(IHoldemReceiver client : clients) {
            client.initializePlayers();
        }
        if(players.size() == 2) {
            startHand();
        }
    }
    
    /**
     * Handles removing player from Texas Hold'em table
     * @param id int to hold which player is being removed/leaving
     */
    @Override
    public void removePlayer(int id) throws RemoteException {
        for(BettingPlayer player : players) {
            if(player.getUserId() == id) {
                players.remove(player);
                activePlayers.remove(player);
                availableSeats.add(new Integer(player.getSeatNumber()));
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
    }
    
    /**
     * Starts a new hand
     */
    public void startHand() {
    	if(players.size() < 2) {
    		throw new IllegalArgumentException("At least 2 players are needed.");
    	}
        Collections.rotate(players, 1);
        activePlayers = new ArrayList<>(players);
        board = new ArrayList<>();
        deck.collectCards();
        bettingHelper = new PokerBettingHelper(activePlayers, bigBlind);
        
        for(BettingPlayer player : players) {
            player.resetHand();
        }
        deck.shuffle();
        System.out.printf("The dealer is Player %d\n", players.get(players.size() - 1).getSeatNumber());
        dealHands();
        bettingHelper.startNewRound(true);
        for(BettingPlayer p : activePlayers) {
            disableActions(p);
        }
        
        try {
            for(IHoldemReceiver client : clients) {
                client.displayCards();
                for(BettingPlayer p : activePlayers) {
                    System.out.println("Sending bet: " + p.getCurrentBet());
                    client.updateChipValues(p);
                }
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        updateBoardCards();
        offerActions();
    }
    
    /**
     * Deals hands to players
     */
    public void dealHands() {
        List<Integer> cards = deck.dealCards(players.size() * 2);        
        for(int i = 0; i < players.size(); i++) {
            players.get(i).giveCard(cards.get(i));
            players.get(i).giveCard(cards.get(i + players.size()));
        }
        
        for(Player player : players) {    //For testing
            System.out.println(String.format("Player %d's hand is %s %s", player.getSeatNumber(), 
                                            Deck.cardToString(player.getHand().get(0)), Deck.cardToString(player.getHand().get(1))));
        }
    }
    
    /**
     * Deals the first three cards (the flop) to the board
     */
    public void dealFlopToBoard() {
        int card;
        deck.dealCard(); //Burn
        try {
            System.out.printf("\nPot Size: %d\n", getPotSize());
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        System.out.print("Board: ");
        for(int i = 0; i < 3; i++) {
            card = deck.dealCard();
            board.add(card);
            System.out.print(Deck.cardToString(card) + " ");
        }
        System.out.print("\n");
        bettingHelper.startNewRound(false);
        offerActions();
    }
    
    /**
     * Deals a single card to the board (for turn and river)
     */
    public void dealCardToBoard() {   //For turn and river
        deck.dealCard(); //Burn
        int card = deck.dealCard();
        board.add(card);
        try {     
            System.out.printf("\nPot Size: %d\n", getPotSize());
        } catch (RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        System.out.print("Board: ");
        for(int boardCard : board) {
            System.out.print(Deck.cardToString(boardCard) + " ");
        }
        System.out.print("\n");
        bettingHelper.startNewRound(false);
        offerActions();
    }
    
    /**
     * Handles a player taking an action.
     * @param action Action player is taking
     * @param chipAmount int to hold chip amount used in action
     */
    @Override
    public void takeAction(PokerAction action, int chipAmount) throws RemoteException {
        disableActions(getCurrentPlayer());
        bettingHelper.takeAction(action, chipAmount);
        for(IHoldemReceiver client : clients) {
            client.updateChipValues(activePlayers.get(activePlayers.size() - 1));
        }
        if(bettingHelper.bettingComplete()) {
            if(isWinner()) {
                startHand();
                return;
            }
            else if(board.isEmpty()) {
                dealFlopToBoard();
            }
            else if (board.size() == 5) {
                findWinner();
                startHand();
                return;
            }
            else {
                dealCardToBoard();
            }
            updateBoardCards();
            bettingHelper.startNewRound(false);
        }
        else {
            offerActions();
        }
    }
    
    /**
     * Function to determine when betting is complete
     * @return boolean
     */
    public boolean bettingComplete() {
        return bettingHelper.bettingComplete();
    }
    
    /**
     * Getter to return current bet by player
     * @return int
     */
    public int getCurrentBet() {
        return bettingHelper.getCurrentBet();
    }
    
    /**
     * Getter to return the pot size
     * @return int
     */
    @Override
    public int getPotSize() throws RemoteException {
        return bettingHelper.getPotSize();
    }
    
    /**
     * Getter to return the current player
     * @return BettingPlayer
     */
    public BettingPlayer getCurrentPlayer() {
        return activePlayers.get(0);
    }
    
    /**
     * Function to determine whether player is the winner or not
     * @return boolean
     */
    public boolean isWinner() {
        return bettingHelper.isWinner();
    }
    
    public void awardPot(BettingPlayer p) {
        bettingHelper.awardPot(p);
    }
    
    /**
     * Function to figure out who won the hand
     * @return A string representation of the winning player and hand.
     */
    public String findWinner() {
        HoldemWinChecker winChecker = new HoldemWinChecker();
        winChecker.findWinningHand(activePlayers, board);
        
        List<BettingPlayer> winningPlayers = winChecker.getWinningPlayers();
        String winningRankString = HoldemWinChecker.ranks.get(winChecker.getWinningRank());
        String a = "";
        if(winningRankString.equals("Pair") || winningRankString.equals("Straight") || winningRankString.equals("Flush") || winningRankString.equals("Full House") || winningRankString.equals("Straight Flush")) {
            a = "a ";
        }
        
        String winMessage = "";
        for(BettingPlayer player : winningPlayers) {
            winMessage += String.format("\nThe winner is %s with %s%s\n", player.getUsername(), a, winningRankString);
            player.incrementChips(bettingHelper.getPotSize() / winningPlayers.size());
        }
        System.out.println(winMessage);
        return winMessage;
    }

    @Override
    public ArrayList<Integer> getAvailableSeats() throws RemoteException {
        Collections.sort(availableSeats);
        return availableSeats;
    }
    
    @Override
    public void addClient(IHoldemReceiver client) throws RemoteException {
        clients.add(client);
    }
    
    @Override
    public void removeClient(IHoldemReceiver client) throws RemoteException {
        clients.remove(client);
        System.out.println("Removing holdem client");
    }
    
    public void offerActions() {
        try {
            findClientById(getCurrentPlayer().getUserId()).offerActions(bettingHelper.getAvailableActions());
        }            
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void disableActions(BettingPlayer player) {
        try {
            findClientById(player.getUserId()).disableActions();
        }
        catch(RemoteException ex) {
        
        }
    }
    
    public void updateBoardCards() {
        try {
            for(IHoldemReceiver client : clients) {
                client.updateBoardCards((ArrayList)board);
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    private IHoldemReceiver findClientById(int id) {
        try {
            for(IHoldemReceiver client : clients) {
                if(client.getPlayerId() == id) {
                    return client;
                }
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        throw new IllegalArgumentException("Client with id " + id + " not found.");
    }
}

