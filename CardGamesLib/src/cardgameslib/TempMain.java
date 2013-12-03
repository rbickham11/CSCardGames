package cardgameslib;

import cardgameslib.games.poker.holdem.HoldemDealer;
import cardgameslib.utilities.*;
import cardgameslib.games.poker.betting.*;
import java.util.*;

public class TempMain {
    private HoldemDealer dealer = new HoldemDealer(20000, 200);
    
    public static void main(String[] args) {
        TempMain main = new TempMain();
        main.runPokerGame();

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
    
    public void runPokerGame() {
        Scanner s = new Scanner(System.in);
        
        for(int i = 1; i < 7; i++) {
            dealer.addPlayer(1000 + i, i, 13000 + 1000 * i);
        }
        
        System.out.println("Welcome to Ryan's awesome poker game!");
        System.out.println("Use actions B (Bet), C (Call), X (Check), F (Fold), and R (Raise) for betting");
        
        char yn = 'Y';
        while(Character.toUpperCase(yn) != 'N') {
            dealer.startHand();
            startBettingRound();
            
            if(!dealer.isWinner()) { 
                dealer.dealFlopToBoard();
                startBettingRound();
            }
            if(!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound();
            }
            if(!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound();
            }
            
            if(!dealer.isWinner()) {
                dealer.findWinner();
            }
            
            System.out.print("\nDo you want to start another hand? (Y/N): ");
            yn = s.nextLine().charAt(0);
        }
        s.close();
    }
    
    public void startBettingRound() {          
        Scanner s = new Scanner(System.in);
        char charAction;
        Action action;
        int chipAmount = 0;
        BettingPlayer activePlayer;
        
        
        while(!dealer.bettingComplete()) {
            activePlayer = dealer.getCurrentPlayer();
            System.out.printf("Current bet is: %d\n", dealer.getCurrentBet());
            System.out.printf("Player %d (%s %s) - %d Chips. Enter betting action: ", activePlayer.getSeatNumber(), 
                                Deck.cardToString(activePlayer.getHand().get(0)), 
                                Deck.cardToString(activePlayer.getHand().get(1)),
                                activePlayer.getChips());
            charAction = s.next().charAt(0);
            switch(Character.toUpperCase(charAction)) {
                case 'B' :
                    action = Action.BET;
                    System.out.print("Bet amount: ");
                    chipAmount = s.nextInt();
                    break;
                case 'C' :
                    action = Action.CALL;
                    break;
                case 'X' :
                    action = Action.CHECK;
                    break;
                case 'F' :
                    action = Action.FOLD;
                    break;
                case 'R' :
                    action = Action.RAISE;                    
                    System.out.print("Raise amount: ");
                    chipAmount = s.nextInt(); 
                    break;
                default :
                    System.out.println("Invalid Action!");
                    continue;
            }
            try {
                dealer.takeAction(action, chipAmount);
            }
            catch(IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if(dealer.isWinner()) {
            System.out.printf("The winner is Player %d\n", dealer.getCurrentPlayer().getSeatNumber());
        }       
    }
}
