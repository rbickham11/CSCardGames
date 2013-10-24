package cardgames.lib.games.poker.holdem;

import cardgames.lib.utilities.*;
import cardgames.lib.utilities.betting.*;

import java.util.*;

public class HoldemDealer {
    private final int MAX_PLAYERS = 10;
    
    private Deck deck;
    private PokerBettingHelper bettingHelper;
    private List<BettingPlayer> players;
    private List<BettingPlayer> activePlayers;
    
    private int chipLimit;
    private int currentDealer;
    
    public HoldemDealer(int maxChips) {
        deck = new Deck();
        players = new ArrayList<>(MAX_PLAYERS);
        chipLimit = maxChips;
        
        Random r = new Random();
        currentDealer = 0;
        //currentDealer = r.nextInt(MAX_PLAYERS); 
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
                players.set(players.indexOf(player), null);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
    }
    
    public void startHand() {
        deck.collectCards();
        deck.shuffle();
        dealHands();
    }
    
    public void dealHands() {
        List<Integer> cards = deck.dealCards(players.size() * 2);
        int i;
        int j = 0;
        
        for(i = currentDealer + 1; i < players.size(); i++) {
            players.get(i).giveCard(cards.get(j));
            players.get(i).giveCard(cards.get(j + players.size()));
            j++;
        }
        for(i = 0; i <= currentDealer; i++) {
            players.get(i).giveCard(cards.get(j));
            players.get(i).giveCard(cards.get(j + players.size()));
            j++;
        }
        
        for(Player player : players) {    //For testing
            System.out.println(String.format("Player %d's hand is %s %s", player.getSeatNumber(), 
                                            deck.cardToString(player.getHand().get(0)), deck.cardToString(player.getHand().get(1))));
        }
    }
    
    public void takeBettingAction(Action action, int chipAmount) {
        switch(action) {
            case BET:
            case CALL:
            case CHECK:
            case FOLD:
            case RAISE:
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
}

