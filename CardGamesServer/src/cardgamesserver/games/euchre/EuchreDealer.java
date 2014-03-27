package cardgamesserver.games.euchre;

import cardgameslib.games.IEuchreDealer;
import cardgameslib.utilities.Player;
import cardgameslib.utilities.Deck;
import java.util.*;

//******Player Configuration******
// 
//                3
//
//        2               4
//
//                1
//
//********************************
//*********Card Breakdown*********
// 7      9C
// 8      TC
// 9      JC
// 10     QC
// 11     KC
// 12     AC
// 20     9D
// 21     TD
// 22     JD
// 23     QD
// 24     KD
// 25     AD
// 33     9S
// 34     TS
// 35     JS
// 36     QS
// 37     KS
// 38     AS
// 46     9H
// 47     TH
// 48     JH
// 49     QH
// 50     KH
// 51     AH
//********************************
//********Dealing Sequences*******
// 2, 2, 2, 2       3, 3, 3, 3
// 3, 2, 2, 2       2, 3, 3, 3
// 2, 3, 2, 2       3, 2, 3, 3
// 2, 2, 3, 2       3, 3, 2, 3
// 2, 2, 2, 3       3, 3, 3, 2
// 3, 3, 2, 2       2, 2, 3, 3
// 3, 2, 3, 2       2, 3, 2, 3
// 3, 2, 2, 3       2, 3, 3, 2
public class EuchreDealer implements IEuchreDealer {

    public static final int MIN_MAX_PLAYERS = 4;

    private EuchreWinChecker winChecker;
    private Deck deck;
    private List<Player> players;
    private int currentDealer;
    private int playersTurn;
    private int trump;
    private boolean alone;
    private int alonePlayer;
    private String topCard;
    private boolean cardUp;

    public EuchreDealer() {
        winChecker = new EuchreWinChecker(this);
        trump = -1;
        alone = false;
        alonePlayer = -1;
        topCard = "";
        players = new ArrayList<>(MIN_MAX_PLAYERS);
        prepareEuchreDeck();
    }

