package json;

import java.util.*;
import cardgameslib.utilities.BettingPlayer;

public class TableInformation {
	private List<BettingPlayer> activePlayers;
	private boolean playerActive;
	private int lastPlayer;
	private String lastAction;
	private int currentBet;
	private boolean bettingComplete;
	public List<BettingPlayer> getActivePlayers() {
		return activePlayers;
	}
	
	public void setActivePlayers(List<BettingPlayer> activePlayers) {
		this.activePlayers = activePlayers;
	}
	public boolean isPlayerActive() {
		return playerActive;
	}
	public void setPlayerActive(boolean playerActive) {
		this.playerActive = playerActive;
	}
	public int getCurrentPlayer() {
		return lastPlayer;
	}
	public void setCurrentPlayer(int lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	public boolean isBettingComplete() {
		return bettingComplete;
	}
	public void setBettingComplete(boolean bettingComplete) {
		this.bettingComplete = bettingComplete;
	}
	
}
