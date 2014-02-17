package cardgameslib;

import cardgameslib.games.poker.holdem.HoldemDealer;
import cardgameslib.utilities.*;
import cardgameslib.games.poker.betting.*;
import cardgameslib.games.euchre.*;
import java.util.*;

/**
 * Main class to run the project
 *
 * @author Ryan Bickham
 *
 */
public class TempMain {

    private HoldemDealer dealer = new HoldemDealer(20000, 200);

    public static void main(String[] args) {
//        TempMain main = new TempMain();
//        main.runPokerGame();

        //******************************************************************
        EuchreDealer euchre = new EuchreDealer();
        Scanner temp = new Scanner(System.in);

        euchre.addPlayer(1111, 0, "2, 3, 2, 3");
        euchre.addPlayer(2222, 1, "3, 3, 3, 2");
        euchre.addPlayer(3333, 2, "3, 2, 2, 3");
        euchre.addPlayer(4444, 3, "2, 2, 2, 2");

        euchre.startNewEuchreGame();
        euchre.displayPlayersHands();
        System.out.println("\nTop Card: " + euchre.getTopCard());
        System.out.println("Current Dealer: " + euchre.getCurrentDealer());
        System.out.println();
        System.out.println("Call Trump: 1       Pass: 0");
        System.out.println();

        while (euchre.getTrump() == -1) {
            int choice;
            int trump;

            boolean alone;

            System.out.print("Player " + euchre.getCurrentPlayer() + "'s action: ");
            choice = temp.nextInt();
            if (choice == 0) {
                euchre.passOnCallingTrump();
            } else if (choice == 1) {
                System.out.print("Trump Choice (Clubs = 0, Diamods = 1, Spades = 2, Hearts = 3): ");
                trump = temp.nextInt();
                System.out.print("Alone (True/False)? ");
                alone = temp.nextBoolean();
                euchre.callTrump(trump, alone);
            }
        }

        System.out.println("Type index of card to play. \n");
        for (int i = 0; i < 5; i++) {
            int count = 0;
            while (count < 4) {
                int card = 0;
                System.out.print("Player " + euchre.getCurrentPlayer() + ": ");
                card = temp.nextInt();
                euchre.cardPlayed(card);
                count += 1;
            }
            euchre.displayPlayersHands();
        }
        euchre.startNewHand(false);
    }

    /**
     * This function begins the poker game
     */
    public void runPokerGame() {
        Scanner s = new Scanner(System.in);

        for (int i = 1; i < 7; i++) {
            dealer.addPlayer(1000 + i, "username", i, 13000 + 1000 * i);
        }

        System.out.println("Welcome to Ryan's awesome poker game!");
        System.out.println("Use actions B (Bet), C (Call), X (Check), F (Fold), and R (Raise) for betting");

        char yn = 'Y';
        while (Character.toUpperCase(yn) != 'N') {
            dealer.startHand();
            startBettingRound();

            if (!dealer.isWinner()) {
                dealer.dealFlopToBoard();
                startBettingRound();
            }
            if (!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound();
            }
            if (!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound();
            }

            if (!dealer.isWinner()) {
                dealer.findWinner();
            }

            System.out.print("\nDo you want to start another hand? (Y/N): ");
            yn = s.nextLine().charAt(0);
        }
        s.close();
    }

    /**
     * This function begins a round of betting
     */
    public void startBettingRound() {
        Scanner s = new Scanner(System.in);
        char charAction;
        Action action;
        int chipAmount = 0;
        BettingPlayer activePlayer;

        while (!dealer.bettingComplete()) {
            activePlayer = dealer.getCurrentPlayer();
            System.out.printf("Current bet is: %d\n", dealer.getCurrentBet());
            System.out.printf("Player %d (%s %s) - %d Chips. Enter betting action: ", activePlayer.getSeatNumber(),
                    Deck.cardToString(activePlayer.getHand().get(0)),
                    Deck.cardToString(activePlayer.getHand().get(1)),
                    activePlayer.getChips());
            charAction = s.next().charAt(0);
            switch (Character.toUpperCase(charAction)) {
                case 'B':
                    action = Action.BET;
                    System.out.print("Bet amount: ");
                    chipAmount = s.nextInt();
                    break;
                case 'C':
                    action = Action.CALL;
                    break;
                case 'X':
                    action = Action.CHECK;
                    break;
                case 'F':
                    action = Action.FOLD;
                    break;
                case 'R':
                    action = Action.RAISE;
                    System.out.print("Raise amount: ");
                    chipAmount = s.nextInt();
                    break;
                default:
                    System.out.println("Invalid Action!");
                    continue;
            }
            try {
                dealer.takeAction(action, chipAmount);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (dealer.isWinner()) {
            System.out.printf("The winner is Player %d\n", dealer.getCurrentPlayer().getSeatNumber());
        }
    }
}
