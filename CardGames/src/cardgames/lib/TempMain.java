package cardgames.lib;

import cardgames.lib.utilities.Deck;
import java.util.*;

public class TempMain {
    public static void main(String[] args) {
        List<Integer> euchreCards = new ArrayList<>();
        for(int i = 0; i < 52; i++) {
            if(i % 13 > 6) {        //If the card is a 9, T, J, Q, K, A
                euchreCards.add(i);
            }
        }

        Deck euchreDeck = new Deck(euchreCards);
        System.out.println("Unshuffled Euchre Deck: ");
        euchreDeck.print();
        
        euchreDeck.shuffle();
        System.out.println("Shuffled Euchre Deck: ");
        euchreDeck.print();
    }
}
