package cardgameslib.games.poker.holdem;

import cardgameslib.utilities.BettingPlayer;
import java.util.*;

public class HoldemWinChecker {
    private final List<String> ranks = Arrays.asList("High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush");
    
    private List<BettingPlayer> players;
    private List<BettingPlayer> winningPlayers;
    
    private List<Integer> hands;
    private List<Integer> board;
    private List<Integer> thisHand;
    private List<Integer> handValues;
    private int winningRank;
    private BitSet dontCheck;
    
    public HoldemWinChecker() {
        thisHand = Arrays.asList(-1, -1, -1, -1, -1, -1, -1);
    }
    
    public void findWinningHand(List<BettingPlayer> activePlayers, List<Integer> inBoard) {
        players = activePlayers;
        board = inBoard;
        hands = new ArrayList<>();
        winningPlayers = new ArrayList<>();
        
        for(BettingPlayer player : players) {
            hands.addAll(player.getHand());
        }
        
        int i, j, k;
        boolean handFound = false;
        List<Integer> rankWinners = new ArrayList<>();
        List<Integer> fiveCardHands;
        
        dontCheck = new BitSet(ranks.size());
     
        eliminateHands();
        
        for(i = 0; i < 5; i++) {
            thisHand.set(i, board.get(i));
        }
        
        for(i = 8; handFound == false; i--) {
            if(!dontCheck.get(i)) {
                for(j = 0; j < hands.size(); j += 2) {
                    thisHand.set(5, hands.get(j));
                    thisHand.set(6, hands.get(j + 1));
                    
                    handValues = getValueList(thisHand);
                    Collections.sort(handValues);
                    if(rankCheck(i)) {
                        handFound = true;
                        rankWinners.add(thisHand.get(5));
                        rankWinners.add(thisHand.get(6));
                    }
                }
            }            
        }
        winningRank = i + 1;
        
        if(rankWinners.size() == 2) {   //If there's only one hand with the winning rank
            winningPlayers.add(BettingPlayer.getPlayerByCard(players, rankWinners.get(0)));
        } else {
            fiveCardHands = getFiveCardHands(rankWinners, winningRank);
            BitSet possibleWinner = new BitSet(fiveCardHands.size() / 5);
            possibleWinner.set(0, fiveCardHands.size() / 5);
            int possibleCount = fiveCardHands.size() / 5;
            
            for(i = 4; i >= 0; i--) {
                if(possibleCount == 1) {
                    break;
                }
                for(j = 0; j < fiveCardHands.size(); j += 5) {
                    if(possibleWinner.get(j / 5)) {
                        for(k = 0; k < fiveCardHands.size(); k += 5) {
                            if (k != j) {
                                if(possibleWinner.get(k / 5) && fiveCardHands.get(j + i) < fiveCardHands.get(k + i)) {
                                    possibleWinner.set(j / 5, false);
                                    possibleCount--;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            for(i = 0; i < possibleWinner.size(); i++) {
                if(possibleWinner.get(i)) {
                    winningPlayers.add(BettingPlayer.getPlayerByCard(players, rankWinners.get(i * 2)));
                }
            } 
        }           
    }
    
    public List<BettingPlayer> getWinningPlayers() {
        return winningPlayers;
    }
    
    public String getWinningRank() {
        return ranks.get(winningRank);
    }
    private void eliminateHands() {
        int i;
        boolean draw = false;
        List<Integer> boardValues = getValueList(board);
        List<Integer> boardSuits = getSuitList(board);
        List<Integer> distinctValues = new ArrayList(new HashSet(boardValues));
        
        switch(distinctValues.size()) {
            case 2: //Must be Four of a Kind or Full House
                for(i = 0; i < 9; i++) {
                    if(i != 7 && i != 6) {
                        dontCheck.set(i, true);
                    }
                }
                return; //Do not need to check for straight and flush
            case 3:
            case 4:
                break;
            case 5: //Eliminate Full House and Four of a Kind
                dontCheck.set(7, true);
                dontCheck.set(8, true);
                break;
            default:
                System.out.println("Error, Invalid Board");
                break;
        }
        
        //Eliminate Flush Hands
        Collections.sort(boardSuits);
        for(i = 2; i < boardSuits.size(); i++) {
            if(boardSuits.get(i) == boardSuits.get(i - 2)) {
                draw = true;
                break;
            }
        }
        if(!draw) {
            dontCheck.set(5, true);
            dontCheck.set(8, true);
        }
        
        //Eliminate Straight Hands
        draw = false;
        Collections.sort(distinctValues);
        for(i = 2; i < distinctValues.size(); i++) {
            if(distinctValues.get(i) == distinctValues.get(i - 2) + 2) {
                draw = true;
                break;
            }
        }
        if(!draw) {
            dontCheck.set(4, true);
            dontCheck.set(8, true);
        }
    }
    
    private boolean rankCheck(int rank) {
        int i, j, k, temp;
        List<Integer> tempHand;
        boolean pairFound = false;
        
        switch(rank) {
            case 8: //Straight Flush
                tempHand = new ArrayList<>(thisHand);
                Collections.sort(tempHand);
                if(isStraight(tempHand) && isStraight(getValueList(tempHand))) { //This is a straight flush becuase thisHand values are still numbered 0-51
                                                                                 //Second check eliminates overlap (Ex: Q-K-A-2-3)
                    return true;
                }
                return false;
            case 7: //Four of a Kind
                for(i = 3; i < handValues.size(); i++) {
                    if(handValues.get(i) == handValues.get(i - 3)) {
                        return true;
                    }
                    return false;
                }
            case 6: //Full House
                for(i = 2; i < handValues.size(); i++) {
                    if(handValues.get(i) == handValues.get(i - 2)) {
                        temp = handValues.get(i);
                        handValues.subList(i - 2, i + 1).clear();
                        for(j = 1; j < handValues.size(); j++) {
                            if(handValues.get(j) == handValues.get(j - 1)) {
                                for(k = 0; k < 3; k++) {
                                    handValues.add(temp);
                                }
                                Collections.sort(handValues);
                                return true;
                            }
                        }
                        for(k = 0; k < 3; k++) {
                            handValues.add(temp);
                        }
                        Collections.sort(handValues);
                        return false;                         
                    }
                }
                return false;
            case 5: //Flush
                if(isFlush(thisHand)) {
                    return true;
                }
                return false;
            case 4: //Straight
                if(isStraight(handValues)) {
                    return true;
                }
                return false;
            case 3: //Three of a Kind
                for(i = 2; i < handValues.size(); i++) {
                    if(handValues.get(i) == handValues.get(i - 2)) {
                        return true;
                    }
                }
                return false;
            case 2: //Two Pair
                for(i = 1; i < handValues.size(); i++) {
                    if(!pairFound) {
                        if(handValues.get(i) == handValues.get(i - 1)) {
                            pairFound = true;
                        }
                    } else {
                        for(j = i; j < handValues.size(); j++) {
                            if(handValues.get(j) == handValues.get(j - 1)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            case 1: //Pair
                for(i = 1; i < handValues.size(); i++) {
                    if(handValues.get(i) == handValues.get(i - 1)) {
                        return true;
                    }
                }
                return false;
            case 0: //High Card
                return true;
            default:
                System.out.println("Invalid rank");
                break;
        }
        return false;
    }
    
    private List<Integer> getFiveCardHands(List<Integer> rankWinners, int rank) {
        int i, j, k;
        List<Integer> fiveCardHands = new ArrayList<>();
        List<Integer> sevenCardHand;
        
        for(i = 0; i < rankWinners.size(); i += 2) {
            sevenCardHand = new ArrayList<>(board);
            sevenCardHand.add(rankWinners.get(i));
            sevenCardHand.add(rankWinners.get(i + 1));
            
            if(rank != 5 && rank != 8) {
                sevenCardHand = getValueList(sevenCardHand);
            }
            Collections.sort(sevenCardHand);
            
            switch(rank) {
                case 0: //High Card
                    sevenCardHand.subList(0, 2).clear();
                    fiveCardHands.addAll(sevenCardHand);
                    break;
                case 1: //Pair
                case 2: //Two Pair
                    for(j = sevenCardHand.size() - 2; j >= 0; j--) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j + 1)) {
                            break;
                        }
                    }
                    int dupCard = sevenCardHand.get(j);
                    sevenCardHand.subList(j, j + 2).clear();
                    sevenCardHand.add(dupCard);
                    sevenCardHand.add(dupCard);
                    
                    if(rank == 2) {
                        for(j = sevenCardHand.size() - 4; j >= 0; j--) {
                            if(sevenCardHand.get(j) == sevenCardHand.get(j + 1)) {
                                break;
                            }
                        }
                        dupCard = sevenCardHand.get(j);
                        sevenCardHand.subList(j, j + 2).clear();
                        sevenCardHand.add(3, dupCard);
                        sevenCardHand.add(3, dupCard);
                    }
                    sevenCardHand.subList(0, 2).clear();
                    fiveCardHands.addAll(sevenCardHand);
                    break;
                case 3: //Three of a Kind
                    for(j = sevenCardHand.size() - 3; j >= 0; j--) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j + 2)) {
                            break;
                        }
                    }
                    dupCard = sevenCardHand.get(j);
                    sevenCardHand.subList(j, j + 3).clear();
                    for(k = 0; k < 3; k++) {
                        sevenCardHand.add(dupCard);
                    }
                    sevenCardHand.subList(0, 2).clear();
                    fiveCardHands.addAll(sevenCardHand);
                    break;
                case 4: //Straight
                    sevenCardHand = new ArrayList(new HashSet(sevenCardHand));
                    for(j = sevenCardHand.size() - 1; j >= 4; j--) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j - 4) + 4) {
                            for(k = j; k > j - 5; k--) {
                                fiveCardHands.add(sevenCardHand.get(k));
                            }
                            break;
                        }
                    }
                    break;
                case 5: //Flush
                    for(j = sevenCardHand.size() - 1; j >= 4; j--) {
                        if(getSuit(sevenCardHand.get(j)) == getSuit(sevenCardHand.get(j - 4))) {
                            for(k = j; k > j - 5; k--) {
                                fiveCardHands.add(getCardValue(sevenCardHand.get(k)));
                            }
                            break;
                        }
                    }
                    break;
                case 6: //Full House
                    for(j = 0; j < sevenCardHand.size() - 2; j++) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j + 2)) {
                            break;
                        }
                    }
                    dupCard = sevenCardHand.get(j);
                    sevenCardHand.subList(j, j + 3).clear();
                    for(k = 0; k < 3; k++) {
                        sevenCardHand.add(dupCard);
                    }
                    
                    for(j = sevenCardHand.size() - 4; j >= 0; j--) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j + 1)) {
                            break;
                        }
                    }
                    dupCard = sevenCardHand.get(j);
                    sevenCardHand.subList(j, j + 2).clear();
                    sevenCardHand.add(2, dupCard);
                    sevenCardHand.add(2, dupCard);
                    
                    sevenCardHand.subList(0, 2).clear();
                    fiveCardHands.addAll(sevenCardHand);
                    break;
                case 7: //Four of a Kind
                    for(j = 0; j < sevenCardHand.size() - 3; j++) {
                        if(sevenCardHand.get(j) == sevenCardHand.get(j + 3)) {
                            break;
                        }
                    }
                    dupCard = sevenCardHand.get(j);
                    sevenCardHand.subList(j, j + 4).clear();
                    for(k = 0; k < 4; k++) {
                        sevenCardHand.add(dupCard);
                    }
                    
                    sevenCardHand.subList(0, 2).clear();
                    fiveCardHands.addAll(sevenCardHand);
                    break;
                case 8: //Straight Flush
                    for(j = sevenCardHand.size() - 1; j >= 4; j--) {
                        if(getSuit(sevenCardHand.get(j)) == getSuit(sevenCardHand.get(j - 4)) && 
                           getCardValue(sevenCardHand.get(j)) == getCardValue(sevenCardHand.get(j - 4)) + 4) {
                            for(k = j; k > j - 5; k--) {
                                fiveCardHands.add(getCardValue(sevenCardHand.get(k)));
                            }
                            break;
                        }
                    }
                    break;
                default: 
                    System.out.println("getFiveCardHands rank error");
                    break;
            }
        }
        return fiveCardHands;
    }
    private boolean isStraight(List<Integer> hand) {
        List<Integer> distinctHand = new ArrayList(new HashSet(hand));
        
        if(distinctHand.get(distinctHand.size() - 1) == 12 && distinctHand.get(0) == 0 && 
                            distinctHand.get(1) == 1 && distinctHand.get(2) == 2 && distinctHand.get(3) == 3) { //Checking for A-2-3-4-5 straight
            return true;
        }
        for(int i = 0; i < distinctHand.size() - 4; i++) {
            if(distinctHand.get(i) == distinctHand.get(i + 4) - 4) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isFlush(List<Integer> hand) {
        hand = getSuitList(hand);
        Collections.sort(hand);
        
        for(int i = 4; i < hand.size(); i++) {
            if(hand.get(i) == hand.get(i - 4)) {
                return true;
            }
        }
        return false;
    }
    
    private int getCardValue(int card) {
        return card % 13;
    }
    
    private int getSuit(int card) {
        return card / 13;
    }
    
    private List<Integer> getValueList(List<Integer> cards) {
        List<Integer> valueList = new ArrayList<>();
        for(int c : cards) {
            valueList.add(c % 13);
        }
        return valueList;
    }
    
    private List<Integer> getSuitList(List<Integer> cards) {
        List<Integer> suitList = new ArrayList<>();
        for(int c : cards) {
            suitList.add(c / 13);
        }
        return suitList;
    }
}
