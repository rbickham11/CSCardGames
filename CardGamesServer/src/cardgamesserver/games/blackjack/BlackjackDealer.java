package cardgamesserver.games.blackjack;

import cardgameslib.games.IBlackjackDealer;
import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.Deck;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Main blackjack game class
 * @author Ryan
 */
public class BlackjackDealer extends UnicastRemoteObject implements IBlackjackDealer {
    public static final int MAX_PLAYERS = 5;
    public static final int DECK_COUNT = 6;
    
    private final int lowLimit;
    private final int highLimit;
    private final Deck deck = new Deck();
    private final List<BettingPlayer> players = new ArrayList<>();
    private final List<Integer> dealerHand = new ArrayList<>();
    
    private final Map<Integer, Integer[]> handValues = new HashMap<>();

    
    private List<BettingPlayer> activePlayers = new ArrayList<>();
    private boolean playerFinished;
    
    
    public BlackjackDealer(int lowLimit, int highLimit) throws RemoteException {
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
        for(int i = 1; i < DECK_COUNT; i++) {
            deck.addDeck();
        }
        deck.shuffle();
        handValues.put(0, new Integer[] {0, 0});  //Dealer hand
    }
    
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
        players.add(new BettingPlayer(id, username, seatNum, startingChips));
        handValues.put(seatNum, new Integer[] {0, 0});
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
        for(BettingPlayer player : activePlayers){
            player.resetHand();
            player.resetCurrentBet();
        }
        dealerHand.clear();
        for(Integer key : handValues.keySet()) {
            handValues.get(key)[0] = 0;
            handValues.get(key)[1] = 0;
        }
    }
    public void dealHands() {
        if(deck.getSize() < (DECK_COUNT * 52) / 4) {
            deck.collectCards();
            deck.shuffle();
        }
        
        List<Integer> cards = deck.dealCards(activePlayers.size() * 2 + 2);
        BettingPlayer player;
        
        for(int i = 0; i < activePlayers.size() + 1; i++) {
            if(i == players.size()) {
                dealerHand.add(cards.get(i));
                dealerHand.add(cards.get(i + activePlayers.size()));
                incrementHandValue(0, cards.get(i));
                incrementHandValue(0, cards.get(i + activePlayers.size()));
            } 
            else {
                player = activePlayers.get(i);
                player.giveCard(cards.get(i));
                player.giveCard(cards.get(i + activePlayers.size()));
                incrementHandValue(player.getSeatNumber(), cards.get(i));
                incrementHandValue(player.getSeatNumber(), cards.get(i + activePlayers.size()));
            }
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
        int card;
        switch(action) {
            case HIT:
                card = deck.dealCard();
                currentPlayer.giveCard(card);
                incrementHandValue(currentPlayer.getSeatNumber(), card);
                if(handValues.get(currentPlayer.getSeatNumber())[0] > 21) {
                    playerFinished = true;
                    activePlayers.remove(currentPlayer);
                }
                break;
            case STAND:
                playerFinished = true;
                Collections.rotate(activePlayers, -1);
                break;
            case DOUBLEDOWN:
                card = deck.dealCard();
                currentPlayer.giveCard(card);
                incrementHandValue(currentPlayer.getSeatNumber(), card);
                currentPlayer.decrementChips(currentPlayer.getCurrentBet());
                currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() * 2);
                playerFinished = true;
                Collections.rotate(activePlayers, -1);
                break;
            case SPLIT:
                break;
            case SURRENDER:
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
    
    public String getHandValueString (int seatNumber) {
        int lowAceValue = handValues.get(seatNumber)[0];
        int highAceValue = handValues.get(seatNumber)[1];
        
        if(lowAceValue != highAceValue && highAceValue <= 21) {
            return String.format("%d/%d", lowAceValue, highAceValue);
        }
        else if(lowAceValue > 21) {
            return Integer.toString(lowAceValue) + " -Bust";
        }
        else {
            return Integer.toString(lowAceValue);
        }
    }
    
    public void completeHand() {
        boolean dealerBust = false;
        int dealerValue;
        
        while(true) {
            int card = deck.dealCard();
            dealerHand.add(card);
            incrementHandValue(0, card);
            if(handValues.get(0)[1] >= 17 && handValues.get(0)[1] <= 21) {
                dealerValue = handValues.get(0)[1];
                break;
            }
            if(handValues.get(0)[0] >= 17) {
                dealerValue = handValues.get(0)[0];
                break;
            }
        }
        if(dealerValue > 21) dealerBust = true;
        
        for(BettingPlayer player : activePlayers) {
            if(dealerBust) {
                player.incrementChips(player.getCurrentBet() * 2);
            }
            else {
                int playerValue;
                if(handValues.get(player.getSeatNumber())[1] > 21) {
                    playerValue = handValues.get(player.getSeatNumber())[0];
                }
                else {
                    playerValue = handValues.get(player.getSeatNumber())[1];
                }
                if(playerValue > dealerValue) {
                    player.incrementChips(player.getCurrentBet() * 2);
                }
                else if(playerValue == dealerValue) {
                    player.incrementChips(player.getCurrentBet());
                }
            }
        }
    }
    
    private void incrementHandValue(int seatNumber, int card) {
        int rawValue = card % 13;
        int blackjackValue; 
        if(rawValue < 8) { //If the card is a 2 - 9
            blackjackValue = rawValue + 2;
        }
        else if(rawValue == 12) { //Ace
            blackjackValue = 1;
        }
        else //T, J, Q, K
            blackjackValue = 10;

        if(blackjackValue == 1 && handValues.get(seatNumber)[0] == handValues.get(seatNumber)[1]) {
            handValues.get(seatNumber)[1] += 11;
        }
        else {
            handValues.get(seatNumber)[1] += blackjackValue;
        }
        handValues.get(seatNumber)[0] += blackjackValue;

    }
    
    public List<BettingPlayer> getActivePlayers() {return activePlayers;}
    public List<Integer> getDealerHand() {return dealerHand;}
    public boolean getPlayerFinished() {return playerFinished;}
}
