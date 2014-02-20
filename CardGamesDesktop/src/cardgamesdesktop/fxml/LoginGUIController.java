package cardgamesdesktop.fxml;

import cardgamesdesktop.ControlledScreen;
import cardgamesdesktop.DesktopCardGameGUI;
import cardgamesdesktop.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class LoginGUIController implements Initializable, ControlledScreen {

    ScreensController controller;

    @FXML
    private Label registrationStatus;
    @FXML
    private TextField registerFirstName;
    @FXML
    private TextField registerLastName;
    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private PasswordField registerPasswordVerify;

    @FXML
    private Label loginStatus;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    @FXML
    private void registerNewUser(ActionEvent event) {
        
    }
    
    @FXML
    private void clearRegisterForm(ActionEvent event) {
        registerFirstName.clear();
        registerLastName.clear();
        registerUsername.clear();
        registerPassword.clear();
        registerPasswordVerify.clear();
    }
    
    @FXML
    private void login(ActionEvent event) {
        
        
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void clearLoginForm(ActionEvent event) {
        loginUsername.clear();
        loginPassword.clear();
    }

    @FXML
    private void goToHomeScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.homeScreen);
    }
}