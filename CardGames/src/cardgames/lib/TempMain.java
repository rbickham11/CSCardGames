package cardgames.lib;

import cardgames.lib.utilities.Deck;
public class TempMain 
{
    public static void main(String[] args) 
    {
        Deck deck = new Deck();
        deck.print();
        deck.shuffle();
        deck.print();
    }
}