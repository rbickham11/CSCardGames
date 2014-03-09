package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import cardgamesdesktop.utilities.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class ManageAccountGUIController implements Initializable, Screens {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
   
    @FXML
    private Label loggedInHeader;
    @FXML 
    private AnchorPane changePassword;
    @FXML
    private AnchorPane changeDisplayName;
    @FXML 
    private AnchorPane changeEmail;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmNewPassword;
    @FXML
    private Label passwordSuccess;
    
    @FXML
    private Label currentDisplayName;
    @FXML
    private TextField newDisplayName;
    @FXML
    private Label displayNameSuccess;
    
    @FXML
    private Label currentEmail;
    @FXML
    private TextField newEmail;
    @FXML
    private Label emailSuccess;
    // </editor-fold>
       
    private String textInputStyle;
    
    private final DBMgr dbMgr = new DBMgr();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInHeader.setVisible(false);
        passwordSuccess.setVisible(false);
        textInputStyle = oldPassword.getStyle();
    }    
    
    @Override
    public void setScreenController(ScreensController controller) {
        this.controller = controller;
    }
    
    @Override 
    public void setPreviousScreen(String previous) {
        this.previous = previous;
    }
    
    @FXML
    private void showChangePassword() {
        changeDisplayName.setVisible(false);
        changeEmail.setVisible(false);
        
        changePassword.setVisible(true);
        clearPasswordForm();
        passwordSuccess.setVisible(false);
    }
    
    @FXML
    private void showChangeDisplayName() {
        currentDisplayName.setText(UserSessionVars.getDisplayName());
        changePassword.setVisible(false);
        changeEmail.setVisible(false);
        
        changeDisplayName.setVisible(true);
        clearDisplayNameForm();
        displayNameSuccess.setVisible(false);
    }
    
    @FXML
    private void showChangeEmail() {
        currentEmail.setText(UserSessionVars.getEmail());
        changePassword.setVisible(false);
        changeDisplayName.setVisible(false);
        
        changeEmail.setVisible(true);
        clearEmailForm();
        emailSuccess.setVisible(false);
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
    private void changePassword() {    
        if(dbMgr.validateUser(UserSessionVars.getUsername(), oldPassword.getText())) {
            if(newPassword.getText().equals(confirmNewPassword.getText())) {
                dbMgr.setPassword(UserSessionVars.getUserId(), newPassword.getText());
                clearPasswordForm();
                passwordSuccess.setVisible(true);
            }
            else {
                oldPassword.setStyle(textInputStyle);
                confirmNewPassword.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
            }
        }
        else {
            oldPassword.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
        }
    }
    
    @FXML 
    private void clearPasswordForm() {
       oldPassword.setStyle(textInputStyle);
       confirmNewPassword.setStyle(textInputStyle);
       oldPassword.clear();
       newPassword.clear();
       confirmNewPassword.clear();
    }
   
    @FXML
    private void changeDisplayName() {
        if(!newDisplayName.getText().trim().equals("")) {
            dbMgr.setDisplayName(UserSessionVars.getUserId(), newDisplayName.getText());
            dbMgr.setSession(UserSessionVars.getUsername());
            showChangeDisplayName();
            displayNameSuccess.setVisible(true);
        }
        else {
            newDisplayName.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
        }        
    }
   
    @FXML
    private void clearDisplayNameForm() {
        newDisplayName.clear();
        newDisplayName.setStyle(textInputStyle);
    }
   
    @FXML
    private void changeEmail() {
        if(!newEmail.getText().trim().equals("")) {
            dbMgr.setEmail(UserSessionVars.getUserId(), newEmail.getText());
            dbMgr.setSession(UserSessionVars.getUsername());
            showChangeEmail();
            emailSuccess.setVisible(true);
        }
        else {
            newEmail.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
        }  
    }
   
    @FXML
    private void clearEmailForm() {
        newEmail.clear();
        newEmail.setStyle(textInputStyle);
    }
}