package cardgameslib.utilities;

import java.util.*;

/**
 * Class holding info pertaining to a common card game Player
 *
 * @author Ryan Bickham
 *
 */
public class Player implements Comparable<Player> {

    protected final int userId;
    protected final String username;
    protected final int seatNumber; //Player # on table (Player 1, Player 2, etc.)
    protected List<Integer> hand;

    /**
     * Constructor for Player
     *
     * @param id int to hold id of player
     * @param seatNum int holding seatnumber player is sitting at
     */
    public Player(int id, String name, int seatNum) {
        userId = id;
        username = name;
        seatNumber = seatNum;
        hand = new ArrayList<>();
    }

    /**
     * Getter to obtain user id
     *
     * @return int
     */
    public int getUserId() {
        return userId;
    }

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