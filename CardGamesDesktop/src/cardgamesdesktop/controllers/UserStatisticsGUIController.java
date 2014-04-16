package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class UserStatisticsGUIController implements Initializable, Screens {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
    
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
    // </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
    }

    @Override
    public void setPreviousScreen(String previous) {
        this.previous = previous;
    }
    
    @FXML
    private void showOverallStats() {
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        overallStats.setVisible(true);
    }
    
    @FXML
    private void showTexasHoldemStats() {
        overallStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        texasholdemStats.setVisible(true);
    }
    
    @FXML
    private void showEuchreStats() {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        blackjackStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        euchreStats.setVisible(true);
    }
    
    @FXML
    private void showBlackjackStats() {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        fivecarddrawStats.setVisible(false);
        
        blackjackStats.setVisible(true);
    }
    
    @FXML
    private void showFiveCardDrawStats() {
        overallStats.setVisible(false);
        texasholdemStats.setVisible(false);
        euchreStats.setVisible(false);
        blackjackStats.setVisible(false);
        
        fivecarddrawStats.setVisible(true);
        
    }
    
    @FXML
    private void goToEuchreTableScreen() {
        controller.setScreen(DesktopCardGameGUI.euchreScreen);
    }

    @Override
    public void closingApplication() {
    }
}