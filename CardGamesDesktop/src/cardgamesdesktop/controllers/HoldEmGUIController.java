package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import cardgamesdesktop.receivers.HoldemReceiver;
import java.net.URL;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.StringProperty;

import cardgamesdesktop.utilities.*;
import cardgameslib.games.IHoldemDealer;
import cardgameslib.utilities.*;
import javafx.application.Platform;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class HoldEmGUIController extends GameController implements Initializable {

    //  Seat Configuration
    //      9       1
    //  8               2
    //  7               3
    //      6   5   4
    
    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    
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
    private TextField betAmount;
    
    @FXML
    private Button betButton;
    @FXML
    private Button callButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button foldButton;
    @FXML
    private Button raiseButton;
    
    // </editor-fold>
    
    private ChatClient chatClient;
    private IHoldemDealer dealer;
    private final int bigBlind = 200;
    private PlayerPane[] playerPanes;
    private AnchorPane[] boardCardPanes;
    private HoldemReceiver client;
    
    private int thisPlayerSeat;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        playerPanes = new PlayerPane[] {
            new PlayerPane(player1, player1Image, Arrays.asList(player1Card1, player1Card2), player1Name, player1ChipCount, player1BetAmount),
            new PlayerPane(player2, player2Image, Arrays.asList(player2Card1, player2Card2), player2Name, player2ChipCount, player2BetAmount),
            new PlayerPane(player3, player3Image, Arrays.asList(player3Card1, player3Card2), player3Name, player3ChipCount, player3BetAmount),
            new PlayerPane(player4, player4Image, Arrays.asList(player4Card1, player4Card2), player4Name, player4ChipCount, player4BetAmount),
            new PlayerPane(player5, player5Image, Arrays.asList(player5Card1, player5Card2), player5Name, player5ChipCount, player5BetAmount),
            new PlayerPane(player6, player6Image, Arrays.asList(player6Card1, player6Card2), player6Name, player6ChipCount, player6BetAmount),
            new PlayerPane(player7, player7Image, Arrays.asList(player7Card1, player7Card2), player7Name, player7ChipCount, player7BetAmount),
            new PlayerPane(player8, player8Image, Arrays.asList(player8Card1, player8Card2), player8Name, player8ChipCount, player8BetAmount),     
            new PlayerPane(player9, player9Image, Arrays.asList(player9Card1, player9Card2), player9Name, player9ChipCount, player9BetAmount)
        };
        boardCardPanes = new AnchorPane[] { houseCard1, houseCard2, houseCard3, houseCard4, houseCard5 };
        StringProperty chatBoxText = chatBox.textProperty();
        
        gameInfo.setEditable(false);
        loggedInHeader.setText(UserSessionVars.getUsername());
        betAmountSlider.setMajorTickUnit(bigBlind);
        betAmountSlider.setMinorTickCount(0);
        betAmountSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                betAmount.setText(Integer.toString(newValue.intValue()));
            }
        });
    }
    
    @Override
    public void connectTable(String tableId, String chatId) {
        Registry registry = RMIConnection.getInstance().getRegistry();
        try {
            dealer = (IHoldemDealer)registry.lookup(tableId);
            chatClient = new ChatClient(chatId, chatBox.textProperty());
            joinTableOpenSeats.getItems().clear();
            joinTableOpenSeats.getItems().addAll(dealer.getAvailableSeats());
            client = new HoldemReceiver(this);
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
    private void goToTablesScreen() {
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void goToLoginScreen() {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
    
    @FXML
    private void joinTable() {
        try {
            int startingChips = Integer.parseInt(joinTableStartingChips.getText());
            int seatNumber = Integer.parseInt(joinTableOpenSeats.getSelectionModel().getSelectedItem().toString());
            dealer.addPlayer(UserSessionVars.getUserId(), UserSessionVars.getUsername(), seatNumber, startingChips);
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
    
    @FXML
    private void bet() {
        handleAction(PokerAction.BET);
    }
    
    @FXML
    private void call() {
        handleAction(PokerAction.CALL);
    }
    
    @FXML
    private void raise() {
        handleAction(PokerAction.RAISE);
    }
    
    @FXML
    private void check() {
        handleAction(PokerAction.CHECK);
    }
    
    @FXML
    private void fold() {
        handleAction(PokerAction.FOLD);
    }
    
    private void handleAction(PokerAction action) {

    }
    
    private void updateGameSummary(BettingPlayer player, PokerAction action) {
        switch(action) {
            case BET:
                gameInfo.appendText(String.format("%s bets %d\n", player.getUsername(), player.getCurrentBet()));
                return;
            case CALL:
                gameInfo.appendText(String.format("%s calls %d\n", player.getUsername(), player.getCurrentBet()));
                return;
            case CHECK:
                gameInfo.appendText(String.format("%s checks\n", player.getUsername()));
                return;
            case FOLD:
                gameInfo.appendText(String.format("%s folds\n", player.getUsername()));
                return;
            case RAISE:
                gameInfo.appendText(String.format("%s raises to %d\n", player.getUsername(), player.getCurrentBet()));
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
    
    public void initializePlayers() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(BettingPlayer p : dealer.getPlayers()) {
                        PlayerPane pane = playerPanes[p.getSeatNumber() - 1];
                        activatePlayer(pane.getContainer(), p.getUsername(), Integer.toString(p.getChips()));
                        pane.getName().setText(p.getUsername());
                        //Update image
                    }
                    for(BettingPlayer p : dealer.getActivePlayers()) {
                        updateChipValues(p);
                    }
                    displayCards();
                }
                catch(RemoteException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
    }
    
    public void displayCards() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(BettingPlayer p : dealer.getActivePlayers()) {
                        List<AnchorPane> cardPanes = playerPanes[p.getSeatNumber() - 1].getCards();
                        if(p.getSeatNumber() == thisPlayerSeat) {
                            for(int i = 0; i < cardPanes.size(); i++) {
                                removeCard(cardPanes.get(i));
                                showCard(cardPanes.get(i), Deck.cardToString(p.getHand().get(i)));
                            }
                        }
                        else {
                            for(int i = 0; i < cardPanes.size(); i++) {
                                removeCard(cardPanes.get(i));
                                showCard(cardPanes.get(i), "Blue");
                            }           
                        }
                    }
                }
                catch(RemoteException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
    }
    
    public void foldHand(final BettingPlayer p) {
    
    }
    
    public void updateChipValues(final BettingPlayer p) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    List<BettingPlayer> activePlayers = dealer.getActivePlayers();
                    PlayerPane pane = playerPanes[p.getSeatNumber() - 1];
                    pane.getChips().setText(Integer.toString(p.getChips()));
                    pane.getBetAmount().setText(Integer.toString(p.getCurrentBet()));
                    potSize.setText(String.format("Pot Size: $%d", dealer.getPotSize()));
                }
                catch(RemoteException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
    }
}