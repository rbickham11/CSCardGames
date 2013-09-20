package cardgames.lib.utilities;

import java.util.*;
public class Deck 
{
    private final int DECK_SIZE = 52;
    private final List<Character> cardValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    private final List<Character> suitValues = Arrays.asList('D', 'H', 'C', 'S');
    
    private List<Integer> deck;
    private List<Integer> dealtCards;
    
    public Deck()
    {
        deck = new ArrayList<>();
        dealtCards = new ArrayList<>();
        
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
    }
    
    public List<Integer> dealtCards()
    {
        return dealtCards;
    }

    public void print()
    {
        for(int card: deck)
            System.out.println(cardToString(card));
    }
    
    public void shuffle()
    {
        Random random = new Random();
        int i, j, temp;
        
        for(i = deck.size() - 1; i > 0; i--)
        {
            j = random.nextInt(i + 1);
            temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);     
        }
    }
    
    public void collectCards()
    {
        deck = new ArrayList<>();
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
        dealtCards = new ArrayList<>();
    }
    
    public void dealCard()
    {
        dealtCards.add(deck.get(0));
        deck.remove(0);
    }
    
    public void dealCards(int numCards)
    {
        for(int i = 0; i < numCards; i++)
        {
            dealtCards.add(deck.get(0));
            deck.remove(0);
        }
    }
    
    public void dealSpecific(int card)
    {
        deck.remove(new Integer(card));
        dealtCards.add(card);
    }
    
    public boolean inDeck(int card)
    {
        if(!deck.contains(card))
        {
            System.out.println(String.format("%s was already dealt", cardToString(card)));
            return false;
        }
        return true;
    }
    public int cardFromString(String card)
    {
        return 13 * suitValues.indexOf(card.charAt(1)) + cardValues.indexOf(card.charAt(0));
    }
    
    public String cardToString(int card)
    {
        return String.format("%c%c", cardValues.get(card % 13), suitValues.get(card / 13));
    }
}
