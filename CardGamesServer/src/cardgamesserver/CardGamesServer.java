package cardgamesserver;

import cardgameslib.utilities.PokerAction;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;

import cardgameslib.*;
import cardgamesserver.games.blackjack.BlackjackAction;
import cardgamesserver.games.blackjack.BlackjackDealer;
import cardgamesserver.games.euchre.EuchreDealer;
import cardgamesserver.games.poker.holdem.HoldemDealer;
import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.Deck;

/**
 * Main class that starts the RMI registry. Also contains test console versions
 * of implemented games.
 * @author Ryan Bickham, Andrew Haeger
 *
 */
public class CardGamesServer {
    public static final int PORT = 1099;
    private static Registry registry;
    
    /**
     * Server side entry point. Initializes table manager and adds initial tables. 
     * @param args
     * @throws RemoteException
     * @throws AlreadyBoundException 
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        CardGamesServer server = new CardGamesServer();
        //server.runPokerGame();
        //server.runEuchreGame();
        //server.runBlackjackGame();
        
        registry = LocateRegistry.createRegistry(PORT);
        
        TableManager tableManager = TableManager.getInstance();
        tableManager.addHoldemTable(20000, 200, "Mid Stakes Texas Hold'em");
        tableManager.addHoldemTable(100000, 1000, "High Stakes Texas Hold'em");
        tableManager.addEuchreTable("Intermediate Euchre", "For a relaxed game");
        registerObject(TableManager.class.getSimpleName(), tableManager);
    }
    
    public static void registerObject(String name, Remote remoteObj) throws RemoteException, AlreadyBoundException {
        registry.bind(name, remoteObj);
        System.out.println("Registered: " + name + " -> " + remoteObj.getClass().getName()
        + "[" + remoteObj + "]");
    }

    /**
     * This function begins the poker game
     */
    public void runPokerGame() {
        HoldemDealer dealer = new HoldemDealer(20000, 200);
        Scanner s = new Scanner(System.in);

        for (int i = 1; i < 7; i++) {
            dealer.addPlayer(1000 + i, "Player " + i, i, 13000 + 1000 * i);
        }

        System.out.println("Welcome to Ryan's awesome poker game!");
        System.out.println("Use actions B (Bet), C (Call), X (Check), F (Fold), and R (Raise) for betting");

        char yn = 'Y';
        while (Character.toUpperCase(yn) != 'N') {
            dealer.startHand();
            startBettingRound(dealer);

            if (!dealer.isWinner()) {
                dealer.dealFlopToBoard();
                startBettingRound(dealer);
            }
            if (!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound(dealer);
            }
            if (!dealer.isWinner()) {
                dealer.dealCardToBoard();
                startBettingRound(dealer);
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
     * @param dealer 
     */
    public void startBettingRound(HoldemDealer dealer) {
        Scanner s = new Scanner(System.in);
        char charAction;
        PokerAction action;
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
                    action = PokerAction.BET;
                    System.out.print("Bet amount: ");
                    chipAmount = s.nextInt(); 
                    break;
                case 'C':
                    action = PokerAction.CALL;
                    break;
                case 'X':
                    action = PokerAction.CHECK;
                    break;
                case 'F':
                    action = PokerAction.FOLD;
                    break;
                case 'R':
                    action = PokerAction.RAISE;
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
    
    public void runEuchreGame() {
        EuchreDealer euchre = new EuchreDealer();
        Scanner temp = new Scanner(System.in);

        euchre.addPlayer(1111, "Player 0", 0);
        euchre.addPlayer(2222, "Player 1", 1);
        euchre.addPlayer(3333, "Player 2", 2);
        euchre.addPlayer(4444, "Player 3", 3);

        euchre.determineDealer();
        euchre.startNewHand(true);
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
    
    public void runBlackjackGame() {
        BlackjackDealer dealer = new BlackjackDealer(100, 10000);
        Scanner s = new Scanner(System.in);
        
        System.out.println("Welcome to Blackjack!");
        System.out.println("Actions: H-Hit, S-Stand, D-Double Down, P-Split, U-Surrender");
        List<BettingPlayer> players;
        dealer.addPlayer(111, "Player 1", 1, 5000);
        dealer.addPlayer(222, "Player 4", 4, 10000);
        
        while(true) {
            dealer.startHand();
            players = dealer.getActivePlayers();
            for(BettingPlayer player : players) {
                System.out.printf("Player %d (%d) enter bet: ", player.getSeatNumber(), player.getChips());
                dealer.takeBet(player.getSeatNumber(), s.nextInt());
            }
            s.nextLine();
            dealer.dealHands();

            String handString;
            char action;
            int numPlayers = players.size();
            for(int i = 0; i < numPlayers; i++) {
                BettingPlayer activePlayer = players.get(0);
                handString = "";
                for(int card : activePlayer.getHand()) {
                    handString += (Deck.cardToString(card) + " ");
                }
                System.out.printf("Player %d, your hand is %s (%s)\n", activePlayer.getSeatNumber(), handString, dealer.getHandValueString(activePlayer.getSeatNumber()));                
                do {            
                    do {
                        System.out.print("Enter action: ");
                        action = s.nextLine().toUpperCase().charAt(0);
                    } while(action != 'H' && action != 'S' && action != 'D' & action != 'P' && action != 'U');
                    switch(action) {
                        case 'H':
                            dealer.takeAction(BlackjackAction.HIT);
                            break;
                        case 'S':
                            dealer.takeAction(BlackjackAction.STAND);
                            break;
                        case 'D':
                            dealer.takeAction(BlackjackAction.DOUBLEDOWN);
                            break;
                        case 'P':
                            dealer.takeAction(BlackjackAction.SPLIT);
                            break;
                        case 'U':
                            dealer.takeAction(BlackjackAction.SURRENDER);
                            break;
                        default:
                            break;
                    }

                    handString = "";
                    for(int card : activePlayer.getHand()) {
                        handString += (Deck.cardToString(card) + " ");
                    }
                    System.out.printf("Player %d, your hand is %s (%s)\n", activePlayer.getSeatNumber(), handString, dealer.getHandValueString(activePlayer.getSeatNumber()));    
                } while(!dealer.getPlayerFinished());
            }
            dealer.completeHand();
            
            handString = "";
            for(int card : dealer.getDealerHand()) {
                handString += (Deck.cardToString(card) + " ");
            }
            System.out.printf("The dealer's hand is %s (%s)\n", handString, dealer.getHandValueString(0));
            
            char yn;
            do {
                System.out.print("Do you want to start another hand? (Y/N): ");
                yn = s.nextLine().toUpperCase().charAt(0);
            } while(yn != 'Y' && yn != 'N');
            if(yn == 'N') break;
        }
    }
}
