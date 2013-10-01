package cardgames.lib.utilities;

import java.util.*;

public class Deck {
    private final List<Character> cardValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    private final List<Character> suitValues = Arrays.asList('D', 'H', 'C', 'S');
    
    private List<Integer> deck;
    private List<Integer> dealtCards;
    private int DECK_SIZE = 52;
    
    //For initializing the standard 52 card deck.
    public Deck() {                         
        deck = new ArrayList<>();
        dealtCards = new ArrayList<>();
        
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
    }

    //For initializing the deck with a custom selection of cards from the standard deck.
    public Deck(List<Integer> customDeck) {
        for(int card: customDeck) {
            if(card < 0 || card > 51) {
                throw new IllegalArgumentException("One or more cards in the requested deck is invalid");
            }
        }
        deck = new ArrayList<>(customDeck); 
        dealtCards = new ArrayList<>(); 
        DECK_SIZE = deck.size();
    }
    
    //Adds another set of the standard 52 cards to the current deck (for using multiple decks).
    public void addDeck() {
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
    }
    
    public void print() {
        for(int card: deck)
            System.out.println(cardToString(card));
    }
    
    public void shuffle() {
        Random random = new Random();
        int i, j, temp;
        
        for(i = DECK_SIZE - 1; i > 0; i--) {
            j = random.nextInt(i + 1);
            temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);     
        }
    }
    
    public void collectCards() {
      // Add the dealt cards back to the deck.
      for (int i = 0; i < dealtCards.size(); i++) {
        deck.add(dealtCards.get(i));
      }
      dealtCards = new ArrayList<>();   // Re-initialize dealtCards list.
    }
    
    public int dealCard() {
        int card = deck.get(0);
        dealtCards.add(card);
        deck.remove(0);
        return card;
    }
    
    public List<Integer> dealCards(int numCards) {
        List<Integer> cards = new ArrayList<>();
        for(int i = 0; i < numCards; i++) {
            dealtCards.add(deck.get(0));
            cards.add(deck.get(0));
            deck.remove(0);
        }
        return cards;
    }
    
    public void dealSpecific(int card) {
        if(validCard(card) && inDeck(card)) {
            deck.remove(new Integer(card));
            dealtCards.add(card);
        }
    }
    
    public boolean validCard(int card) {
        if(card < 0 || card > 51) {
            throw new IllegalArgumentException(String.format("%d does not match a valid card", card));
        }
        return true;
    }
    
    public boolean inDeck(int card) {
        if(!deck.contains(card)) {
            throw new IllegalArgumentException(String.format("%s was already dealt or is not a member of this deck.", cardToString(card)));
        }
        return true;
    }
    public int cardFromString(String card) {
        return 13 * suitValues.indexOf(card.charAt(1)) + cardValues.indexOf(card.charAt(0));
    }
    
    public String cardToString(int card) {
        return String.format("%c%c", cardValues.get(card % 13), suitValues.get(card / 13));
    }
}
