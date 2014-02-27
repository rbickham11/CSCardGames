package cardgameslib.games.blackjack;

import java.util.*;
import cardgameslib.utilities.*;

/**
 * Main blackjack game class
 * @author Ryan
 */
public class BlackjackDealer {
    private static final int MAX_PLAYERS = 5;
    private static final int DECK_COUNT = 6;
    
    private final int lowLimit;
    private final int highLimit;
    private final Deck deck = new Deck();
    private final List<BettingPlayer> players = new ArrayList<>();
    private final List<Integer> dealerHand = new ArrayList<>();
    
    public BlackjackDealer(int lowLimit, int highLimit) {
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
        for(int i = 1; i < DECK_COUNT; i++) {
            deck.addDeck();
        }
        deck.shuffle();
    }
    
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
        players.add(new BettingPlayer(id, seatNum, startingChips));
        Collections.sort(players);
    }
    
    public void dealHands() {
        if(deck.getSize() < (DECK_COUNT * 52) / 4) {
            deck.collectCards();
            deck.shuffle();
        }
        
        List<Integer> cards = deck.dealCards(players.size() * 2 + 2);
        
        for(int i = 0; i < players.size() + 1; i++) {
            if(i == players.size()) {
                dealerHand.add(cards.get(i));
                dealerHand.add(cards.get(i + players.size()));
            } 
            else {
                players.get(i).giveCard(cards.get(i));
                players.get(i).giveCard(cards.get(i + players.size()));
            }
        }
        
        for(Player player : players) {    //For testing
            System.out.printf("Player %d's hand is %s %s\n", player.getSeatNumber(), 
                                            Deck.cardToString(player.getHand().get(0)), Deck.cardToString(player.getHand().get(1)));
        }
        
        System.out.printf("The dealer's upcard is: %s\n", Deck.cardToString(dealerHand.get(0)));
    }
}
