package cardgameslib.games.poker.holdem;

import cardgameslib.utilities.*;
import cardgameslib.games.poker.betting.*;

import java.util.*;
/**
 * This class handles Texas Hold'em style poker
 * @author Ryan Bickham
 *
 */
public class HoldemDealer {
    private static final int MAX_PLAYERS = 9;
   
    private final Deck deck = new Deck();
    private final List<BettingPlayer> players = new ArrayList<>(MAX_PLAYERS);

    private final int chipLimit;
    private final int bigBlind;
    
    private PokerBettingHelper bettingHelper;
    private List<BettingPlayer> activePlayers;
    private List<Integer> board;
   
    /**
     * Constructor for HoldemDealer
     * @param maxChips int holding the chip limit for the table
     * @param bb int holding the value of the big blind for the table
     */
    public HoldemDealer(int maxChips, int bb) {
        chipLimit = maxChips;
        bigBlind = bb;
        bettingHelper = new PokerBettingHelper(activePlayers, bigBlind);

        Random r = new Random();
        Collections.rotate(players, r.nextInt(MAX_PLAYERS));
        
        System.out.println("HoldemDealer Instance Created!");
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
    public PokerAction getLastAction() {
    	return bettingHelper.getLastAction();
    }
    
    public boolean getFirstActed() {
    	return bettingHelper.getFirstActed();
    }
    /**
     * Function to handle adding a new player to the table
     * @param id int to hold player number
     * @param seatNum int to hold the seat the player is at
     * @param startingChips int to hold the number of chips the player starts with
     */
    public void addPlayer(int id, int seatNum, int startingChips) {
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
            players.add(new BettingPlayer(id, seatNum, startingChips));
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
     * Deals a single card to the board (for turn and river)
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
     * Handles a player taking an action.
     * @param action Action player is taking
     * @param chipAmount int to hold chip amount used in action
     */
    public void takeAction(PokerAction action, int chipAmount) {
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
        HoldemWinChecker winChecker = new HoldemWinChecker();
        winChecker.findWinningHand(activePlayers, board);
        
        List<BettingPlayer> winningPlayers = winChecker.getWinningPlayers();
        String winningRankString = HoldemWinChecker.ranks.get(winChecker.getWinningRank());
        String a = "";
        if(winningRankString.equals("Pair") || winningRankString.equals("Straight") || winningRankString.equals("Flush") || winningRankString.equals("Full House") || winningRankString.equals("Straight Flush")) {
            a = "a ";
        }
        for(BettingPlayer player : winningPlayers) {
            System.out.printf("\nThe winner is Player %d with %s%s", player.getSeatNumber(), a, winningRankString);
        }
    }
}

