package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import cardgamesdesktop.utilities.*;
import cardgameslib.games.euchre.EuchreDealer;
import cardgameslib.utilities.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jfx.messagebox.MessageBox;


/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class EuchreGUIController extends GameController implements Initializable, Screens {

    // Seat Configuration
    //      3
    //  2       4
    //      1
    
    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
    
    @FXML
    private Label loggedInHeader;
    
    @FXML
    private AnchorPane player1;
    @FXML
    private AnchorPane player1Image;
    @FXML
    private AnchorPane player1Card1;
    @FXML
    private AnchorPane player1Card2;
    @FXML
    private AnchorPane player1Card3;
    @FXML
    private AnchorPane player1Card4;
    @FXML
    private AnchorPane player1Card5;
    @FXML
    private Label player1Name;
    @FXML
    private AnchorPane player1CardUp;
    @FXML
    private AnchorPane player1CardPlayed;
    
    @FXML
    private AnchorPane player2;
    @FXML
    private AnchorPane player2Image;
    @FXML
    private AnchorPane player2Card1;
    @FXML
    private AnchorPane player2Card2;
    @FXML
    private AnchorPane player2Card3;
    @FXML
    private AnchorPane player2Card4;
    @FXML
    private AnchorPane player2Card5;
    @FXML
    private Label player2Name;
    @FXML
    private AnchorPane player2CardUp;
    @FXML
    private AnchorPane player2CardPlayed;
    
    @FXML
    private AnchorPane player3;
    @FXML
    private AnchorPane player3Image;
    @FXML
    private AnchorPane player3Card1;
    @FXML
    private AnchorPane player3Card2;
    @FXML
    private AnchorPane player3Card3;
    @FXML
    private AnchorPane player3Card4;
    @FXML
    private AnchorPane player3Card5;
    @FXML
    private Label player3Name;
    @FXML
    private AnchorPane player3CardUp;
    @FXML
    private AnchorPane player3CardPlayed;
    
    @FXML
    private AnchorPane player4;
    @FXML
    private AnchorPane player4Image;
    @FXML
    private AnchorPane player4Card1;
    @FXML
    private AnchorPane player4Card2;
    @FXML
    private AnchorPane player4Card3;
    @FXML
    private AnchorPane player4Card4;
    @FXML
    private AnchorPane player4Card5;
    @FXML
    private Label player4Name;
    @FXML
    private AnchorPane player4CardUp;
    @FXML
    private AnchorPane player4CardPlayed;
    
    @FXML
    private AnchorPane scoreboard;
    @FXML
    private AnchorPane currentTrump;
    @FXML
    private Label team1Name1;
    @FXML
    private Label team1Name3;
    @FXML
    private Label teamOneTricks;
    @FXML
    private Label teamOnePoints;
    @FXML
    private Label team2Name2;
    @FXML
    private Label team2Name4;
    @FXML
    private Label teamTwoTricks;
    @FXML
    private Label teamTwoPoints;
    
    @FXML
    private AnchorPane gameChoices;
    @FXML
    private TextArea gameInfo;
    @FXML
    private CheckBox goAlone;
    @FXML
    private RadioButton spades;
    @FXML
    private Label spadesLabel;
    @FXML
    private RadioButton hearts;
    @FXML
    private Label heartsLabel;
    @FXML
    private RadioButton diamonds;
    @FXML
    private Label diamondsLabel;
    @FXML
    private RadioButton clubs;
    @FXML
    private Label clubsLabel;
    @FXML
    private Label handInformation;
    @FXML
    private TextArea chatBox;
    @FXML
    private TextField chatMessage;
    
    @FXML
    private AnchorPane dealOrder;
    @FXML
    private RadioButton left2;
    @FXML
    private RadioButton left3;
    @FXML
    private RadioButton front2;
    @FXML
    private RadioButton front3;
    @FXML
    private RadioButton right2;
    @FXML
    private RadioButton right3;
    @FXML
    private RadioButton self2;
    @FXML
    private RadioButton self3;
    
    @FXML
    private AnchorPane playDiscardCard;
    @FXML
    private Button discardCard;
    @FXML
    private Button playCard;
    // </editor-fold>
    
    private ChatClient chatClient;
    private EuchreDealer dealer;
    private PlayerPane[] playerPanes;
    private AnchorPane[] playedCards;
    private boolean canPlayCard = false;
    private AnchorPane activeCard = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        
        playerPanes = new PlayerPane[] {
            new PlayerPane(player1, player1Image, Arrays.asList(player1Card1, player1Card2, player1Card3, player1Card4, player1Card5), player1Name, player1CardUp),
            new PlayerPane(player2, player2Image, Arrays.asList(player2Card1, player2Card2, player2Card3, player2Card4, player2Card5), player2Name, player2CardUp),
            new PlayerPane(player3, player3Image, Arrays.asList(player3Card1, player3Card2, player3Card3, player3Card4, player3Card5), player3Name, player3CardUp),
            new PlayerPane(player4, player4Image, Arrays.asList(player4Card1, player4Card2, player4Card3, player4Card4, player4Card5), player4Name, player4CardUp)
        };
        playedCards = new AnchorPane[] { player1CardPlayed, player2CardPlayed, player3CardPlayed, player4CardPlayed };
        
        StringProperty chatBoxText = chatBox.textProperty();
        
//        try{
//            chatClient = new ChatClient("EuchreChatServer", chatBox.textProperty());
//        }
//        catch(RemoteException ex) {
//            ex.printStackTrace(System.out);
//        }
        
        gameInfo.setEditable(false);
        loggedInHeader.setText(UserSessionVars.getUsername());
        
        dealer = new EuchreDealer();
        dealer.addPlayer(111, "Ryan Bickham", 1);
        dealer.addPlayer(222, "Nick Borushko", 2);
        dealer.addPlayer(333, "Ryan Gillett", 3);
        dealer.addPlayer(444, "Andrew Haeger", 4);
        
        dealer.determineDealer();
        updateGameSummary(dealer.getCurrentDealer(), "is the dealer.");
        startNewHand(true);
    }    
    
    @Override
    public void setPreviousScreen(String previous) {
        this.previous = previous;
    }
    
    @Override
    public void closingApplication() {
        System.out.println("Exiting Euchre");
    }
    
    @FXML
    private void goToTablesScreen() {
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void goToLoginScreen() {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
    
    @FXML
    private void showUserStatisticsScreen() {
        controller.setScreen(DesktopCardGameGUI.statisticsScreen);
    }
    
    private void startNewHand(boolean newGame) {
        canPlayCard = false;
        activeCard = null;
        List<Player> players = dealer.getPlayers();
        
        for(Player p : players) {
            activatePlayer(playerPanes[p.getSeatNumber() - 1].getContainer(), p.getUsername(), "");
            int seat = p.getSeatNumber();
            if (seat % 2 == 0) {
                ((Label)scoreboard.lookup("#team2Name" + seat)).setText(p.getUsername());
            }else{
                ((Label)scoreboard.lookup("#team1Name" + seat)).setText(p.getUsername());
            }
        }
        for(PlayerPane p : playerPanes) {
            removeCard(p.getCards().get(0));
            removeCard(p.getCards().get(1));
            removeCard(p.getCards().get(2));
            removeCard(p.getCards().get(3));
            removeCard(p.getCards().get(4));
        }
        dealer.startNewHand(newGame);
        
        AnchorPane previousPlayer = null;
        
        if(dealer.getCurrentPlayerPos() != -1) {
            previousPlayer = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
        }
        showPlayersTurn(playerPanes[dealer.getCurrentDealer().getSeatNumber() - 1].getContainer(), previousPlayer);
        showDealSequencePanel();
    }
    
    private void dealHands() {
        List<AnchorPane> cards;
        for(Player p : dealer.getPlayers()) {
            cards = playerPanes[p.getSeatNumber() - 1].getCards();
            for(int i = 0; i < cards.size(); i++) {
                removeCard(cards.get(i));
                if(p.getHand().size() > i) {
                    showCard(cards.get(i), Deck.cardToString(p.getHand().get(i)));
                    cards.get(i).setVisible(true);
                } else {
                    cards.get(i).setVisible(false);
                }
            }
        }
    }
    
    @FXML
    private void dealCards() {
        String sequence;
        
        // Left
        if(left2.isSelected()) {
            sequence = "2";
        } else {
            sequence = "3";
        }
        // Front
        if(front2.isSelected()) {
            sequence += ", 2";
        } else {
            sequence += ", 3";
        }
        // Right
        if(right2.isSelected()) {
            sequence += ", 2";
        } else {
            sequence += ", 3";
        }
        // Self
        if(self2.isSelected()) {
            sequence += ", 2";
        } else {
            sequence += ", 3";
        }
        
        dealer.dealHands(sequence);
        showGameChoicesPanel();
        dealHands();
        disableAllTrumpChoices();
        showTopCard();
        showAvailableTrump();
        showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), playerPanes[dealer.getCurrentDealer().getSeatNumber() - 1].getContainer());
    }
    
    private void showTopCard() {
        Player p = dealer.getCurrentDealer();
        AnchorPane topCard = playerPanes[p.getSeatNumber() - 1].getTopCard();
        showCard(topCard, dealer.getTopCard());
    }
    
    private void downTopCard() {
        Player p = dealer.getCurrentDealer();
        AnchorPane topCard = playerPanes[p.getSeatNumber() - 1].getTopCard();
        removeCard(topCard);
    }
    
    @FXML
    private void pass() {
        String action;
        AnchorPane previousPlayer = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
        updateGameSummary(dealer.getCurrentPlayer(), "passed.");
        action = dealer.passOnCallingTrump();
        showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), previousPlayer);
        
        switch (action) {
            case "down":
                updateGameSummary(dealer.getTopCard() + " was turned down.");
                downTopCard();
                toggleTrumpChoices();
                break;
            case "muck":
                updateGameSummary("Hand was mucked.");
                resetAfterMuck();
                break;
        }
    }
    
    @FXML
    private void call() {
        boolean alone = goAlone.isSelected();
        int trump;
        String selectTrump = "";
        
        if(clubs.isSelected()) {
            trump = 0;
            selectTrump = "C";
        } else if(diamonds.isSelected()) {
            trump = 1;
            selectTrump = "D";
        } else if(spades.isSelected()) {
            trump = 2;
            selectTrump = "S";
        } else if(hearts.isSelected()) {
            trump = 3;
            selectTrump = "H";
        } else {
            trump = 4;
        }
        
        if(trump < 4) {
            canPlayCard = true;
            if(alone) {
                int curPlayer = dealer.getCurrentPlayerPos();
                if(curPlayer > -1 && curPlayer < 2) {
                    curPlayer += 2;
                } else if(curPlayer > 1 && curPlayer < 4) {
                    curPlayer -= 2;
                }
                playerPanes[curPlayer].getContainer().setOpacity(0.5);
                updateGameSummary(dealer.getCurrentPlayer(), "called " + selectTrump + "'s trump and is going alone.");
            } else {
                updateGameSummary(dealer.getCurrentPlayer(), "called " + selectTrump + "'s trump.");
            }
            dealer.callTrump(trump, alone);
            if(dealer.isCardUp()) {
                AnchorPane previousPlayer = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
                showPlayersTurn(playerPanes[dealer.getCurrentDealer().getSeatNumber() - 1].getContainer(), previousPlayer);
                dealer.setCurrentPlayer(dealer.getCurrentDealerPos());
                showCardActions("discard");
                discardCard.setDisable(true);
            }
            
            showTrump(selectTrump);
            
            if(!dealer.isCardUp()) {
                startHand();
            }
        } else{
            MessageBox.show(null, "You did not select a suit.", "Error", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }
    
    private void showTrump(String trump) {
        currentTrump.getStylesheets().add("cardgamesdesktop/css/Cards.css");
        currentTrump.getStyleClass().remove("cardDefault");
        currentTrump.getStyleClass().add(trump);
    }
    
    private void removeTrump() {
        currentTrump.getStyleClass().remove(1);
        currentTrump.getStyleClass().add("cardDefault");
    }
    
    @FXML
    private void activeCard(MouseEvent event) {
        AnchorPane card = (AnchorPane)event.getSource();
        String source = card.getId();
        String parent = source.substring(0, 7);
        source = parent.replace("player", "");
        
        if(source.equals(Integer.toString(dealer.getCurrentPlayer().getSeatNumber())) && !card.getStyleClass().contains("cardDefault") && (card.getOpacity() != 0.7) && canPlayCard) {
            if(card.getLayoutY() == -10) {
                resetActiveCards(parent);
                playCard.setDisable(true);
                discardCard.setDisable(true);
            } else {
                resetActiveCards(parent);
                card.setLayoutY(-10);
                activeCard = card;
                playCard.setDisable(false);
                discardCard.setDisable(false);
            }
        }
    }
    
    private void resetActiveCards(String player) {
        AnchorPane playerContain = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
        
        for(int i = 1; i <= 5; i++) {
            if(playerContain.lookup("#" + player + "Card" + i).getLayoutY() == -10) {
                playerContain.lookup("#" + player + "Card" + i).setLayoutY(0);
            }
        }
        activeCard = null;
    }
    
    @FXML
    private void cardAction(ActionEvent event) {
        Button action = (Button)event.getSource();
        String id = action.getId();
        
        if(activeCard != null) {
            switch(id) {
                case "playCard":
                    AnchorPane previousPlayer = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
                    resetFollowSuit(dealer.getCurrentPlayer().getSeatNumber());
                    AnchorPane playedCard = playedCards[dealer.getCurrentPlayer().getSeatNumber() - 1];
                    int result;
                    
                    showCard(playedCard, activeCard.getStyleClass().get(0).replace("card", ""));
                    removeCard(activeCard);
                    activeCard.setLayoutY(0);
                    result = dealer.cardPlayed(Integer.parseInt(activeCard.getId().substring(11)) - 1);
                    activeCard = null;
                    playCard.setDisable(true);
                    showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), previousPlayer);
                    followSuit(dealer.getCurrentPlayer().getSeatNumber());
                    if(result == 1) {           // Trick Done
                        resetAfterTrick();
                    } else if(result == 2) {    // Hand Done
                        resetAfterHand();
                    } else if(result == 3) {    // Game Done
                        resetAfterGame();
                    }
                    break;
                case "discardCard":
                    discardCard.setDisable(false);
                    dealer.getCardToReplace(Integer.parseInt(activeCard.getId().substring(11)) - 1);
                    startHand();
                    break;
            }
        } else {
            MessageBox.show(null, "You did not select a card.", "Error", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }
    
    private void startHand() {
        AnchorPane previousPlayer = playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer();
        downTopCard();
        dealer.sortHandsTrumpFirst();
        if(activeCard != null) {
            activeCard.setLayoutY(0);
            activeCard = null;
        }
        dealer.setCurrentPlayer(dealer.nextPlayer(dealer.getCurrentDealerPos()));
        showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), previousPlayer);
        dealHands();
        dealer.startHand();
        updateGameSummary(dealer.getCurrentPlayer(), "'s lead.");
        canPlayCard = true;
        showCardActions("play");
        playCard.setDisable(true);
        discardCard.setDisable(true);
    }
    
    private void followSuit(int player) {
        List<AnchorPane> cards = playerPanes[player - 1].getCards();
        int count = dealer.getCurrentPlayer().getHand().size();
        String leftBower = "";
        String trump = "";
        
        switch(dealer.getTrump()) {
            case 0: //C
                leftBower = "JS";
                trump = "C";
                break;
            case 1: //D
                leftBower = "JH";
                trump = "D";
                break;
            case 2: //S
                leftBower = "JC";
                trump = "S";
                break;
            case 3: //H
                leftBower = "JD";
                trump = "H";
                break;
        }
        
        for (AnchorPane card : cards) {
            if(!card.getStyleClass().contains("cardDefault")) {
                String playedTrump = card.getStyleClass().get(0).substring(5);
                if((!playedTrump.equals(dealer.getLeadSuit())) || ((!trump.equals(dealer.getLeadSuit())) && card.getStyleClass().get(0).substring(4).equals(leftBower))){
                    card.setOpacity(0.7);
                    card.setLayoutY(10);
                    count -= 1;
                }
                if(trump.equals(dealer.getLeadSuit()) && card.getStyleClass().get(0).substring(4).equals(leftBower)){
                    card.setOpacity(1);
                    card.setLayoutY(0);
                    count += 1;
                }
            }
        }
        if(count == 0) {
            resetFollowSuit(player);
        }
    }
    
    private void resetFollowSuit(int player) {
        List<AnchorPane> cards = playerPanes[player - 1].getCards();
        
        for (AnchorPane card : cards) {
            if(!card.getStyleClass().contains("cardDefault")) {
                card.setOpacity(1);
                card.setLayoutY(0);
            }
        }
    }
    
    private void updateTricksScores() {
        teamOneTricks.setText(Integer.toString(dealer.getTeamOneTricks()));
        teamOnePoints.setText(Integer.toString(dealer.getTeamOneScore()));
        teamTwoTricks.setText(Integer.toString(dealer.getTeamTwoTricks()));
        teamTwoPoints.setText(Integer.toString(dealer.getTeamTwoScore()));
    }
    
    private void resetAfterTrick() {
        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
        updateGameSummary(dealer.getCurrentPlayer(), "'s lead.");
        updateTricksScores();
        clearPlayedCards();
        dealHands();
        dealer.startTrick();
    }
    
    private void resetAfterMuck() {
        startNewHand(false);
    }
    
    private void resetAfterHand() {
        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
        updateGameSummary(null, dealer.getWinner());
        updateTricksScores();
        clearPlayedCards();
        removeTrump();
        startNewHand(false);
    }
    
    private void resetAfterGame() {
        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
        updateGameSummary(null, dealer.getWinner());
        updateTricksScores();
        clearPlayedCards();
        startNewHand(false);
    }
    
    private void clearPlayedCards() {
        for(AnchorPane played : playedCards) {
            removeCard(played);
        }
    }
    
    private void updateGameSummary(String action) {
        gameInfo.appendText(String.format("%s\n", action));
    }
    
    private void updateGameSummary(Player player, String action) {
        if(player != null) {
            gameInfo.appendText(String.format("%s %s\n", player.getUsername(), action));
        } else {
            gameInfo.appendText(String.format("%s\n", action));
        }
    }
    
    @FXML
    private void sendMessage() {
        try {
            String message = UserSessionVars.getDisplayName() + ": " + chatMessage.getText();
            chatClient.sendChatMessage(message);
        }
        catch(NullPointerException ex) {
            chatBox.setText("The chat server is currently unavailable.");
        }
        chatMessage.setText("");
    }
    
    private void showAvailableTrump() {
        if(dealer.isCardUp()) {
            switch (dealer.getTopCard().charAt(1)){
                case 'C':
                    changeClubsOption(false, true);
                    break;
                case 'D':
                    changeDiamondsOption(false, true);
                    break;
                case 'S':
                    changeSpadesOption(false, true);
                    break;
                case 'H':
                    changeHeartsOption(false, true);
                    break;
            }
        } else {
            toggleTrumpChoices();
        }
    }
    
    private void disableAllTrumpChoices() {
        changeSpadesOption(true, false);
        changeHeartsOption(true, false);
        changeDiamondsOption(true, false);
        changeClubsOption(true, false);
    }
    
    private void toggleTrumpChoices() {
        changeSpadesOption(!spades.isDisabled(), false);
        changeHeartsOption(!hearts.isDisabled(), false);
        changeDiamondsOption(!diamonds.isDisabled(), false);
        changeClubsOption(!clubs.isDisabled(), false);
    }
    
    private void changeSpadesOption(boolean disable, boolean selected) {
        spades.setDisable(disable);
        spadesLabel.setDisable(disable);
        spades.setSelected(selected);
    }
    
    private void changeHeartsOption(boolean disable, boolean selected)  {
        hearts.setDisable(disable);
        heartsLabel.setDisable(disable);
        hearts.setSelected(selected);
    }
    
    private void changeDiamondsOption(boolean disable, boolean selected) {
        diamonds.setDisable(disable);
        diamondsLabel.setDisable(disable);
        diamonds.setSelected(selected);
    }
    
    private void changeClubsOption(boolean disable, boolean selected) {
        clubs.setDisable(disable);
        clubsLabel.setDisable(disable);
        clubs.setSelected(selected);
    }
    
    private void showDealSequencePanel() {
        dealOrder.setVisible(true);
        gameChoices.setVisible(false);
        playDiscardCard.setVisible(false);
    }
    
    private void showGameChoicesPanel() {
        gameChoices.setVisible(true);
        dealOrder.setVisible(false);
        playDiscardCard.setVisible(false);
    }
    
    private void showCardActions(String action) {
        showCardActionPanel();
        switch(action) {
            case "discard":
                discardCard.setVisible(true);
                playCard.setVisible(false);
                break;
            case "play":
                playCard.setVisible(true);
                discardCard.setVisible(false);
                break;
        }
    }
    
    private void showCardActionPanel() {
        playDiscardCard.setVisible(true);
        gameChoices.setVisible(false);
        dealOrder.setVisible(false);
    }
}