/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesdesktop;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
    public void start(Stage stage) throws Exception {
        ScreensController container = new ScreensController();
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