    // Create a new Euchre Deck.
    private void prepareEuchreDeck() {
        List<Integer> euchreCards = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            if (i % 13 > 6) {        //If the card is a 9, T, J, Q, K, A
                euchreCards.add(i);
            }
        }
        deck = new Deck(euchreCards);
        deck.shuffle();
    }

    public void addPlayer(int id , String username, int seat) {
        players.add(new Player(id, username, seat));
        Collections.sort(players);
    }

    public void removePlayer(int id) {
        for (Player player : players) {
            if (player.getUserId() == id) {
                players.set(players.indexOf(player), null);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Player with id %d not found on table", id));
    }

    private void resetPlayersHands() {
        for (Player player : players) {
            player.resetHand();
        }
    }

    // Determine the first dealer for the game.
    public void determineDealer() {
        Random rand = new Random();
        int randomPlayer = rand.nextInt(MIN_MAX_PLAYERS);
        int card = deck.dealCard();

        // Deal until you see a "Black Jack" card.
        while (card != 9 && card != 35) {
            randomPlayer = nextPlayer(randomPlayer);
            card = deck.dealCard();
        }

        currentDealer = randomPlayer;       // Set the current dealer.
        deck.collectCards();                // Collect the cards.
        deck.shuffle();                     // Shuffle the deck.
    }

    private int nextPlayer(int player) {
        // Increment the dealer to the next player at the table.
        player++;
        if (player >= MIN_MAX_PLAYERS) {
            player = 0;
        }

        if (alonePlayer > -1 && alonePlayer < 2) {
            if (player == (alonePlayer + 2)) {
                player = nextPlayer(player);
            }
        } else if (alonePlayer > 1 && alonePlayer < 4) {
            if (player == (alonePlayer - 2)) {
                player = nextPlayer(player);
            }
        }

        return player;
    }

    public void startNewHand(boolean newGame) {
        // Only change dealer if this is the beginning
        // of a new game.
        if (!newGame) {
            nextPlayer(currentDealer);
        }

        deck.collectCards();
        deck.shuffle();
        trump = -1;
        topCard = "";
        alone = false;
        alonePlayer = -1;
        resetPlayersHands();
        //dealHands("3, 3, 3, 2");
    }

    public void dealHands(String dealSequence) {
        if (players.size() == 4) {    // Make sure there are 4 players.
            playersTurn = nextPlayer(currentDealer);
            // Use the deal sequence that user has chosen.
            int[] sequence = convertSequence(dealSequence);

            // Deal the first round of cards.
            dealTheCards(sequence[0], sequence[1], sequence[2], sequence[3]);

            // Reverse the sequence and deal the second round of cards
            // to give each player 5 total cards.
            dealSequence = reverseSequence(dealSequence);
            sequence = convertSequence(dealSequence);
            dealTheCards(sequence[0], sequence[1], sequence[2], sequence[3]);
            sortTheHands();

            cardUp = true;
            topCard = deck.getTopCard();
        }
    }

    private int[] convertSequence(String sequence) {
        // Convert the string sequence into integer numbers.
        int[] deal = new int[4];
        String[] temp = sequence.split(", ");

        for (int i = 0; i < 4; i++) {
            deal[i] = Integer.parseInt(temp[i]);
        }
        return deal;
    }

    private String reverseSequence(String sequence) {
        // Reverse the card deal sequence.
        // Replace all 2's with 3's amd 3's with 2's
        sequence = sequence.replaceAll("2", "T");
        sequence = sequence.replaceAll("3", "2");
        sequence = sequence.replaceAll("T", "3");
        return sequence;
    }

    private void dealTheCards(int p1, int p2, int p3, int p4) {
        // Use the values passed in and deal that number of cards to
        // the correct players on the table.
        players.get(playersTurn).giveCard(deck.dealCards(p1));
        playersTurn = nextPlayer(playersTurn);

        players.get(playersTurn).giveCard(deck.dealCards(p2));
        playersTurn = nextPlayer(playersTurn);

        players.get(playersTurn).giveCard(deck.dealCards(p3));
        playersTurn = nextPlayer(playersTurn);

        players.get(playersTurn).giveCard(deck.dealCards(p4));
        playersTurn = nextPlayer(playersTurn);
    }

    private void sortTheHands() {
        // Sort the cards in each players hand so they appear in order.
        for (int i = 0; i < MIN_MAX_PLAYERS; i++) {
            players.get(i).sortHand();
        }
    }

    private void sortHandsTrumpFirst() {
        for (int i = 0; i < MIN_MAX_PLAYERS; i++) {
            sortHandTrumpFirst(i);
        }
    }

    public void sortHandTrumpFirst(int player) {
        boolean containTrump = false;
        int firstTrumpCard = 0;
        int tempCard = 0;
        List<Integer> hand = players.get(player).getHand();

        for (Integer card : hand) {
            if (card / 13 == trump) {
                if (containTrump == false) {
                    firstTrumpCard = card;
                }
                containTrump = true;
            }
        }

        while (containTrump && (hand.get(0) != firstTrumpCard)) {
            tempCard = hand.remove(0);
            hand.add(tempCard);
        }

        switch (trump) {
            case 1: // Diamonds
                if (hand.contains(48)) {
                    hand.add(0, hand.remove(hand.indexOf(48)));
                }
                if (hand.contains(22)) {
                    hand.add(0, hand.remove(hand.indexOf(22)));
                }
                break;
            case 3: // Hearts
                if (hand.contains(22)) {
                    hand.add(0, hand.remove(hand.indexOf(22)));
                }
                if (hand.contains(48)) {
                    hand.add(0, hand.remove(hand.indexOf(48)));
                }
                break;
            case 0: // Clubs
                if (hand.contains(35)) {
                    hand.add(0, hand.remove(hand.indexOf(35)));
                }
                if (hand.contains(9)) {
                    hand.add(0, hand.remove(hand.indexOf(9)));
                }
                break;
            case 2: // Spades
                if (hand.contains(9)) {
                    hand.add(0, hand.remove(hand.indexOf(9)));
                }
                if (hand.contains(35)) {
                    hand.add(0, hand.remove(hand.indexOf(35)));
                }
                break;
        }
        players.get(player).resetHand();
        players.get(player).giveCard(hand);
    }
    
    public void passOnCallingTrump() {
        if (playersTurn == currentDealer) {
            if (cardUp == true) {
                cardUp = false;
                topCard = "";
                System.out.println("Card Turned Down");
            } else {
                trump = 10;  // End loop for testing
                System.out.println("Muck Hand");
//                startNewHand(false);
            }
        }
        playersTurn = nextPlayer(playersTurn);
    }

    public void callTrump(int trump, boolean alone) {
        Scanner temp = new Scanner(System.in);
        // Set trump and start the hand
        if (cardUp) {
            this.trump = trump;
            System.out.print("Player " + currentDealer + ".  What card do you want to discard. ");
            getCardToReplace(temp.nextInt());
        } else {
            this.trump = trump;
        }
        this.alone = alone;

        if (alone) {
            setAlonePlayer(getCurrentPlayer());
        }
        sortHandsTrumpFirst();

        displayPlayersHands();
        winChecker.setPlayerCalledTrump(getCurrentPlayer());
        setCurrentPlayer(nextPlayer(currentDealer));
        startHand();

        winChecker.setAlone(alone);
        winChecker.setTrump(trump);
    }

    public void getCardToReplace(int card) {
        // If the "top card" is called as trump,
        // have the dealer replace a card from his hand
        // with that top card.
        players.get(currentDealer).giveCard(deck.dealCard());
        players.get(currentDealer).removeCard(card);
        players.get(currentDealer).sortHand();
    }

    private void startHand() {
        System.out.println("Start Hand");
        winChecker.setPlayerLead(getCurrentPlayer());
    }

    public void cardPlayed(int card) {
        int player = getCurrentPlayer();
        int cardValue = players.get(player).getHand().get(card);

        winChecker.setCardPlayed(player, cardValue);
        players.get(player).removeCard(card);
        
        if ((alone && winChecker.getCardPlayedCount() == 3) || (!alone && winChecker.getCardPlayedCount() == 4)) {
            winChecker.determineWinner();
        }else{
            setCurrentPlayer(nextPlayer(player));
        }
    }

//*******************************************************************************  
    public int getTrump() {
        return trump;
    }

    public String getTopCard() {
        return topCard;
    }

    public Player getCurrentDealer() {
        return players.get(currentDealer);
    }

    public int getCurrentPlayer() {
        return playersTurn;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public void setCurrentPlayer(int player) {
        playersTurn = player;
    }

    private void setAlonePlayer(int player) {
        alonePlayer = player;
    }
//------------------------------------------------------------------------------

    public void displayPlayersHands() {
        System.out.print("                              Player 3: ");
        for (int cards : players.get(2).getHand()) {
            System.out.print(Deck.cardToString(cards));
            System.out.print(", ");
        }
        System.out.println("\n");

        System.out.print("Player 2: ");
        for (int cards : players.get(1).getHand()) {
            System.out.print(Deck.cardToString(cards));
            System.out.print(", ");
        }

        System.out.print("                              Player 4: ");
        for (int cards : players.get(3).getHand()) {
            System.out.print(Deck.cardToString(cards));
            System.out.print(", ");
        }
        System.out.println("\n");

        System.out.print("                              Player 1: ");
        for (int cards : players.get(0).getHand()) {
            System.out.print(Deck.cardToString(cards));
            System.out.print(", ");
        }
        System.out.println();
    }
//------------------------------------------------------------------------------
}