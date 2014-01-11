/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesktopCardGameGUI.FXML;

import DesktopCardGameGUI.ControlledScreen;
import DesktopCardGameGUI.DesktopCardGameGUI;
import DesktopCardGameGUI.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Andrew Haeger
 */
public class MainGUIController implements Initializable, ControlledScreen {

    ScreensController controller;

    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen2ID);
    }
}
