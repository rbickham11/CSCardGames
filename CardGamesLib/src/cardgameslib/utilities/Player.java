package cardgameslib.utilities;

import java.util.*;

/**
 * Class holding info pertaining to a common card game Player
 *
 * @author Ryan Bickham
 *
 */
public class Player implements Comparable<Player> {

    protected int userId;
    protected String username;

    protected int seatNumber; //Player # on table (Player 1, Player 2, etc.)
    protected String euchreDealSequence;
    protected List<Integer> hand;

    /**
     * Constructor for Player
     *
     * @param id int to hold id of player
     * @param userName String holding username of player
     * @param seatNum int holding seatnumber player is sitting at
     */
    public Player(int id, String userName, int seatNum) {
        userId = id;
        username = userName;
        seatNumber = seatNum;
        hand = new ArrayList<>();
    }

    /**
     * Constructor for Player
     *
     * @param id int to hold id of player
     * @param seatNum int holding the seat player is sitting at
     * @param dealSequence String holding the deal sequence of player
     */
    public Player(int id, int seatNum, String dealSequence) {
        userId = id;
        seatNumber = seatNum;
        hand = new ArrayList<>();
        setDealSequence(dealSequence);
        //Query database using userId to get any other information needed for backend 
        //(Total chip count, username if we need it, etc.)
    }

    /**
     * Getter to obtain user id
     *
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter to obtain username
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter to return player's seat number
     *
     * @return int
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Getter to return player's deal sequence
     *
     * @return String
     */
    public String getDealSequence() {
        return euchreDealSequence;
    }

    /**
     * Function to set the deal sequence of a player
     *
     * @param dealSequence
     */
    private void setDealSequence(String dealSequence) {
        String[] sequence = dealSequence.split(", ");

        for (int i = 0; i < sequence.length; i++) {
            if (!sequence[i].equals("2") && !sequence[i].equals("3")) {
                dealSequence = "3, 2, 3, 2";
                break;
            }
        }
        euchreDealSequence = dealSequence;
    }

    /**
     * Getter to return a player's hand
     *
     * @return List<Integer>
     */
    public List<Integer> getHand() {
        return hand;
    }

    public void sortHand() {
        Collections.sort(hand, Collections.reverseOrder());
    }

    public void sortHandTrumpFirst(int trump) {
        boolean containTrump = false;
        int firstTrumpCard = 0;
        int tempCard = 0;

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
            giveCard(tempCard);
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
    }

    /**
     * Function to give card to a player
     *
     * @param card int value representing card to be given
     */
    public void giveCard(int card) {
        hand.add(card);
    }

    /**
     * Function to give multiple cards to a player
     *
     * @param cards List of integer representation of cards to be given
     */
    public void giveCard(List<Integer> cards) {
        for (Integer card : cards) {
            hand.add(card);
        }
    }

    /**
     * Function to remove a card from the user's hand
     *
     * @param card int representation of card to be removed
     */
    public void removeCard(int card) {
        hand.remove(card);
    }

    /**
     * Function to reset a player's hand
     */
    public void resetHand() {       //Needed whenever starting a new hand
        hand = new ArrayList<>();
    }

    @Override
    /**
     * Function to compare one player to another
     *
     * @param otherPlayer Player to compare current Player against
     * @return int
     */
    public int compareTo(Player otherPlayer) {
        return seatNumber - otherPlayer.getSeatNumber();
    }
}
