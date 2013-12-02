package json;

import java.util.*;

public class PlayerModel {
	private int id;
	private int seatNumber;
	private List<Integer> hand;
	
	
	public PlayerModel(int userId, int seatNum, List<Integer> inHand) {
		id = userId;
		seatNumber = seatNum;
		hand = inHand;
	}
	
	public int getId() {
		return id;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public List<Integer> getHand() {
		return hand;
	}
}
