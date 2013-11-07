package cardgameslib;

import cardgameslib.games.poker.holdem.HoldemDealer;
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
        
//        EuchreDealer euchre = new EuchreDealer();
//        Scanner temp = new Scanner(System.in);
//        
//        while (euchre.getTrump() == 0) {
//          int choice;
//          char trump;
//          
//          System.out.print("Player " + euchre.getCurrentPlayer() + "'s action: ");
//          choice = temp.nextInt();
//          if (choice == 0) {
//            euchre.passOnCallingTrump();
//          } else if (choice == 1) {
//            System.out.print("Trump Choice: ");
//            trump = temp.next().charAt(0);
//            euchre.callTrump(trump);
//          }
//        }
    }
}
