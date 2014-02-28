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
    
    private List<BettingPlayer> activePlayers = new ArrayList<>();
    private boolean playerFinished;
    
    
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
    
    public void startHand() {
        activePlayers = new ArrayList<>(players);
        for(BettingPlayer player : players) {
            player.resetCurrentBet();
        }
        dealHands();
    }
    
    public void dealHands() {
        if(deck.getSize() < (DECK_COUNT * 52) / 4) {
            deck.collectCards();
            deck.shuffle();
        }
        
        List<Integer> cards = deck.dealCards(activePlayers.size() * 2 + 2);
        
        for(int i = 0; i < players.size() + 1; i++) {
            if(i == players.size()) {
                dealerHand.add(cards.get(i));
                dealerHand.add(cards.get(i + activePlayers.size()));
            } 
            else {
                activePlayers.get(i).giveCard(cards.get(i));
                activePlayers.get(i).giveCard(cards.get(i + players.size()));
            }
        }
        
        for(BettingPlayer player : activePlayers) {    //For testing
            System.out.printf("Player %d's hand is %s %s\n", player.getSeatNumber(), 
                                            Deck.cardToString(player.getHand().get(0)), Deck.cardToString(player.getHand().get(1)));
        }
        
        System.out.printf("The dealer's upcard is: %s\n", Deck.cardToString(dealerHand.get(0)));
    }
    
    public void takeBet(int seatNumber, int bet) {
        BettingPlayer player = BettingPlayer.getPlayerBySeatNumber(activePlayers, seatNumber);
        if(bet < lowLimit || bet > highLimit) {
            throw new IllegalArgumentException("Bet is outside limits for this table.");
        }
        if(bet <= player.getChips()) {
            player.setCurrentBet(bet);
            player.decrementChips(bet);
        }
        else {
            throw new IllegalArgumentException("Bet amount exceeds chip count.");
        }  
    }
    
    public void takeAction(BlackjackAction action) {
        BettingPlayer currentPlayer = activePlayers.get(0);
        if(currentPlayer.getHand().size() == 2) {
            playerFinished = false;
        }    
        switch(action) {
            case HIT:
                currentPlayer.giveCard(deck.dealCard());
                break;
            case STAND:
                playerFinished = true;
                Collections.rotate(players, -1);
                break;
            case DOUBLEDOWN:
                currentPlayer.decrementChips(currentPlayer.getCurrentBet());
                currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() * 2);
                playerFinished = true;
                Collections.rotate(players, -1);
                break;
            case SPLIT:
                break;
            case SURRENDER:
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
    
    public boolean getPlayerFinished() {return playerFinished;}
}
