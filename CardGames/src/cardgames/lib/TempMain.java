package cardgames.lib;

import cardgames.lib.games.poker.holdem.*;
import java.util.*;

public class TempMain {
    public static void main(String[] args) {
        //Please comment this out instead of deleting when doing other stuff!
        Scanner s = new Scanner(System.in);
        
        HoldemDealer dealer = new HoldemDealer(10000);
        for(int i = 1; i < 7; i++) {
            dealer.addPlayer(1000 + i, i, 3000 + 1000 * i);
        }
        
        char yn = 'Y';
        while(Character.toUpperCase(yn) != 'N') {
            dealer.startHand();
            dealer.dealFlopToBoard();
            dealer.dealCardToBoard();
            dealer.dealCardToBoard();
            dealer.printBoard();
            dealer.findWinner();
            
            System.out.print("\nDo you want to start another hand? (Y/N): ");
            yn = s.nextLine().charAt(0);
        }
        //******************************************************************
    }
}
