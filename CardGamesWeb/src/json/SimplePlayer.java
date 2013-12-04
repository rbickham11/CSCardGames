package json;

public class SimplePlayer {
	private int playerId;
	private int seatNumber;
	private int startingChips;
	
	public int getPlayerId() {
		return playerId;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	
	public int getStartingChips() {
		return startingChips;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public void setStartingChips(int startingChips) {
		this.startingChips = startingChips;
	}
}
