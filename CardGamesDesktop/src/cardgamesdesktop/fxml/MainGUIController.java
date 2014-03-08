package cardgamesdesktop.fxml;

import cardgamesdesktop.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Andrew Haeger
 */
public class MainGUIController implements Initializable, ControlledScreen {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    
    @FXML
    ProgressIndicator progress;
    // </editor-fold>
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
    @FXML
    private void goToLoginScreen() {
        System.out.println("show");
        progress.toFront();
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
}