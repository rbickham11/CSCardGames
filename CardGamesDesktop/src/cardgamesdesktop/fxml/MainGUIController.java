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
 *
 * @author Andrew Haeger
 */
public class MainGUIController implements Initializable, ControlledScreen {

    ScreensController controller;

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