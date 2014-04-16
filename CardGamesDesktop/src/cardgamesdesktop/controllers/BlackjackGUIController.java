package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import cardgamesdesktop.utilities.ChatClient;
import cardgamesdesktop.utilities.RMIConnection;
import cardgameslib.games.IBlackjackDealer;
import java.net.URL;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.ResourceBundle;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class BlackjackGUIController extends GameController implements Initializable {

    // Seat Configuration
    //  1   2   3   4   5
    
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
    private Label player1Name;
    @FXML
    private Label player1ChipCount;
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
    private Label player5BetAmount;
    @FXML
    private AnchorPane player5ChipBetImage;
    
    @FXML
    private AnchorPane houseCard1;
    @FXML
    private AnchorPane houseCard2;
    
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
    // </editor-fold>
    
    IBlackjackDealer dealer;
    ChatClient chatClient;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        
        loggedInHeader.setVisible(false);
        betAmount.setText(Integer.toString((int)betAmountSlider.getValue()));
        
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
            dealer = (IBlackjackDealer)registry.lookup(tableId);
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
    private void insurance() {
        
    }
    
    @FXML
    private void split() {
        
    }
    
    @FXML
    private void doubleDown() {
        
    }
    
    @FXML
    private void hit() {
        
    }
    
    @FXML
    private void stand() {
        
    }
    
    @FXML
    private void surrender() {
        
    }
    
    @FXML
    private void sendMessage() {
        
    }

    @Override
    public void closingApplication() {
    }
}