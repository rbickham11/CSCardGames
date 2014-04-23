package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import cardgamesdesktop.receivers.EuchreReceiver;
import cardgamesdesktop.utilities.*;
import cardgameslib.games.IEuchreDealer;
import cardgameslib.utilities.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.*;
import javafx.application.Platform;
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
public class EuchreGUIController extends GameController implements Initializable {

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
    private AnchorPane joinTableOverlay;
    @FXML
    private ComboBox joinTableOpenSeats;
    @FXML
    private TextField joinTableStartingChips;
    @FXML
    private Button joinTableCancel;
    @FXML
    private Button joinTableJoin;
 
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
    private IEuchreDealer dealer;
    private PlayerPane[] playerPanes;
    private AnchorPane[] playedCards;
    private boolean canPlayCard = false;
    private AnchorPane activeCard = null;
    private EuchreReceiver client;
    
    private int thisPlayerSeat;
    
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

        gameInfo.setEditable(false);
        loggedInHeader.setText(UserSessionVars.getUsername());
    }    
    
    public void initializePlayers() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(Player p : dealer.getPlayers()) {
                        PlayerPane pane = playerPanes[p.getSeatNumber() - 1];
                        activatePlayer(pane.getContainer(), p.getUsername(), "");
                        activatePlayer(playerPanes[p.getSeatNumber() - 1].getContainer(), p.getUsername(), "");
                        int seat = p.getSeatNumber();
                        if (seat % 2 == 0) {
                            ((Label)scoreboard.lookup("#team2Name" + seat)).setText(p.getUsername());
                        }else{
                            ((Label)scoreboard.lookup("#team1Name" + seat)).setText(p.getUsername());
                        }
                    }
                } catch(RemoteException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
    }
    
    @Override
    public void connectTable(String tableId, String chatId) {
        Registry registry = RMIConnection.getInstance().getRegistry();
        try {
            dealer = (IEuchreDealer)registry.lookup(tableId);
            chatClient = new ChatClient(chatId, chatBox.textProperty());
            joinTableOpenSeats.getItems().clear();
            joinTableOpenSeats.getItems().addAll(dealer.getAvailableSeats());
            client = new EuchreReceiver(this, UserSessionVars.getUserId());
            dealer.addClient(client);
        }
        catch(NotBoundException ex) {
            System.out.println("The requested remote object " + tableId + " was not found");
            ex.printStackTrace(System.out);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @FXML
    private void joinTable() {
        try {
            int seatNumber = Integer.parseInt(joinTableOpenSeats.getSelectionModel().getSelectedItem().toString());
            dealer.addPlayer(UserSessionVars.getUserId(), UserSessionVars.getUsername(), seatNumber);
            thisPlayerSeat = seatNumber;
            joinTableOverlay.setVisible(false);
        }
        catch(NumberFormatException ex) {
            ex.printStackTrace(System.out);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        catch(IllegalArgumentException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @FXML
    private void cancelJoin() {
        goToTablesScreen();
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
//        canPlayCard = false;
//        activeCard = null;
//
//        for(PlayerPane p : playerPanes) {
//            removeCard(p.getCards().get(0));
//            removeCard(p.getCards().get(1));
//            removeCard(p.getCards().get(2));
//            removeCard(p.getCards().get(3));
//            removeCard(p.getCards().get(4));
//        }
//        AnchorPane previousPlayer = null;
//        
//        showDealSequencePanel();
    }
    
    public void resetPlayersCards() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(PlayerPane p : playerPanes) {
                    removeCard(p.getCards().get(0));
                    removeCard(p.getCards().get(1));
                    removeCard(p.getCards().get(2));
                    removeCard(p.getCards().get(3));
                    removeCard(p.getCards().get(4));
                }
            }
        });
    }
    
    public void dealHands() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(Player p : dealer.getPlayers()) {
                        List<AnchorPane> cardPanes = playerPanes[p.getSeatNumber() - 1].getCards();
                        if(p.getSeatNumber() == thisPlayerSeat) {
                            for(int i = 0; i < cardPanes.size(); i++) {
                                removeCard(cardPanes.get(i));
                                if(p.getHand().size() > i) {
                                    showCard(cardPanes.get(i), Deck.cardToString(p.getHand().get(i)));
                                    cardPanes.get(i).setVisible(true);
                                } else {
                                    cardPanes.get(i).setVisible(false);
                                }
                            }
                        } else {
                            for(int i = 0; i < cardPanes.size(); i++) {
                                removeCard(cardPanes.get(i));
                                if(p.getHand().size() > i) {
                                    showCard(cardPanes.get(i), "Red");
                                    cardPanes.get(i).setVisible(true);
                                } else {
                                    cardPanes.get(i).setVisible(false);
                                }
                            }          
                        }
                    }
                } catch(RemoteException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
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
        
        try {
            dealer.dealHands(sequence);
        } catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        
        hidePlayerOptions();
    }
    
    public void showTopCard(final int dealer, final String topCard) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AnchorPane topCardContain = playerPanes[dealer - 1].getTopCard();
                showCard(topCardContain, topCard);
            }
        });
    }
    
    public void downTopCard(final int dealer) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AnchorPane topCardContain = playerPanes[dealer - 1].getTopCard();
                removeCard(topCardContain);
            }
        });
    }
    
    @FXML
    private void pass() {
//        updateGameSummary(dealer.getCurrentPlayer(), "passed.");
        try {
            dealer.passOnCallingTrump();
        } catch(RemoteException ex) {}
//        switch (action) {
//            case "down":
//                updateGameSummary(dealer.getTopCard() + " was turned down.");
//            case "muck":
//                updateGameSummary("Hand was mucked.");
//                break;
//        }
    }
    
    @FXML
    private void call() {
        boolean alone = goAlone.isSelected();
        int trump;
        
        if(clubs.isSelected()) {
            trump = 0;
        } else if(diamonds.isSelected()) {
            trump = 1;
        } else if(spades.isSelected()) {
            trump = 2;
        } else if(hearts.isSelected()) {
            trump = 3;
        } else {
            trump = 4;
        }
        
        if(trump < 4) {
//            if(alone) {
//                updateGameSummary(dealer.getCurrentPlayer(), "called " + selectTrump + "'s trump and is going alone.");
//            } else {
//                updateGameSummary(dealer.getCurrentPlayer(), "called " + selectTrump + "'s trump.");
//            }
            try {
                dealer.callTrump(trump, alone);
            } catch(RemoteException ex) {}
        } else{
            MessageBox.show(null, "You did not select a suit.", "Error", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }
    
    public void showTrump(final String trump) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentTrump.getStylesheets().add("cardgamesdesktop/css/Cards.css");
                currentTrump.getStyleClass().remove("cardDefault");
                currentTrump.getStyleClass().add(trump);
            }
        });
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
        
        if(playerPanes[thisPlayerSeat - 1].getName().getStyleClass().contains("playersTurn") && !card.getStyleClass().contains("cardDefault") && (card.getOpacity() != 0.7) && canPlayCard) {
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
        AnchorPane playerContain = playerPanes[thisPlayerSeat - 1].getContainer();
        
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
                    try {
                        resetFollowSuit(thisPlayerSeat);
                        //AnchorPane playedCard = playedCards[thisPlayerSeat - 1];
                        
                        //showCard(playedCard, activeCard.getStyleClass().get(0).replace("card", ""));
                        removeCard(activeCard);
                        
                        dealer.cardPlayed(Integer.parseInt(activeCard.getId().substring(11)) - 1);
                        playCard.setDisable(true);
                        activeCard.setLayoutY(0);
                        activeCard = null;
                    } catch(RemoteException ex) {}
                    break;
                case "discardCard":
                    try {
                        discardCard.setDisable(false);
                        dealer.getCardToReplace(Integer.parseInt(activeCard.getId().substring(11)) - 1);
                        if(activeCard != null) {
                            activeCard.setLayoutY(0);
                            activeCard = null;
                        }
                    } catch(RemoteException ex) {}
                    break;
            }
        } else {
            MessageBox.show(null, "You did not select a card.", "Error", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }
    
    private void startHand() {
//        updateGameSummary(dealer.getCurrentPlayer(), "'s lead.");
    }
    
    public void followSuit(final List<String> canNotPlay) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<AnchorPane> cards = playerPanes[thisPlayerSeat - 1].getCards();
                if(!canNotPlay.isEmpty()) {
                    for(AnchorPane card : cards) {
                        if(!card.getStyleClass().contains("cardDefault")) {
                            if(canNotPlay.contains(card.getStyleClass().get(0).substring(4))){
                                card.setOpacity(0.7);
                                card.setLayoutY(10);
                            }
                        }
                    }
                }
            }
        });
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
        try {
            teamOneTricks.setText(Integer.toString(dealer.getTeamOneTricks()));
            teamOnePoints.setText(Integer.toString(dealer.getTeamOneScore()));
            teamTwoTricks.setText(Integer.toString(dealer.getTeamTwoTricks()));
            teamTwoPoints.setText(Integer.toString(dealer.getTeamTwoScore()));
        } catch(RemoteException ex) {}
    }
    
    public void resetAfterTrick() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
