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
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class TablesGUIController implements Initializable, ControlledScreen {

    ScreensController controller;

    @FXML
    private ScrollPane holdemTables;
    
    @FXML
    private ScrollPane fivecarddrawTables;
    
    @FXML
    private ScrollPane euchreTables;
    
    @FXML
    private ScrollPane blackjackTables;

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
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen2ID);
    }

    @FXML
    private void showHoldEmTables(ActionEvent event) {
        System.out.println("show holdem");
        
        fivecarddrawTables.setVisible(false);
        euchreTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!holdemTables.isVisible()) {
            holdemTables.setVisible(true);
        } else {
            holdemTables.setVisible(false);
        }
    }

    @FXML
    private void showFiveCardDrawTables(ActionEvent event) {
        System.out.println("show five card draw");
        
        holdemTables.setVisible(false);
        euchreTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!fivecarddrawTables.isVisible()) {
            fivecarddrawTables.setVisible(true);
        } else {
            fivecarddrawTables.setVisible(false);
        }
    }

    @FXML
    private void showEuchreTables(ActionEvent event) {
        System.out.println("show euchre");
        
        holdemTables.setVisible(false);
        fivecarddrawTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!euchreTables.isVisible()) {
            euchreTables.setVisible(true);
        } else {
            euchreTables.setVisible(false);
        }
    }

    @FXML
    private void showBlackjackTables(ActionEvent event) {
        System.out.println("show blackjack");
        
        holdemTables.setVisible(false);
        fivecarddrawTables.setVisible(false);
        euchreTables.setVisible(false);
        
        if (!blackjackTables.isVisible()) {
            blackjackTables.setVisible(true);
        } else {
            blackjackTables.setVisible(false);
        }
    }

    @FXML
    private void goToHoldEmScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen4ID);
    }
    
    @FXML
    private void goToFiveCardDrawScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen5ID);
    }
    
    @FXML
    private void goToEuchreScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen6ID);
    }
    
    @FXML
    private void goToBlackjackScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen7ID);
    }
}
