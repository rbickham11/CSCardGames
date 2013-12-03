package json;

public class ActionStatusResponse {
	private boolean playerActive;
	private int lastPlayer;
	private String lastAction;
	private int lastBet;
	
	public boolean getPlayerActive() {
		return playerActive;
	}
	
	public void setPlayerActive(boolean playerActive) {
		this.playerActive = playerActive;
	}
	
	public int getLastPlayer() {
		return lastPlayer;
	}
	
	public void setLastPlayer(int lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
	
	public String getLastAction() {
		return lastAction;
	}
	
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	
	public int getLastBet() {
		return lastBet;
	}
	
	public void setLastBet(int lastBet) {
		this.lastBet = lastBet;
	}
}
