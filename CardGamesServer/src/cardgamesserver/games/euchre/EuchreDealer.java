package cardgamesserver.games.euchre;

import cardgameslib.games.IEuchreDealer;
import cardgameslib.receivers.IEuchreReceiver;
import cardgameslib.utilities.Deck;
import cardgameslib.utilities.Player;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class EuchreDealer extends UnicastRemoteObject implements IEuchreDealer {

    public static final int MIN_MAX_PLAYERS = 4;

    private EuchreWinChecker winChecker;
    private Deck deck;
    private List<Player> players;
    private int currentDealer;
    private int playersTurn;
    private int previousPlayer;
    private int trump;
    private boolean alone;
    private int alonePlayer;
    private String topCard;
    private boolean cardUp;
    
    private final ArrayList<Integer> availableSeats;
    private final List<IEuchreReceiver> clients = new ArrayList<>();

    public EuchreDealer() throws RemoteException {
        winChecker = new EuchreWinChecker(this);
        previousPlayer = 0;
        trump = -1;
        alone = false;
        alonePlayer = -1;
        topCard = "";
        players = new ArrayList<>(MIN_MAX_PLAYERS);
        
        availableSeats = new ArrayList<>();
        for(int i = 1; i <= MIN_MAX_PLAYERS; i++) {
            availableSeats.add(i);
        }
        
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
    
    @Override
    public void addPlayer(int id , String username, int seat) throws RemoteException {
        players.add(new Player(id, username, seat));
        availableSeats.remove(new Integer(seat));
        Collections.sort(players);
        
        for(IEuchreReceiver client : clients) {
            client.initializePlayers();
        }
        if(players.size() == 4) {
            startNewHand(true);
        }
    }

    public void removePlayer(int id) {
        for (Player player : players) {
            if (player.getUserId() == id) {
                players.set(players.indexOf(player), null);
                availableSeats.add(player.getSeatNumber());
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
        playersTurn = currentDealer;
        previousPlayer = 0;
        deck.collectCards();                // Collect the cards.
        deck.shuffle();                     // Shuffle the deck.
    }

    private int nextPlayer(int player) {
        previousPlayer = getCurrentPlayer().getSeatNumber();
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
        } else {
            determineDealer();
        }

        deck.collectCards();
        deck.shuffle();
        trump = -1;
        topCard = "";
        alone = false;
        alonePlayer = -1;
        resetPlayersHands();
        
        for(Player p : players) {
            hidePlayerOptions(p);
        }
        
        updateActivePlayer();
        showDealerOption(getCurrentPlayer());
        setCanPlayCard(false);
    }

    @Override
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
            updateCards();
            showPassCallOption(getCurrentPlayer());
            updateActivePlayer();
            showAvailableTrump();
            showTopCard();
        }
    }
    
    public void showAvailableTrump() {
        try {
            for(IEuchreReceiver client : clients) {
                client.showAvailableTrump();
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void updateActivePlayer() {
        try {
            for(IEuchreReceiver client : clients) {
                client.showActivePlayer(getCurrentPlayer().getSeatNumber(), previousPlayer);
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void setCanPlayCard(boolean can) {
        try {
            for(IEuchreReceiver client : clients) {
                client.setCanPlayCard(can);
            }
        } catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    } 
    
    public void updateCards() {
        try {
            for(IEuchreReceiver client : clients) {
                client.displayCards();
            }
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void showTopCard() {
        try {
            for(IEuchreReceiver client : clients) {
                client.showTopCard(getCurrentDealer().getSeatNumber(), getTopCard());
            }
        } catch(RemoteException ex) {}
    }
    
    public void downTopCard() {
        try {
            for(IEuchreReceiver client : clients) {
                client.downTopCard(getCurrentDealer().getSeatNumber());
            }
        } catch(RemoteException ex) {}
    }
    
    public void showTrump() {
        List<String> suitValues = Arrays.asList("C", "D", "S", "H");
        try {
            for(IEuchreReceiver client : clients) {
                client.showTrump(suitValues.get(trump));
            }
        } catch(RemoteException ex) {}
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
    
    @Override
    public void passOnCallingTrump() {
        if (playersTurn == currentDealer) {
            if (cardUp == true) {
                cardUp = false;
                topCard = "";
                downTopCard();
                showAvailableTrump();
                System.out.println("Card Turned Down");
            } else {
                System.out.println("Muck Hand");
                startNewHand(false);
            }
        }
        hidePlayerOptions(getCurrentPlayer());
        playersTurn = nextPlayer(playersTurn);
        updateActivePlayer();
        showPassCallOption(getCurrentPlayer());
    }

    public void callTrump(int trump, boolean alone) {
        // Set trump and start the hand
        this.trump = trump;
        this.alone = alone;
        
        showTrump();
        winChecker.setPlayerCalledTrump(playersTurn);
        winChecker.setAlone(alone);
        winChecker.setTrump(trump);
        hidePlayerOptions(getCurrentPlayer());
        
        if (alone) {
            setAlonePlayer(playersTurn);
        }
        
        setCanPlayCard(true);
        
        if (cardUp) {
            previousPlayer = getCurrentPlayer().getSeatNumber();
            setCurrentPlayer(currentDealer);
            updateActivePlayer();
            showCardAction(getCurrentPlayer(), "discard");
        } else {
            startHand();
        }
    }

    @Override
    public void getCardToReplace(int card) {
        hidePlayerOptions(getCurrentPlayer());
        players.get(currentDealer).giveCard(deck.dealCard());
        players.get(currentDealer).removeCard(card);
        players.get(currentDealer).sortHand();
        startHand();
    }

    private void startHand() {
        System.out.println("Start Hand");
        sortHandsTrumpFirst();
        downTopCard();
        
        playersTurn = nextPlayer(currentDealer);
        winChecker.setPlayerLead(getCurrentPlayerPos());
        updateActivePlayer();
        
        updateCards();
        showCardAction(getCurrentPlayer(), "play");
    }

    public void cardPlayed(int card) {
        int player = getCurrentPlayerPos();
        int cardValue = players.get(player).getHand().get(card);

        winChecker.setCardPlayed(player, cardValue);
        players.get(player).removeCard(card);
        
        if ((alone && winChecker.getCardPlayedCount() == 3) || (!alone && winChecker.getCardPlayedCount() == 4)) {
            winChecker.determineWinner();
        }else{
            setCurrentPlayer(nextPlayer(player));
        }
    }

    @Override
    public void addClient(IEuchreReceiver client) throws RemoteException {
        clients.add(client);
    }
    
    @Override
    public void removeClient(IEuchreReceiver client) throws RemoteException {
        clients.remove(client);
        System.out.println("Removing euchre client");
    }
    
    public void showDealerOption(Player player) {
        try {
            findClientById(player.getUserId()).showDealerOption();
        } catch(RemoteException ex) {}
    }
    
    public void hidePlayerOptions(Player player) {
        try {
            findClientById(player.getUserId()).hidePlayerOptions();
        } catch(RemoteException ex) {}
    }
    
    public void showPassCallOption(Player player) {
        try {
            findClientById(player.getUserId()).showPassCallOption();
        } catch(RemoteException ex) {}
    }
    
    public void showCardAction(Player player, String action) {
        try {
            findClientById(player.getUserId()).showCardActions(action);
        } catch(RemoteException ex) {}
    }
    
    private IEuchreReceiver findClientById(int id) {
        try {
            for(IEuchreReceiver client : clients) {
                if(client.getPlayerId() == id) {
                    return client;
                }
            }
        } catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        throw new IllegalArgumentException("Client with id " + id + " not found.");
    }
    
//*******************************************************************************
    @Override
    public ArrayList<Integer> getAvailableSeats() throws RemoteException {
        Collections.sort(availableSeats);
        return availableSeats;
    }
    
    public int getTrump() {
        return trump;
    }

    public String getTopCard() {
        return topCard;
    }

    public boolean isCardUp() {
        return cardUp;
    }
    
    public Player getCurrentDealer() {
        return players.get(currentDealer);
    }
    
    public int getCurrentDealerPos() {
        return currentDealer;
    }
    
    public Player getCurrentPlayer() {
        return players.get(playersTurn);
    }
    
    public int getCurrentPlayerPos() {
        return playersTurn;
    }

    public ArrayList<Player> getPlayers() {
        return (ArrayList)players;
    }
    
//    public int getTeamOneTricks() {
//        return winChecker.getTeamOneTricks();
//    }
//    
//    public int getTeamTwoTricks() {
//        return winChecker.getTeamTwoTricks();
//    }
//    
//    public int getTeamOneScore() {
//        return winChecker.getTeamOneScore();
//    }
//    
//    public int getTeamTwoScore() {
//        return winChecker.getTeamTwoScore();
//    }
//    
//    public String getWinner() {
//        return winChecker.getWinner();
//    }
    
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