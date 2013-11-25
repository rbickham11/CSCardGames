package cardgameslib.games.poker.holdem;

import cardgameslib.utilities.betting.PokerBettingHelper;
import cardgameslib.utilities.betting.Action;
import cardgameslib.utilities.Player;
import cardgameslib.utilities.Deck;
import cardgameslib.utilities.BettingPlayer;

import java.util.*;

public class HoldemDealer {
    private final int MAX_PLAYERS = 10;
    
    private Deck deck;
    private PokerBettingHelper bettingHelper;
    private HoldemWinChecker winChecker;
    
    private List<BettingPlayer> players;
    private List<BettingPlayer> activePlayers;
    
    private List<Integer> board;
    
    private int chipLimit;    
   
    public HoldemDealer(int maxChips, int bigBlind) {
        deck = new Deck();
        bettingHelper = new PokerBettingHelper(activePlayers, bigBlind);
        winChecker = new HoldemWinChecker();
        
        players = new ArrayList<>(MAX_PLAYERS);
        chipLimit = maxChips;

        Random r = new Random();
        Collections.rotate(players, r.nextInt(MAX_PLAYERS));
        
    }
    
    public void addPlayer(int id, int seatNum, int startingChips) {
        if(startingChips <= chipLimit) {
            players.add(new BettingPlayer(id, seatNum, startingChips));
            Collections.sort(players);
        } else {
            throw new IllegalArgumentException("Starting chip count exceeds maximum for this table");
        }
    }
    
    public void removePlayer(int id) {
        for(BettingPlayer player : players) {
            if(player.getUserId() == id) {
                players.remove(players.indexOf(player));
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
    }
    
    public void startHand() {
        Collections.rotate(players, 1);
        activePlayers = new ArrayList<>(players);
        board = new ArrayList<>();
        deck.collectCards();
        for(BettingPlayer player : players) {
            player.resetHand();
        }
        deck.shuffle();
        System.out.printf("The dealer is Player %d\n", players.get(players.size() - 1).getSeatNumber());
        dealHands();
    }
    
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
                                            deck.cardToString(player.getHand().get(0)), deck.cardToString(player.getHand().get(1))));
        }
    }
    
    public void dealFlopToBoard() {
        int card;
        deck.dealCard(); //Burn
        
        System.out.print("Board: ");
        for(int i = 0; i < 3; i++) {
            card = deck.dealCard();
            board.add(card);
            System.out.print(deck.cardToString(card) + " ");
        }
    }
    
    public void dealCardToBoard() {   //For turn and river
        deck.dealCard(); //Burn
        int card = deck.dealCard();
        board.add(card);
        System.out.print(deck.cardToString(card) + " ");
    }
    
    public void takeAction(Action action, int chipAmount) {
        bettingHelper.takeAction(action, chipAmount);
    }
    
    public boolean bettingComplete() {
        return bettingHelper.bettingComplete();
    }
    
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
}

