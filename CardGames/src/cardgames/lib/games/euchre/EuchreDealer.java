package cardgames.lib.games.euchre;

import cardgames.lib.utilities.*;
import java.util.*;

//******Player Configuration******
// 
//                2
//
//        1               3
//
//                0
//
//********************************
//********Dealing Sequences*******
// 2, 2, 2, 2       3, 3, 3, 3

// 3, 2, 2, 2       2, 3, 3, 3
// 2, 3, 2, 2       3, 2, 3, 3
// 2, 2, 3, 2       3, 3, 2, 3
// 2, 2, 2, 3       3, 3, 3, 2

// 3, 3, 2, 2       2, 2, 3, 3
// 3, 2, 3, 2       2, 3, 2, 3
// 3, 2, 2, 3       2, 3, 3, 2

public class EuchreDealer {
  private final int MIN_MAX_PLAYERS = 4;
  
  private Deck deck;
  private List<Player> players;
  private int currentDealer;
  private int playersTurn;
  private char trump;
  private boolean cardUp;
  
  public EuchreDealer() {
    trump = 0;
    prepareEuchreDeck();
    players = new ArrayList<>(MIN_MAX_PLAYERS);
    determineDealer();
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
//*******************************************************************************    
    // For Testing the euchre game.
    addPlayer(1111, 0, "2, 3, 2, 3");
    addPlayer(2222, 1, "3, 3, 3, 2");
    addPlayer(3333, 2, "3, 2, 2, 3");
    addPlayer(4444, 3, "2, 2, 2, 2");
    
    startNewHand(true);
    
    for(int i = 0; i < 4; i++) {
      System.out.print("Player " + i + ": ");
      for(int cards: players.get(i).getHand()) {
        System.out.print(deck.cardToString(cards));
        System.out.print(", ");
      }
      System.out.println();
    }
    
    deck.print();
    System.out.println("Current Dealer: " + currentDealer);
    System.out.println("Players Turn: " + playersTurn);
//*******************************************************************************
  }
  
  private int changeDealer(int dealer) {
    // Increment the dealer to the next player at the table.
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
    
    dealHands(players.get(currentDealer).getDealSequence());
  }
  
  public void addPlayer(int id, int seat, String dealSequence) {
    players.add(new Player(id, seat, dealSequence));
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
  
  private void dealHands(String dealSequence) {
    if(players.size() == 4){    // Make sure there are 4 players.
      playersTurn = changeDealer(currentDealer);
      // Use the deal sequence that user has chosen.
      int[] sequence = convertSequence(dealSequence);
      
      // Deal the first round of cards.
      dealTheCards(sequence[0], sequence[1], sequence[2], sequence[3]);
      
      // Reverse the sequence and deal the second round of cards
      // to give each player 5 total cards.
      dealSequence = reverseSequence(dealSequence);
      sequence = convertSequence(dealSequence);
      dealTheCards(sequence[0], sequence[1], sequence[2], sequence[3]);
      
      cardUp = true;
    }
  }
  
  private int[] convertSequence(String sequence) {
    // Convert the string sequence into integer numbers.
    int[] deal = new int[4];
    String[] temp = sequence.split(", ");
    
    for(int i = 0; i < 4; i++) {
      deal[i] = Integer.parseInt(temp[i]);
    }
    
    return deal;
  }
  
  private String reverseSequence(String sequence) {
    // Reverse the card deal sequence.
    // Replace all 2's with 3's amd 3's with 2's
    sequence = sequence.replaceAll("2", "T");
    sequence = sequence.replaceAll("3", "2");
    sequence = sequence.replaceAll("T", "3");
    return sequence;
  }
  
  private void dealTheCards(int p1, int p2, int p3, int p4) {
    // Use the values passed in and deal that number of cards to
    // the correct players on the table.
    players.get(playersTurn).giveCard(deck.dealCards(p1));
    playersTurn = changeDealer(playersTurn);
        
    players.get(playersTurn).giveCard(deck.dealCards(p2));
    playersTurn = changeDealer(playersTurn);
    
    players.get(playersTurn).giveCard(deck.dealCards(p3));
    playersTurn = changeDealer(playersTurn);
    
    players.get(playersTurn).giveCard(deck.dealCards(p4));
    playersTurn = changeDealer(playersTurn);
  }
  
  public void passOnCallingTrump() {
    playersTurn = changeDealer(playersTurn);
    
    if(playersTurn == currentDealer) {
      cardUp = false;
    }
  }
  
  public void callTrump(char trump) {
    // Set trump and start the hand
    if(cardUp) {
      this.trump = trump;
    } else {
      
    }
  }
  
  public void getCardToReplace(String card) {
    // If the "top card" is called as trump,
    // have the dealer replace a card from his hand
    // with that top card.
  }
  
//*******************************************************************************  
  
  public char getTrump() {
    return trump;
  }
  
  public int getCurrentDealer() {
    return currentDealer;
  }
  
  public int getCurrentPlayer() {
    return playersTurn;
  }
}