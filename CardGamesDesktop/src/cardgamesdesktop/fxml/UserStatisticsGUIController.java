/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class UserStatisticsGUIController implements Initializable, ControlledScreen {

    ScreensController controller;
    
    @FXML
    private AnchorPane overallStats;
    
    @FXML
    private AnchorPane texasholdemStats;
    
    @FXML
    private AnchorPane euchreStats;
    
    @FXML
    private AnchorPane blackjackStats;
    
    @FXML
    private AnchorPane fivecarddrawStats;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void showOverallStats(ActionEvent event) {
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        overallStats.setVisible(true);
    }
    
    @FXML
    private void showTexasHoldemStats(ActionEvent event) {
        overallStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        texasholdemStats.setVisible(true);
    }
    
    @FXML
    private void showEuchreStats(ActionEvent event) {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        euchreStats.setVisible(true);
    }
    
    @FXML
    private void showBlackjackStats(ActionEvent event) {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        blackjackStats.setVisible(true);
    }
    
    @FXML
    private void showFiveCardDrawStats(ActionEvent event) {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        
        fivecarddrawStats.setVisible(true);
        
    }
    
    @FXML
    private void goToEuchreTableScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.euchreScreen);
    }
}