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
public class EuchreGUIController implements Initializable, ControlledScreen {

    ScreensController controller;
    
    @FXML
    private AnchorPane player1Image;
    
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
}
