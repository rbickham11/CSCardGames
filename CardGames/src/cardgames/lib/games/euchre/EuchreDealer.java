package cardgames.lib.games.euchre;

import cardgames.lib.utilities.*;
import java.util.*;

public class EuchreDealer {
  private final int MIN_MAX_PLAYERS = 4;
  
  private Deck deck;
  private List<Player> players;
  private int currentDealer;
  
  public EuchreDealer() {
    prepareEuchreDeck();
    players = new ArrayList<>(MIN_MAX_PLAYERS);
    
    determineDealer();
    startNewHand(true);
    dealHands();
  }
  
  // Create a new Euchre Deck.
  private void prepareEuchreDeck() {
    List<Integer> euchreCards = new ArrayList<>();
    for(int i = 0; i < 52; i++) {
        if(i % 13 > 6) {        //If the card is a 9, T, J, Q, K, A
            euchreCards.add(i);
        }
    }
    deck = new Deck(euchreCards);
    deck.shuffle();
  }
  
  // Determine the first dealer for the game.
  private void determineDealer() {
    Random rand = new Random();
    int startDetermine = rand.nextInt(MIN_MAX_PLAYERS);
    int card = deck.dealCard();
    
    // Deal until you see a "Black Jack" card.
    while(card != 35 && card != 48) {
      startDetermine = changeDealer(startDetermine);
      card = deck.dealCard();
    }
    
    currentDealer = startDetermine;     // Set the current dealer.
    deck.collectCards();                // Collect the cards.
    deck.shuffle();                     // Shuffle the deck.
  }
  
  private int changeDealer(int dealer) {
    dealer++;
    
    if(dealer >= MIN_MAX_PLAYERS) {
      dealer = 0;
    }
    return dealer;
  }
  
  public void startNewHand(boolean newGame) {
    // Only change dealer if this is the beginning
    // of a new game.
    if(!newGame) {
      changeDealer(currentDealer);
    }
    
    deck.collectCards();
    deck.shuffle();
    dealHands();
  }
  
  public void addPlayer(int id, int seat) {
    players.add(new Player(id, seat));
    Collections.sort(players);
  }
  
  public void removePlayer(int id) {
    for(Player player : players) {
      if(player.getUserId() == id) {
        players.set(players.indexOf(player), null);
        return;
      }
    }
    throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
  }
  
  private void dealHands() {
    Random rand = new Random();
    int dealTo = changeDealer(currentDealer);
    
    for(int i = 0; i < 25; i++){
      System.out.println(rand.nextInt(4-2) + 2);
    }
  }
}