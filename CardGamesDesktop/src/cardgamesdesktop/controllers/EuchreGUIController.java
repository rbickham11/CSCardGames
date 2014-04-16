package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.StringProperty;
import java.rmi.*;
import java.rmi.registry.*;

import cardgamesdesktop.utilities.*;
import cardgameslib.games.IEuchreDealer;


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
    private AnchorPane currentTrump;
    @FXML
    private Label teamOneName;
    @FXML
    private Label teamOneTricks;
    @FXML
    private Label teamOnePoints;
    @FXML
    private Label teamTwoName;
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
    // </editor-fold>
    
    private IEuchreDealer dealer;
    private ChatClient chatClient;
    //private EuchreDealer dealer;
    private PlayerPane[] playerPanes;
    private AnchorPane[] playedCards;
    
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
               
//        gameInfo.setEditable(false);
//        loggedInHeader.setText(UserSessionVars.getUsername());
//        
//        dealer = new EuchreDealer();
//        dealer.addPlayer(111, "Ryan Bickham", 1);
//        dealer.addPlayer(222, "Nick Borushko", 2);
//        dealer.addPlayer(333, "Ryan Gillett", 3);
//        dealer.addPlayer(444, "Andrew Haeger", 4);
//        
//        dealer.determineDealer();
//        showPlayersTurn(playerPanes[dealer.getCurrentDealer().getSeatNumber() - 1].getContainer(), null);
//        startNewHand(true);
    }    
    
    @Override
    public void connectTable(String tableId, String chatId) {
        Registry registry = RMIConnection.getInstance().getRegistry();
        try {
            dealer = (IEuchreDealer)registry.lookup(tableId);
            chatClient = new ChatClient(chatId, chatBox.textProperty());
        }
        catch(NotBoundException ex) {
            System.out.println("The requested remote object " + tableId + " was not found");
            ex.printStackTrace(System.out);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
//    private void startNewHand(boolean newGame) {
//        List<Player> players = dealer.getPlayers();
//        
//        for(Player p : players) {
//            activatePlayer(playerPanes[p.getSeatNumber() - 1].getContainer(), p.getUsername(), "");
//        }
//        for(PlayerPane p : playerPanes) {
//            removeCard(p.getCards().get(0));
//            removeCard(p.getCards().get(1));
//            removeCard(p.getCards().get(2));
//            removeCard(p.getCards().get(3));
//            removeCard(p.getCards().get(4));
//        }
//        
//        dealer.startNewHand(newGame);
//        showDealSequencePanel();
//    }
//    
//    private void showDealSequencePanel() {
//        dealOrder.setVisible(true);
//        gameChoices.setVisible(false);
//    }
//    
//    private void showGameChoicesPanel() {
//        gameChoices.setVisible(true);
//        dealOrder.setVisible(false);
//    }
//    
//    private void dealHands() {
//        List<AnchorPane> cards;
//        for(Player p : dealer.getPlayers()) {
//            cards = playerPanes[p.getSeatNumber() - 1].getCards();
//            for(int i = 0; i < cards.size(); i++) {
//                showCard(cards.get(i), Deck.cardToString(p.getHand().get(i)));
//            }
//        }
//    }
//    
    
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
    
    @FXML
    private void pass() {
        
    }
    
    @FXML
    private void call() {
        
    }
    
    @FXML
    private void dealCards() {
        String sequence = "";
        
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
        
//        dealer.dealHands(sequence);
//        showGameChoicesPanel();
//        
//        dealHands();
//        dealer.displayPlayersHands();
    }
    
    private void showTopCard() {
        
    }
    
    private void showAvailableTrump() {
        
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

    @Override
    public void closingApplication() {
    }
}