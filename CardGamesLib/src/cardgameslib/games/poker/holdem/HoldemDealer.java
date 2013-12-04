package cardgameslib.games.poker.holdem;

import cardgameslib.textchat.ChatServer;
import cardgameslib.utilities.*;
import cardgameslib.games.poker.betting.*;

import java.util.*;
/**
 * This class handles Textas Hold'em style poker
 * @author Ryan Bickham
 *
 */
public class HoldemDealer {
    private final int MAX_PLAYERS = 9;
    
    private Deck deck;
    private PokerBettingHelper bettingHelper;
    private HoldemWinChecker winChecker;
    //private ChatServer chat;
    
    private List<BettingPlayer> players;
    private List<BettingPlayer> activePlayers;
    
    private List<Integer> board;
    
    private int chipLimit;
    private int bigBlind;
   
    /**
     * Constructor for HoldemDealer
     * @param maxChips int holding the chip limit for the table
     * @param bb int holding the value of the big blind for the table
     */
    public HoldemDealer(int maxChips, int bb) {
        deck = new Deck();
        players = new ArrayList<>(MAX_PLAYERS);
        bettingHelper = new PokerBettingHelper(activePlayers, bigBlind);
        winChecker = new HoldemWinChecker();
        //chat = new ChatServer(8081);
        chipLimit = maxChips;
        bigBlind = bb;

        Random r = new Random();
        Collections.rotate(players, r.nextInt(MAX_PLAYERS));
        
    }
    
    /**
     * Getter to return list of players at table
     * @return List<BettingPlayer>
     */
    public List<BettingPlayer> getPlayers() {
    	return players;
    }
    
    /**
     * Getter to return list of players at table still playing (have not folded yet)
     * @return List<BettingPlayer>
     */
    public List<BettingPlayer> getActivePlayers() {
    	return activePlayers;
    }
    
    /**
     * Getter to return the table
     * @return List<Integer>
     */
    public List<Integer> getBoard() {
    	return board;
    }
    
    /**
     * Getter to return action of last player
     * @return Action
     */
    public Action getLastAction() {
    	return bettingHelper.getLastAction();
    }
    
    /**
     * Function to handle adding a new player to the table
     * @param id int to hold player number
     * @param username String to hold username of user
     * @param seatNum int to hold the seat the player is at
     * @param startingChips int to hold the number of chips the player starts with
     */
    public void addPlayer(int id, String username, int seatNum, int startingChips) {
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
            Collections.sort(players);
        } else {
            throw new IllegalArgumentException("Starting chip count exceeds maximum for this table");
        }
    }
    
    /**
     * Handles removing player from Texas Hold'em table
     * @param id int to hold which player is being removed/leaving
     */
    public void removePlayer(int id) {
        for(BettingPlayer player : players) {
            if(player.getUserId() == id) {
                players.remove(player);
                activePlayers.remove(player);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
    }
    
    /**
     * Function to handle starting a hand of Texas Hold'em
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
    }
    
    /**
     * Function to handle dealing the Texas Hold'em hands
     */
    public void dealHands() {
        List<Integer> cards = deck.dealCards(players.size() * 2);
        int i;
        int j = 0;
        
        for(i = 0; i < players.size(); i++) {
            players.get(i).giveCard(cards.get(j));
            players.get(i).giveCard(cards.get(j + players.size()));
            j++;
        }
        
        for(Player player : players) {    //For testing
            System.out.println(String.format("Player %d's hand is %s %s", player.getSeatNumber(), 
                                            Deck.cardToString(player.getHand().get(0)), Deck.cardToString(player.getHand().get(1))));
        }
    }
    
    /**
     * Function to handle dealing the flop on the board, the first three cards
     */
    public void dealFlopToBoard() {
        int card;
        deck.dealCard(); //Burn
        System.out.printf("\nPot Size: %d\n", getPotSize());
        System.out.print("Board: ");
        for(int i = 0; i < 3; i++) {
            card = deck.dealCard();
            board.add(card);
            System.out.print(Deck.cardToString(card) + " ");
        }
        System.out.print("\n");
        bettingHelper.startNewRound(false);
    }
    
    /**
     * Function to handle dealing the turn and the river to the board, the fourth and fifth cards
     */
    public void dealCardToBoard() {   //For turn and river
        deck.dealCard(); //Burn
        int card = deck.dealCard();
        board.add(card);
        System.out.printf("\nPot Size: %d\n", getPotSize());     
        System.out.print("Board: ");
        for(int boardCard : board) {
            System.out.print(Deck.cardToString(boardCard) + " ");
        }
        System.out.print("\n");
        bettingHelper.startNewRound(false);
    }
    
    /**
     * Function to handle a player taking an action.
     * @param action Action player is taking
     * @param chipAmount int to hold chip amount used in action
     */
    public void takeAction(Action action, int chipAmount) {
        bettingHelper.takeAction(action, chipAmount);
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
    public int getPotSize() {
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
    
    /**
     * Function to figure out who won the hand
     */
    public void findWinner() {
        winChecker.findWinningHand(activePlayers, board);
        
        List<BettingPlayer> winningPlayers = winChecker.getWinningPlayers();
        String winningRank = winChecker.getWinningRank();
        String a = "";
        if(winningRank.equals("Pair") || winningRank.equals("Straight") || winningRank.equals("Flush") || winningRank.equals("Full House") || winningRank.equals("Straight Flush")) {
            a = "a ";
        }
        for(BettingPlayer player : winningPlayers) {
            System.out.printf("\nThe winner is Player %d with %s%s", player.getSeatNumber(), a, winChecker.getWinningRank());
        }
    }
    
    /**
     * Function to send a chat message to a player
     * @param playerId int holding the player who sent the message 
     * @param message message to be sent
     */
    public void sendPlayerMessage(int playerId, String message) {
    	for(BettingPlayer player : players) {
            if(player.getUserId() == playerId) {
            	player.sendMessage(message);
            }
        }
    }
}

