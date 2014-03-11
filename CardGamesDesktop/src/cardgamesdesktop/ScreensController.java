package cardgamesdesktop;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andrew Haeger
 */
public class ScreensController extends StackPane {

    private final HashMap<String, String> screens = new HashMap<>();
    private static ScreensController instance = null;

    protected ScreensController() {
        super();
    }

    public static ScreensController getInstance() {
        if(instance == null) {
            instance = new ScreensController();
        }
        
        return instance;
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
                Screens controller = ((Screens) loader.getController());
                if (!getChildren().isEmpty()) {
                    controller.setPreviousScreen(getChildren().get(0).getId());
                    getChildren().remove(0);
                    getChildren().add(0, loadScreen);
                } else {
                    controller.setPreviousScreen("");
                    getChildren().add(loadScreen);
                }
            } catch (IOException e) {
                e.printStackTrace(System.out);
                System.out.println(e.getMessage());
            }
        }
    }
}