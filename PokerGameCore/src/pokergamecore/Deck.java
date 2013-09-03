package pokergamecore;

import java.util.*;
public class Deck 
{
    private final int DECK_SIZE = 52;
    private final List<Character> cardValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    private final List<Character> suitValues = Arrays.asList('D', 'H', 'C', 'S');
    
    private OutFile outFile;
    private List<Integer> deck;
    private List<Integer> dealtHandList;
    private List<Integer> board;
    private int handsDealt;
    private boolean gettingUserInput;
    
    public Deck(OutFile file)
    {
        deck = new ArrayList<>();
        dealtHandList = new ArrayList<>();
        board = new ArrayList<>();
        outFile = file;
        gettingUserInput = true;
        handsDealt = 0;
        
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
    }
    
    public int getHandsDealt()
    {
        return handsDealt;
    }
    
    public List<Integer> getDealtHandList()
    {
        return dealtHandList;
    }
    
    public void setGettingUserInput(boolean value)
    {
        gettingUserInput = value;
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
        board = new ArrayList<>();
        for(int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
        handsDealt = 0;
        dealtHandList = new ArrayList<>();
    }
    
    public void dealRandom(int numHands)
    {
        int card1, card2;
        for(int i = 0; i < numHands; i++)
        {
            handsDealt++;
            card1 = deck.get(0);
            deck.remove(0);
            dealtHandList.add(card1);
            card2 = deck.get(0);
            deck.remove(0);
            dealtHandList.add(card2);
            
            if(gettingUserInput)
                System.out.println(String.format("Player %d's hand is: %s %s", handsDealt, cardToString(card1), cardToString(card2)));
            else
                outFile.addLine(String.format("Player %d's hand is: %s %s", handsDealt, cardToString(card1), cardToString(card2)));
        }
    }
    
    public void dealSpecific(int card1, int card2)
    {
        handsDealt++;
        deck.remove(new Integer(card1));
        deck.remove(new Integer(card2));
        dealtHandList.add(card1);
        dealtHandList.add(card2);
        
        if(gettingUserInput)
            System.out.println(String.format("Player %d's hand is: %s %s", handsDealt, cardToString(card1), cardToString(card2)));
        else
            outFile.addLine(String.format("Player %d's hand is: %s %s", handsDealt, cardToString(card1), cardToString(card2)));
    }
    
    public void dealBoard()
    {
        int card;
        
        outFile.appendLine("Board: ");
        for(int i = 0; i < 8; i++)
        {
            if(i == 0 || i == 4 || i == 6) //Burn Cards
                deck.remove(0);
            else
            {
                card = deck.get(0);
                deck.remove(0);
                board.add(card);
                outFile.appendLine(String.format("%s ", cardToString(card)));
            }
        }
        outFile.addLine();
    }
    
    public boolean cardValid(String card)
    {
        boolean valid = true;
        
        if(!cardValues.contains(card.charAt(0)))
            valid = false;
        else if(!suitValues.contains(card.charAt(1)))
            valid = false;
        
        if(!valid)
        {
            System.out.println(String.format("%s is invalid", card));
            return false;
        }
        return true;
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
