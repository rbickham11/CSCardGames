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

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class LoginGUIController implements Initializable, ControlledScreen {

    ScreensController controller;

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
    private void goToHomeScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen1ID);
    }
    
    @FXML
    private void goToTablesScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen3ID);
    }
}