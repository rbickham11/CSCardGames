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
    private int nextToAct;
    
    public HoldemDealer(int maxChips) {
        deck = new Deck();
        players = new ArrayList<>(MAX_PLAYERS);
        chipLimit = maxChips;
        bettingHelper = new PokerBettingHelper();
        
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
        activePlayers = new ArrayList<>(players);
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
    
    public void takeNonBettingAction(Action action) {
        BettingPlayer player = activePlayers.get(nextToAct);
        
        switch(action) {
            case CALL:
                bettingHelper.call(player);
                break;
            case CHECK:
                break;
            case FOLD:
                activePlayers.remove(player);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        nextToAct++;
    }
    
    public void takeBettingAction(Action action, int chipAmount) {
        BettingPlayer player = activePlayers.get(nextToAct);
        
        switch(action) {
            case BET:
                bettingHelper.bet(player, chipAmount);
                break;
            case RAISE:
                bettingHelper.raise(player, chipAmount);
            default:
                throw new IllegalArgumentException("Invalid action");
        }
        nextToAct++;
    }
}

