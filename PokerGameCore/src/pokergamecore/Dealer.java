package pokergamecore;
import java.util.*;
import java.io.*;

public class Dealer 
{
    private final int MAX_HANDS = 10;
    
    private Deck deck;
    private WinnerChecker winnerChecker;
    private OutFile outFile;
    private int randomHands;
    
    public Dealer()
    {
        outFile = new OutFile();
        deck = new Deck(outFile);
    }
    
    public void getUserInput()
    {
        Scanner scanner = new Scanner(System.in);
        
        String inString;
        String[] cardStrings;
        int card1, card2, numHands;
        boolean randomChange = false;
        
        System.out.println(String.format("Enter up to %d specific hands to be dealt, separating the cards by a space (Ex. \"AS KD\"). Press Enter without typing anything when finished.\n", MAX_HANDS));
        
        deck.shuffle();
        
        while(deck.getHandsDealt() <= MAX_HANDS)
        {
            System.out.print(String.format("Hand %d: ", deck.getHandsDealt() + 1));
            inString = scanner.nextLine();
            if(inString.trim().equals(""))
                break;
            if(inString.trim().length() != 5)
            {
                System.out.println("Invalid hand");
                continue;
            }
            cardStrings = inString.split(" ");
            
            if(cardStrings[0].equals(cardStrings[1]))
            {
                System.out.println("Invalid hand");
                continue;
            }
            if(deck.cardValid(cardStrings[0]) && deck.cardValid(cardStrings[1]))
            {
                card1 = deck.cardFromString(cardStrings[0]);
                card2 = deck.cardFromString(cardStrings[1]);
                if(deck.inDeck(card1) && deck.inDeck(card2))
                {
                    deck.dealSpecific(card1, card2);
                }
            }
        }
        
        while(true)
        {
            System.out.println("Additional random hands: ");
            inString = scanner.nextLine();
            try
            {
                randomHands = Integer.parseInt(inString);
            }
            catch(Exception ex)
            {
                System.out.println("Please enter a valid number");
                continue;
            }
            
            if(randomHands + deck.getHandsDealt() <= MAX_HANDS)
            {
                deck.dealRandom(randomHands);
                break;
            }
            else
            {
                System.out.println(String.format("Hand limit of %d exceeded, try again.", MAX_HANDS));
                continue;
            }
        }
        
        if(randomHands > 0)
        {
            System.out.println("Do you want different random hands dealt each time?");
            System.out.print(String.format("(Choose no for the same %d hands each time)", randomHands));
            
            while(!inString.equals("Y") && !inString.equals("N"))
            {
                System.out.println();
                System.out.print("Y/N: ");
                inString = scanner.nextLine().trim().toUpperCase();
            }
            if(inString.equals("Y"))
            {
                randomChange = true;
            }
        }
        deck.setGettingUserInput(false);
        
        while(true)
        {
            System.out.print("Number of hands to simulate: ");
            inString = scanner.nextLine();
            try
            {
                numHands = Integer.parseInt(inString);
                runHands(numHands, randomChange);
                break;
            }
            catch(Exception ex)
            {
                System.out.println("Please enter a valid number");
            }   
        }
    }  
    
    public void runHands(int numHands, boolean randomChange)
    {
        winnerChecker = new WinnerChecker(outFile, deck.getHandsDealt());
        List<Integer> specHands = new ArrayList<>();
        int i;
        
        if(randomChange)
        {
            for(i = 0; i < (deck.getHandsDealt() - randomHands) * 2; i++)
            {
                specHands.add(deck.getDealtHandList().get(i));
            }
            
            for(i = 0; i < numHands; i++)
            {
                outFile.addLine("---------------------------------------------------------------");
                deck.collectCards();
                deck.shuffle();
                for(int j = 0; j < specHands.size(); j += 2)
                {
                    deck.dealSpecific(specHands.get(j), specHands.get(j + 1));
                }
                deck.dealRandom(randomHands);
                deck.dealBoard();
                outFile.addLine();
                outFile.addLine("(Find winner)");
            }       
        }
        else
        {
            for(int k : deck.getDealtHandList())
            {
                specHands.add(k);
            }
            
            for(i = 0; i < numHands; i++)
            {
                outFile.addLine("---------------------------------------------------------------");
                deck.collectCards();
                deck.shuffle();
                for(int j = 0; j < specHands.size(); j += 2)
                {
                    deck.dealSpecific(specHands.get(j), specHands.get(j + 1));
                }
                deck.dealBoard();
                outFile.addLine();
                outFile.addLine("(Find winner)");
            }
        }
        System.out.println("(Print win stats)");
        outFile.addTopLine("(Print win stats)");
        
        String filePath = "SimulationResults.txt";
        outFile.writeLinesToFile(filePath);
        try
        {
            Runtime.getRuntime().exec("notepad " + filePath);
        }
        catch(IOException ex)
        {
            System.out.println("Unable to automatically open output file. It can be found in the root directory named SimulationResults.txt.");
            ex.printStackTrace(System.out);
        }
    }
}

