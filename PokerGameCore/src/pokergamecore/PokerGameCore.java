package pokergamecore;

public class PokerGameCore 
{
    public static void main(String[] args) 
    {
        OutFile outFile = new OutFile();
        Deck deck = new Deck(outFile);
        deck.print();
        deck.shuffle();
        System.out.println("Sorted deck:");
        deck.print();
        deck.dealRandom(1);
    }
}
