package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import cardgamesdesktop.utilities.*;
import java.rmi.RemoteException;
import javafx.beans.property.StringProperty;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class HoldEmGUIController implements Initializable, ControlledScreen {

    //  Seat Configuration
    //      9       1
    //  8               2
    //  7               3
    //      6   5   4
    
    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    @FXML
    private Label loggedInHeader;
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
    
    ScreensController controller;
    // </editor-fold>
    
    private ChatClient chatClient;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInHeader.setVisible(false);
        StringProperty chatBoxText = chatBox.textProperty();
        try{
            chatClient = new ChatClient(chatBoxText);
        }
        catch(RemoteException ex){ex.printStackTrace(System.out);}
        
        betAmountSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                betAmount.setText(Integer.toString(newValue.intValue()));
            }
        });
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void goToTablesScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
    
    @FXML
    private void bet(ActionEvent event) {
        
    }
    
    @FXML
    private void call(ActionEvent event) {
        
    }
    
    @FXML
    private void raise(ActionEvent event) {
        
    }
    
    @FXML
    private void check(ActionEvent event) {
        
    }
    
    @FXML
    private void fold(ActionEvent event) {
        
    }
    
    @FXML
    private void sendMessage(ActionEvent event) {
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