/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardgamesdesktop.fxml;

import cardgamesdesktop.ControlledScreen;
import cardgamesdesktop.DesktopCardGameGUI;
import cardgamesdesktop.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class ManageAccountGUIController implements Initializable, ControlledScreen {

    ScreensController controller;
    
    @FXML 
    private AnchorPane changePassword;
    
    @FXML
    private AnchorPane changeDisplayName;
    
    @FXML 
    private AnchorPane changeEmail;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void showChangePassword(ActionEvent event) {
        changeDisplayName.setVisible(false);
        changeEmail.setVisible(false);
        
        changePassword.setVisible(true);
    }
    
    @FXML
    private void showChangeDisplayName(ActionEvent event) {
        changePassword.setVisible(false);
        changeEmail.setVisible(false);
        
        changeDisplayName.setVisible(true);
    }
    
    @FXML
    private void showChangeEmail(ActionEvent event) {
        changePassword.setVisible(false);
        changeDisplayName.setVisible(false);
        
        changeEmail.setVisible(true);
    }
    
    @FXML
    private void goToTablesScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
}
