/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import cardgamesdesktop.utilities.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class ManageAccountGUIController implements Initializable, ControlledScreen {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
   
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
    
    @Override public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void showChangePassword(ActionEvent event) {
        changeDisplayName.setVisible(false);
        changeEmail.setVisible(false);
        
        changePassword.setVisible(true);
        clearPasswordForm(new ActionEvent());
        passwordSuccess.setVisible(false);
    }
    
    @FXML
    private void showChangeDisplayName(ActionEvent event) {
        currentDisplayName.setText(UserSessionVars.getDisplayName());
        changePassword.setVisible(false);
        changeEmail.setVisible(false);
        
        changeDisplayName.setVisible(true);
        clearDisplayNameForm(new ActionEvent());
        displayNameSuccess.setVisible(false);
    }
    
    @FXML
    private void showChangeEmail(ActionEvent event) {
        currentEmail.setText(UserSessionVars.getEmail());
        changePassword.setVisible(false);
        changeDisplayName.setVisible(false);
        
        changeEmail.setVisible(true);
        clearEmailForm(new ActionEvent());
        emailSuccess.setVisible(false);
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
    private void changePassword(ActionEvent event) {    
        if(dbMgr.validateUser(UserSessionVars.getUsername(), oldPassword.getText())) {
            if(newPassword.getText().equals(confirmNewPassword.getText())) {
                dbMgr.setPassword(UserSessionVars.getUserId(), newPassword.getText());
                clearPasswordForm(new ActionEvent());
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
    private void clearPasswordForm(ActionEvent event) {
       oldPassword.setStyle(textInputStyle);
       confirmNewPassword.setStyle(textInputStyle);
       oldPassword.clear();
       newPassword.clear();
       confirmNewPassword.clear();
    }
   
    @FXML
    private void changeDisplayName(ActionEvent event) {
        if(!newDisplayName.getText().trim().equals("")) {
            dbMgr.setDisplayName(UserSessionVars.getUserId(), newDisplayName.getText());
            dbMgr.setSession(UserSessionVars.getUsername());
            showChangeDisplayName(new ActionEvent());
            displayNameSuccess.setVisible(true);
        }
        else {
            newDisplayName.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
        }        
    }
   
    @FXML
    private void clearDisplayNameForm(ActionEvent event) {
        newDisplayName.clear();
        newDisplayName.setStyle(textInputStyle);
    }
   
    @FXML
    private void changeEmail(ActionEvent event) {
        if(!newEmail.getText().trim().equals("")) {
            dbMgr.setEmail(UserSessionVars.getUserId(), newEmail.getText());
            dbMgr.setSession(UserSessionVars.getUsername());
            showChangeEmail(new ActionEvent());
            emailSuccess.setVisible(true);
        }
        else {
            newEmail.setStyle(textInputStyle + "\n-fx-border-color: ff0000");
        }  
    }
   
    @FXML
    private void clearEmailForm(ActionEvent event) {
        newEmail.clear();
        newEmail.setStyle(textInputStyle);
    }
}
