package cardgamesdesktop.fxml;

import cardgamesdesktop.ControlledScreen;
import cardgamesdesktop.DesktopCardGameGUI;
import cardgamesdesktop.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class EuchreGUIController implements Initializable, ControlledScreen {

    // Seat Configuration
    //      2
    //  1       3
    //      0
    
    ScreensController controller;
    
    @FXML
    private AnchorPane player0Image;
    @FXML
    private AnchorPane player0Card1;
    @FXML
    private AnchorPane player0Card2;
    @FXML
    private AnchorPane player0Card3;
    @FXML
    private AnchorPane player0Card4;
    @FXML
    private AnchorPane player0Card5;
    @FXML
    private Label player0Name;
    @FXML
    private AnchorPane player0CardUp;
    @FXML
    private AnchorPane player0CardPlayed;
    
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
    private TextArea gameInfo;
    @FXML
    private CheckBox goAlone;
    @FXML
    private RadioButton spades;
    @FXML
    private RadioButton hearts;
    @FXML
    private RadioButton diamonds;
    @FXML
    private RadioButton clubs;
    @FXML
    private Label handInformation;
    @FXML
    private TextArea chatBox;
    @FXML
    private TextField chatMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void goToTablesScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen3ID);
    }
    
    @FXML
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen2ID);
    }
    
    @FXML
    private void showUserStatisticsScreen() {
        controller.setScreen(DesktopCardGameGUI.screen9ID);
    }
    
    @FXML
    private void pass(ActionEvent event) {
        
    }
    
    @FXML
    private void call(ActionEvent event) {
        
    }
    
    @FXML
    private void sendMessage(ActionEvent event) {
        
    }
}