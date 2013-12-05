package json;

public class SimplePlayer {
	private int userId;
	private String username;
	private int seatNumber;
	private int startingChips;
	
	public int getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	
	public int getStartingChips() {
		return startingChips;
	}
	
	public void setUserId(int playerId) {
		this.userId = playerId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public void setStartingChips(int startingChips) {
		this.startingChips = startingChips;
	}
}
