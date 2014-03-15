package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import cardgamesdesktop.utilities.*;
import cardgameslib.games.poker.holdem.HoldemDealer;
import cardgameslib.utilities.BettingPlayer;
import cardgameslib.utilities.Deck;
import java.rmi.RemoteException;
import java.util.*;
import javafx.beans.property.StringProperty;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class HoldEmGUIController extends GameController implements Initializable, Screens {

    //  Seat Configuration
    //      9       1
    //  8               2
    //  7               3
    //      6   5   4
    
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
    private Label player1Name;
    @FXML
    private Label player1ChipCount;
    @FXML
    private AnchorPane player1DealerButton;
    @FXML
    private Label player1BetAmount;
    @FXML
    private AnchorPane player1ChipBetImage;
    
    @FXML
    private AnchorPane player2;
    @FXML
    private AnchorPane player2Image;
    @FXML
    private AnchorPane player2Card1;
    @FXML
    private AnchorPane player2Card2;
    @FXML
    private Label player2Name;
    @FXML
    private Label player2ChipCount;
    @FXML
    private AnchorPane player2DealerButton;
    @FXML
    private Label player2BetAmount;
    @FXML
    private AnchorPane player2ChipBetImage;
    
    @FXML
    private AnchorPane player3;
    @FXML
    private AnchorPane player3Image;
    @FXML
    private AnchorPane player3Card1;
    @FXML
    private AnchorPane player3Card2;
    @FXML
    private Label player3Name;
    @FXML
    private Label player3ChipCount;
    @FXML
    private AnchorPane player3DealerButton;
    @FXML
    private Label player3BetAmount;
    @FXML
    private AnchorPane player3ChipBetImage;
    
    @FXML
    private AnchorPane player4;
    @FXML
    private AnchorPane player4Image;
    @FXML
    private AnchorPane player4Card1;
    @FXML
    private AnchorPane player4Card2;
    @FXML
    private Label player4Name;
    @FXML
    private Label player4ChipCount;
    @FXML
    private AnchorPane player4DealerButton;
    @FXML
    private Label player4BetAmount;
    @FXML
    private AnchorPane player4ChipBetImage;
    
    @FXML
    private AnchorPane player5;
    @FXML
    private AnchorPane player5Image;
    @FXML
    private AnchorPane player5Card1;
    @FXML
    private AnchorPane player5Card2;
    @FXML
    private Label player5Name;
    @FXML
    private Label player5ChipCount;
    @FXML
    private AnchorPane player5DealerButton;
    @FXML
    private Label player5BetAmount;
    @FXML
    private AnchorPane player5ChipBetImage;
    
    @FXML
    private AnchorPane player6;
    @FXML
    private AnchorPane player6Image;
    @FXML
    private AnchorPane player6Card1;
    @FXML
    private AnchorPane player6Card2;
    @FXML
    private Label player6Name;
    @FXML
    private Label player6ChipCount;
    @FXML
    private AnchorPane player6DealerButton;
    @FXML
    private Label player6BetAmount;
    @FXML
    private AnchorPane player6ChipBetImage;
    
    @FXML
    private AnchorPane player7;
    @FXML
    private AnchorPane player7Image;
    @FXML
    private AnchorPane player7Card1;
    @FXML
    private AnchorPane player7Card2;
    @FXML
    private Label player7Name;
    @FXML
    private Label player7ChipCount;
    @FXML
    private AnchorPane player7DealerButton;
    @FXML
    private Label player7BetAmount;
    @FXML
    private AnchorPane player7ChipBetImage;
    
    @FXML
    private AnchorPane player8;
    @FXML
    private AnchorPane player8Image;
    @FXML
    private AnchorPane player8Card1;
    @FXML
    private AnchorPane player8Card2;
    @FXML
    private Label player8Name;
    @FXML
    private Label player8ChipCount;
    @FXML
    private AnchorPane player8DealerButton;
    @FXML
    private Label player8BetAmount;
    @FXML
    private AnchorPane player8ChipBetImage;
    
    @FXML
    private AnchorPane player9;
    @FXML
    private AnchorPane player9Image;
    @FXML
    private AnchorPane player9Card1;
    @FXML
    private AnchorPane player9Card2;
    @FXML
    private Label player9Name;
    @FXML
    private Label player9ChipCount;
    @FXML
    private AnchorPane player9DealerButton;
    @FXML
    private Label player9BetAmount;
    @FXML
    private AnchorPane player9ChipBetImage;
    
    @FXML
    private Label potSize;
    @FXML
    private AnchorPane houseCard1;
    @FXML
    private AnchorPane houseCard2;
    @FXML
    private AnchorPane houseCard3;
    @FXML
    private AnchorPane houseCard4;
    @FXML
    private AnchorPane houseCard5;
    
    @FXML
    private TextArea gameInfo;
    @FXML
    private Label handInformation;
    @FXML
    private TextArea chatBox;
    @FXML
    private TextField chatMessage;
    @FXML
    private Slider betAmountSlider;
    @FXML
    private Label betAmount;
    
    // </editor-fold>
    
    private ChatClient chatClient;
    private HoldemDealer dealer;
    private final int bigBlind = 200;
    private PlayerPane[] playerPanes;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        playerPanes = new PlayerPane[] {
            new PlayerPane(player1, player1Image, Arrays.asList(player1Card1, player1Card2), player1Name, player1ChipCount),
            new PlayerPane(player2, player2Image, Arrays.asList(player2Card1, player2Card2), player2Name, player2ChipCount),
            new PlayerPane(player3, player3Image, Arrays.asList(player3Card1, player3Card2), player3Name, player3ChipCount),
            new PlayerPane(player4, player4Image, Arrays.asList(player4Card1, player4Card2), player4Name, player4ChipCount),
            new PlayerPane(player5, player5Image, Arrays.asList(player5Card1, player5Card2), player5Name, player5ChipCount),
            new PlayerPane(player6, player6Image, Arrays.asList(player6Card1, player6Card2), player6Name, player6ChipCount),
            new PlayerPane(player7, player7Image, Arrays.asList(player7Card1, player7Card2), player7Name, player7ChipCount),
            new PlayerPane(player8, player8Image, Arrays.asList(player8Card1, player8Card2), player8Name, player8ChipCount),     
            new PlayerPane(player9, player9Image, Arrays.asList(player9Card1, player9Card2), player9Name, player9ChipCount)
        };
        
        StringProperty chatBoxText = chatBox.textProperty();
        try{
            chatClient = new ChatClient(chatBoxText);
        }
        catch(RemoteException ex){ex.printStackTrace(System.out);}
        
        loggedInHeader.setText(UserSessionVars.getUsername());
        betAmountSlider.setMin(bigBlind);
        betAmountSlider.setMajorTickUnit(bigBlind);
        betAmountSlider.setMinorTickCount(0);
        betAmountSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                betAmount.setText(Integer.toString(newValue.intValue()));
            }
        });
        
        dealer = new HoldemDealer(20000, bigBlind);
        dealer.addPlayer(111, "Ryan", 5, 20000);
        dealer.addPlayer(222, "Andrew", 1, 18500);
        dealer.addPlayer(333, "Nick", 7, 15000);
        dealer.addPlayer(444, "RyanG", 3, 10000);
        
        for(BettingPlayer p : dealer.getPlayers()) {
            activatePlayer(playerPanes[p.getSeatNumber() - 1].getContainer(), p.getUsername(), Integer.toString(p.getChips()));
        }
        showPlayersTurn(player5, null);
        dealer.startHand();
        dealHands();
    }
    
    public void dealHands() {
        List<AnchorPane> cards;
        for(BettingPlayer p : dealer.getActivePlayers()) {
            cards = playerPanes[p.getSeatNumber() - 1].getCards();
            for(int i = 0; i < cards.size(); i++) {
                showCard(cards.get(i), Deck.cardToString(p.getHand().get(i)));
            }
        }
        //showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), null);
    }
    
    @Override
    public void setPreviousScreen(String previous) {
        this.previous = previous;
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
    private void bet() {
        
    }
    
    @FXML
    private void call() {
        
    }
    
    @FXML
    private void raise() {
        
    }
    
    @FXML
    private void check() {
        
    }
    
    @FXML
    private void fold() {
        
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
}