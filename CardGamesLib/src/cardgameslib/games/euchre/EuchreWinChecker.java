package cardgameslib.games.euchre;

import java.util.*;

/**
 * Class to handle who won the hand and round of euchre
 *
 * @author Andrew Haegar
 *
 */
public class EuchreWinChecker {
    private EuchreDealer game;
    private int trump;
    private int playerCalledTrump;
    private int rightBower;
    private int leftBower;
    private boolean alone;
    private List<Integer> cardsPlayed;
    private int cardsPlayedCount;
    private int playerLead;
    private int teamOneTricks;
    private int teamTwoTricks;
    private int teamOneScore;
    private int teamTwoScore;

    public EuchreWinChecker(EuchreDealer game) {
        this.game = game;
        trump = -1;
        playerCalledTrump = -1;
        rightBower = -1;
        leftBower = -1;
        alone = false;
        cardsPlayed = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        cardsPlayedCount = 0;
        playerLead = 0;
        teamOneTricks = 0;
        teamTwoTricks = 0;
        teamOneScore = 0;           // Players 1 and 3
        teamTwoScore = 0;           // Players 2 and 4
    }

    public void setTrump(int trump) {
        this.trump = trump;
    }

    public void setPlayerCalledTrump(int player) {
        this.playerCalledTrump = player;
    }
    
    public void setAlone(boolean alone) {
        this.alone = alone;
    }

    public int getCardPlayedCount() {
        return cardsPlayedCount;
    }
    
    public void setCardPlayed(int player, int card) {
        cardsPlayed.set(player, card);
        cardsPlayedCount += 1;
    }

    public void setPlayerLead(int lead) {
        playerLead = lead;
    }

    private void determineBowers() {
        switch (trump) {
            case 0:                 // Clubs
                rightBower = 9;
                leftBower = 35;
                break;
            case 1:                 // Diamonds
                rightBower = 22;
                leftBower = 48;
                break;
            case 2:                 // Spades
                rightBower = 35;
                leftBower = 9;
                break;
            case 3:                 // Hearts
                rightBower = 48;
                leftBower = 22;
                break;
        }
    }

    public boolean determineWinner() {
        List<Integer> filteredCards = new ArrayList<>();
        int winner;
        
        determineBowers();
        trumpCards(filteredCards);
        
        if (filteredCards.size() > 1) {           // Multiple Trump Cards
            determineHighestTrump(filteredCards);
        } else if (filteredCards.isEmpty()) {
            determineHighestSuit(filteredCards);
        }

        winner = cardsPlayed.indexOf(filteredCards.get(0));
        awardTrickWin(winner);
        
        if((teamOneTricks + teamTwoTricks) == 5){
            determineHandWinner();
            resetAfterHand();
        }
        
        if((teamOneScore >= 10) || teamTwoScore >= 10){
            determineGameWinner();
            resetAfterHand();
        }
        
        System.out.println("Team One Tricks: " + teamOneTricks);
        System.out.println("Team Two Tricks: " + teamTwoTricks);
        System.out.println();
        System.out.println("Team One Score: " + teamOneScore);
        System.out.println("Team Two Score: " + teamTwoScore);
        System.out.println();
        System.out.println("Winner: " + winner);
        game.setCurrentPlayer(winner);
        resetAfterTrick();
        
        return true;
    }
    
    private void awardTrickWin(int player){
        if(player == 0 || player == 2){
            teamOneTricks += 1;
        }else if(player == 1 || player == 3){
            teamTwoTricks += 1;
        }
    }
    
    private void determineHandWinner(){
        if(playerCalledTrump == 0 || playerCalledTrump == 2) {
            if(alone) {
                if(teamOneTricks == 5) {
                    teamOneScore += 4;
                }else if(teamOneTricks == 4 || teamOneTricks == 3) {
                    teamOneScore += 1;
                }else if(teamOneTricks < 3) {
                    teamTwoScore += 2;
                }
            }else{
                if(teamOneTricks == 5){
                    teamOneScore += 2;
                }else if(teamOneTricks == 4 || teamOneTricks == 3) {
                    teamOneScore += 1;
                }else if(teamOneTricks < 3) {
                    teamTwoScore += 2;
                }
            }
        }else if(playerCalledTrump == 1 || playerCalledTrump == 3) {
            if(alone) {
                if(teamTwoTricks == 5) {
                    teamTwoScore += 4;
                }else if(teamTwoTricks == 4 || teamTwoTricks == 3) {
                    teamTwoScore += 1;
                }else if(teamOneTricks < 3) {
                    teamOneScore += 2;
                }
            }else{
                if(teamTwoTricks == 5) {
                    teamTwoScore += 2;
                }else if(teamTwoTricks == 4 || teamTwoTricks == 3) {
                    teamTwoScore += 1;
                }else if(teamTwoTricks < 3) {
                    teamOneScore += 2;
                }
            }
        }
    }
    
    private void determineGameWinner(){
        if(teamOneScore >= 10) {
            System.out.println("Team One Wins!");
        }else if(teamTwoScore >= 10) {
            System.out.println("Team Two Wins!");
        }
    }

    private void trumpCards(List<Integer> cards) {
        for (Integer card : cardsPlayed) {
            if (card != 0 && ((card / 13 == trump) || card == leftBower)) {
                cards.add(card);
            }
        }
    }

    private void determineHighestTrump(List<Integer> cards) {
        int highestTrump = 0;
        
        Collections.sort(cards, Collections.reverseOrder());
        
        if(cards.contains(rightBower)) {
            highestTrump = rightBower;
        }else if(cards.contains(leftBower)) {
            highestTrump = leftBower;
        }else{
            highestTrump = cards.get(0);
        }
        cards.clear();
        cards.add(highestTrump);
    }
    
    private void determineHighestSuit(List<Integer> cards) {
        int highestCard = 0;
        int trumpLead = cardsPlayed.get(playerLead) / 13;
        
        Collections.sort(cards, Collections.reverseOrder());
        
        for(Integer card : cards) {
            if((card / 13) == trumpLead){
                if(card > highestCard){
                    highestCard = card;
                }
            }
        }
        cards.add(highestCard);
    }
    
    private void resetAfterTrick() {
        playerLead = 0;
        cardsPlayed.set(0, 0);
        cardsPlayed.set(1, 0);
        cardsPlayed.set(2, 0);
        cardsPlayed.set(3, 0);
        cardsPlayedCount = 0;
    }
    
    private void resetAfterHand() {
        trump = -1;
        playerCalledTrump = -1;
        rightBower = -1;
        leftBower = -1;
        alone = false;
    }
    
    public int getTeamOneTricks() {
        return teamOneTricks;
    }
    
    public int getTeamTwoTricks() {
        return teamTwoTricks;
    }
    
    public int getTeamOneScore() {
        return teamOneScore;
    }
    
    public int getTeamTwoScore() {
        return teamTwoScore;
    }
}