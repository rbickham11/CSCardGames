package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;

/**
 *
 * @author Andrew Haeger
 */
public class MainGUIController implements Initializable, Screens {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
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
    private void goToLoginScreen() {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }

    @Override
    public void closingApplication() {
    }
}