//        updateGameSummary(dealer.getCurrentPlayer(), "'s lead.");
                updateTricksScores();
                clearPlayedCards();
                dealHands();
//        dealer.startTrick();
            }
        });
    }
    
    public void resetAfterHand() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
//        updateGameSummary(null, dealer.getWinner());
                updateTricksScores();
                clearPlayedCards();
                removeTrump();
//        startNewHand(false);
            }
        });
    }
    
    public void resetAfterGame() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//        updateGameSummary(dealer.getCurrentPlayer(), "won the trick.");
//        updateGameSummary(null, dealer.getWinner());
                updateTricksScores();
                clearPlayedCards();
//        startNewHand(false);
            }
        });
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
    
    public void showAvailableTrump() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    if(dealer.isCardUp()) {
                        disableAllTrumpChoices();
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
                } catch(RemoteException ex) {}
        
            }
        });
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
    
    public void hidePlayerOptions() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dealOrder.setVisible(false);
                gameChoices.setVisible(false);
                playDiscardCard.setVisible(false);
            }
        });
    }
    
    public void showDealSequencePanel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dealOrder.setVisible(true);
                gameChoices.setVisible(false);
                playDiscardCard.setVisible(false);
            }
        });
    }
    
    public void showGameChoicesPanel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameChoices.setVisible(true);
                dealOrder.setVisible(false);
                playDiscardCard.setVisible(false);
            }
        });
    }
    
    public void showCardActions(final String action) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showCardActionPanel();
                switch(action) {
                    case "discard":
                        discardCard.setVisible(true);
                        playCard.setVisible(false);
                        discardCard.setDisable(true);
                        break;
                    case "play":
                        playCard.setVisible(true);
                        discardCard.setVisible(false);
                        playCard.setDisable(true);
                        break;
                }
            }
        });
    }
    
    private void showCardActionPanel() {
        playDiscardCard.setVisible(true);
        gameChoices.setVisible(false);
        dealOrder.setVisible(false);
    }
    
    public void showPlayedCard(final int player, final String card) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AnchorPane playedCard = playedCards[player - 1];
                showCard(playedCard, card);
            }
        });
    }
    
    public void setCanPlayCard(final boolean can) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                canPlayCard = can;
            }
        });
    }
    
    public void showActivePlayer(final int currentSeat, final int lastSeat) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AnchorPane lastContainer;
                if(lastSeat == 0) {
                    lastContainer = null;
                } 
                else {
                    lastContainer = playerPanes[lastSeat - 1].getContainer();
                }
                showPlayersTurn(playerPanes[currentSeat - 1].getContainer(), lastContainer);
            }
        });
    }
}