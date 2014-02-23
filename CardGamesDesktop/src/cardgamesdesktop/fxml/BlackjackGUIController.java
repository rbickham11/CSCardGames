package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class BlackjackGUIController implements Initializable, ControlledScreen {

    // Seat Configuration
    //  1   2   3   4   5
    
    ScreensController controller;
    
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
    private Label betAmount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void insurance(ActionEvent event) {
        
    }
    
    @FXML
    private void split(ActionEvent event) {
        
    }
    
    @FXML
    private void doubleDown(ActionEvent event) {
        
    }
    
    @FXML
    private void hit(ActionEvent event) {
        
    }
    
    @FXML
    private void stand(ActionEvent event) {
        
    }
    
    @FXML
    private void surrender(ActionEvent event) {
        
    }
    
    @FXML
    private void sendMessage(ActionEvent event) {
        
    }
}