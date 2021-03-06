package cardgamesdesktop;

import cardgamesdesktop.controllers.EuchreGUIController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfx.messagebox.MessageBox;

/**
 *
 * @author Andrew Haeger
 */
public class DesktopCardGameGUI extends Application {
    
    public static String homeScreen = "main";
    public static String homeScreenFile = "fxml/MainGUI.fxml";
    public static String loginScreen = "login";
    public static String loginScreenFile = "fxml/LoginGUI.fxml";
    public static String tablesScreen = "tables";
    public static String tablesScreenFile = "fxml/TablesGUI.fxml";
    public static String accountScreen = "accountsettings";
    public static String accountScreenFile = "fxml/ManageAccountGUI.fxml";
    public static String holdemScreen = "holdem";
    public static String holdemScreenFile = "fxml/HoldEmGUI.fxml";
    public static String fivecarddrawScreen = "fivecarddraw";
    public static String fivecarddrawScreenFile = "fxml/FiveCardDrawGUI.fxml";
    public static String euchreScreen = "euchre";
    public static String euchreScreenFile = "fxml/EuchreGUI.fxml";
    public static String blackjackScreen = "blackjack";
    public static String blackjackScreenFile = "fxml/BlackjackGUI.fxml";
    public static String statisticsScreen = "userstatistics";
    public static String statisticsScreenFile = "fxml/UserStatisticsGUI.fxml";
    
    @Override
    public void start(final Stage stage) throws Exception {
        ScreensController container = ScreensController.getInstance();
        container.loadScreen(DesktopCardGameGUI.homeScreen, DesktopCardGameGUI.homeScreenFile);
        container.loadScreen(DesktopCardGameGUI.loginScreen, DesktopCardGameGUI.loginScreenFile);
        container.loadScreen(DesktopCardGameGUI.tablesScreen, DesktopCardGameGUI.tablesScreenFile);
        container.loadScreen(DesktopCardGameGUI.accountScreen, DesktopCardGameGUI.accountScreenFile);
        container.loadScreen(DesktopCardGameGUI.holdemScreen, DesktopCardGameGUI.holdemScreenFile);
        container.loadScreen(DesktopCardGameGUI.fivecarddrawScreen, DesktopCardGameGUI.fivecarddrawScreenFile);
        container.loadScreen(DesktopCardGameGUI.euchreScreen, DesktopCardGameGUI.euchreScreenFile);
        container.loadScreen(DesktopCardGameGUI.blackjackScreen, DesktopCardGameGUI.blackjackScreenFile);
        container.loadScreen(DesktopCardGameGUI.statisticsScreen, DesktopCardGameGUI.statisticsScreenFile);

        container.setScreen(DesktopCardGameGUI.homeScreen);
        
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
        
        Platform.setImplicitExit(false);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                int result = MessageBox.show(stage, "Are you sure you want to quit?", "Are you sure?", MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO);
                
                if(result == MessageBox.YES) {
                    try {
                        ScreensController temp = ((ScreensController)stage.getScene().getRoot().getChildrenUnmodifiable().get(0));
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(temp.getScreen(temp.getChildren().get(0).getId())));
                        Parent loadScreen = (Parent) loader.load();
                        Screens controller = ((Screens) loader.getController());
                        controller.closingApplication();
                    } catch (IOException ex) {
                        // Do nothing at this point
                    }
                    
                    System.exit(0);
                } else {
                    event.consume();
                }
            }
        });
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