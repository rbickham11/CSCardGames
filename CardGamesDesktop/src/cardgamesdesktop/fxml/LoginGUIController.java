package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.SQLException;

import cardgamesdesktop.utilities.DBMgr;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class LoginGUIController implements Initializable, ControlledScreen {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;

    @FXML
    private Label registrationStatus;
    @FXML
    private TextField registerEmail;
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
    
    @FXML
    ProgressIndicator progress;
    // </editor-fold>

    private final DBMgr dbMgr = new DBMgr();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
        System.out.println("Testing");
        progress.setVisible(false);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    @FXML
    private void registerNewUser(ActionEvent event) {
        String user = registerUsername.getText();
        String password = registerPassword.getText();
        
        registrationStatus.setVisible(true);
        if(user.trim().equals("") || password.trim().equals("")) {
            registrationStatus.setText("Please enter a Username, Password, and Email");
            return;
        }
        if(!password.equals(registerPasswordVerify.getText())) {
            registrationStatus.setText("Passwords do not match");
            return;
        }
        
        try {
            if(!dbMgr.userExists(user)) {
                dbMgr.addUser(user, registerPassword.getText(), registerEmail.getText());
                clearRegisterForm(new ActionEvent());
                registrationStatus.setText("Registration Successful!");
            }
            else {
                registrationStatus.setText("User " + user + " already exists!");
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace(System.out);
            registrationStatus.setText("An error occurred connecting to the database. Please try again.");
        }
    }
    
    @FXML
    private void clearRegisterForm(ActionEvent event) {
        registerEmail.clear();
        registerUsername.clear();
        registerPassword.clear();
        registerPasswordVerify.clear();
    }
    
    @FXML
    private void login(ActionEvent event) {
        if(loginUsername.getText().equals("test")) {
            controller.setScreen(DesktopCardGameGUI.tablesScreen);
        }
        else if(dbMgr.validateUser(loginUsername.getText(), loginPassword.getText())) {
            loginStatus.setVisible(false);
            registrationStatus.setVisible(false);
            controller.setScreen(DesktopCardGameGUI.tablesScreen);
        }
        else {
            loginStatus.setVisible(true);
            loginStatus.setText("Your username or password is incorrect. Try again");
        }
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