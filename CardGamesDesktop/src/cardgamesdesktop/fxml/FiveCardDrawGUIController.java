package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
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
public class FiveCardDrawGUIController extends GameControllerHelper implements Initializable, Screens {

    // Seat Configuration
    //      7       1
    //  6               2
    //      5   4   3
    
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
    private AnchorPane player2Card3;
    @FXML
    private AnchorPane player2Card4;
    @FXML
    private AnchorPane player2Card5;
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
    private AnchorPane player3Card3;
    @FXML
    private AnchorPane player3Card4;
    @FXML
    private AnchorPane player3Card5;
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
    private AnchorPane player4Card3;
    @FXML
    private AnchorPane player4Card4;
    @FXML
    private AnchorPane player4Card5;
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
    private AnchorPane player5Card3;
    @FXML
    private AnchorPane player5Card4;
    @FXML
    private AnchorPane player5Card5;
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
    private AnchorPane player6Card3;
    @FXML
    private AnchorPane player6Card4;
    @FXML
    private AnchorPane player6Card5;
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
    private AnchorPane player7Card3;
    @FXML
    private AnchorPane player7Card4;
    @FXML
    private AnchorPane player7Card5;
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
    private Label potSize;
    
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
        
    }
}