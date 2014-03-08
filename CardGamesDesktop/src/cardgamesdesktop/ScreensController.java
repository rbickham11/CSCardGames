/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesdesktop;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andrew Haeger
 */
public class ScreensController extends StackPane {

    private HashMap<String, String> screens = new HashMap<>();

    public ScreensController() {
        super();
    }

    public void addScreen(String name, String screen) {
        screens.put(name, screen);
    }

    public String getScreen(String name) {
        return screens.get(name);
    }

    public void loadScreen(String name, String resource) {
        addScreen(name, resource);
    }

    public void setScreen(final String name) {
        if (screens.get(name) != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(screens.get(name)));
                Parent loadScreen = (Parent) loader.load();
                ControlledScreen controller = ((ControlledScreen) loader.getController());
                controller.setScreenParent(this);
                if (!getChildren().isEmpty()) {
                    getChildren().remove(0);
                    getChildren().add(0, loadScreen);
                } else {
                    getChildren().add(loadScreen);
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                System.out.println(e.getMessage());
            }
        }
    }
}

// Change HaseTable to String, String
// Only load name, values into hash table.
// On setScreen remove and get child, then load FXML and other items.
// Change name of files and maybe method names to hide possible connection
//  to website used to get basic idea of screen handler.

// Add "Are you sure?" prompt on closing of the form.  Figure out how to cancel
// the close if use does not want to close.
// https://blogs.oracle.com/javajungle/entry/dialogfx_a_new_approach_to
// http://sourceforge.jp/projects/jfxmessagebox/wiki/JfxMessageBox