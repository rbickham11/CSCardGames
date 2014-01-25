/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesdesktop;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Andrew Haeger
 */
public class DesktopCardGameGUI extends Application {
    
    public static String screen1ID = "main";
    public static String screen1File = "fxml/MainGUI.fxml";
    public static String screen2ID = "login";
    public static String screen2File = "fxml/LoginGUI.fxml";
    public static String screen3ID = "tables";
    public static String screen3File = "fxml/TablesGUI.fxml";
    public static String screen4ID = "holdem";
    public static String screen4File = "fxml/HoldEmGUI.fxml";
    public static String screen5ID = "fivecarddraw";
    public static String screen5File = "fxml/FiveCardDrawGUI.fxml";
    public static String screen6ID = "euchre";
    public static String screen6File = "fxml/EuchreGUI.fxml";
    public static String screen7ID = "blackjack";
    public static String screen7File = "fxml/BlackjackGUI.fxml";
    
    @Override
    public void start(Stage stage) throws Exception {
        ScreensController container = new ScreensController();
        container.loadScreen(DesktopCardGameGUI.screen1ID, DesktopCardGameGUI.screen1File);
        container.loadScreen(DesktopCardGameGUI.screen2ID, DesktopCardGameGUI.screen2File);
        container.loadScreen(DesktopCardGameGUI.screen3ID, DesktopCardGameGUI.screen3File);
        container.loadScreen(DesktopCardGameGUI.screen4ID, DesktopCardGameGUI.screen4File);
        container.loadScreen(DesktopCardGameGUI.screen5ID, DesktopCardGameGUI.screen5File);
        container.loadScreen(DesktopCardGameGUI.screen6ID, DesktopCardGameGUI.screen6File);
        container.loadScreen(DesktopCardGameGUI.screen7ID, DesktopCardGameGUI.screen7File);

        container.setScreen(DesktopCardGameGUI.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(container);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setWidth(1061);
        stage.setHeight(680);
        stage.getIcons().add(new Image(DesktopCardGameGUI.class.getResourceAsStream("images/CardGameIcon.png")));
        stage.setTitle("Legion Games");
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
