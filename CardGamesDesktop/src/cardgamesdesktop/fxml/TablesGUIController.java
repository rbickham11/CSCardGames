package cardgamesdesktop.fxml;

import cardgamesdesktop.ControlledScreen;
import cardgamesdesktop.DesktopCardGameGUI;
import cardgamesdesktop.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import cardgameslib.games.poker.holdem.*;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class TablesGUIController implements Initializable, ControlledScreen {

    ScreensController controller;
    
    private static final int NAME_WIDTH = 259;
    private static final int NAME_HEIGHT = 22;
    private static final int NAME_X_Y = 14;
    private static final int INFO_WIDTH = 199;
    private static final int INFO_HEIGHT = 19;
    private static final int INFO_Y = 16;
    private static final int BLIND_ANTE_X = 310;
    private static final int BUY_IN_X = 505;
    private static final int CAPACITY_WIDTH = 165;
    private static final int CAPACITY_X = 735;
    private static final int BUTTON_WIDTH = 76;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_X = 850;
    private static final int BUTTON_Y = 10;
    
    @FXML
    private ScrollPane holdemTables;
    @FXML
    private ScrollPane fivecarddrawTables;
    @FXML
    private ScrollPane euchreTables;
    @FXML
    private ScrollPane blackjackTables;
    
    @FXML
    private AnchorPane holdemTablesList;
    @FXML
    private AnchorPane fivecarddrawTablesList;
    @FXML
    private AnchorPane euchreTablesList;
    @FXML
    private AnchorPane blackjackTablesList;
    
    private ArrayList<AnchorPane> holdem = new ArrayList<>();
    private ArrayList<AnchorPane> fivecarddraw = new ArrayList<>();
    private ArrayList<AnchorPane> euchre = new ArrayList<>();
    private ArrayList<AnchorPane> blackjack = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addNewHoldemTable("Low Stakes Texas Hold'em", "Blinds: 100 / 200", "Max Buy-in: 20,000", "0 / 9");
    }

    public void addNewHoldemTable(String tableName, String blind, String maxBuyIn, String capacity) {
        AnchorPane contain = createNewTableEntry(tableName, blind, maxBuyIn, capacity);
        contain.setLayoutY((holdem.size() + 1) * 50);
        holdemTablesList.getChildren().add(contain);
        holdem.add(contain);
    }
    
    public void addNewFiveCardDrawTable(String tableName, String ante, String maxBuyIn, String capacity) {
        AnchorPane contain = createNewTableEntry(tableName, ante, maxBuyIn, capacity);
        contain.setLayoutY((fivecarddraw.size() + 1) * 50);
        fivecarddrawTablesList.getChildren().add(contain);
        fivecarddraw.add(contain);
    }
    
    public void addNewEuchreTable(String tableName, String info1, String info2, String capacity) {
        AnchorPane contain = createNewTableEntry(tableName, info1, info2, capacity);
        contain.setLayoutY((euchre.size() + 1) * 50);
        euchreTablesList.getChildren().add(contain);
        euchre.add(contain);
    }
    
    public void addNewBlackjackTable(String tableName, String minBet, String maxBet, String capacity) {
        AnchorPane contain = createNewTableEntry(tableName, minBet, maxBet, capacity);
        contain.setLayoutY((blackjack.size() + 1) * 50);
        blackjackTablesList.getChildren().add(contain);
        blackjack.add(contain);
    }
    
    private AnchorPane createNewTableEntry(String name, String info1, String info2, String capacity) {
        AnchorPane contain = new AnchorPane();
        Label field1 = new Label(name);
        Label field2 = new Label(info1);
        Label field3 = new Label(info2);
        Label field4 = new Label(capacity);
        Button join = new Button("Join Table");
        
        contain.getStylesheets().add("cardgamesdesktop/css/Tables.css");
        
        field1.setLayoutX(NAME_X_Y);
        field1.setLayoutY(NAME_X_Y);
        field1.setMinWidth(NAME_WIDTH);
        field1.setMinHeight(NAME_HEIGHT);
        field1.getStyleClass().add("tableInfo");
        
        field2.setLayoutX(BLIND_ANTE_X);
        field2.setLayoutY(INFO_Y);
        field2.setMinWidth(INFO_WIDTH);
        field2.setMinHeight(INFO_HEIGHT);
        field2.getStyleClass().add("tableInfo");
        
        field3.setLayoutX(BUY_IN_X);
        field3.setLayoutY(INFO_Y);
        field3.prefWidth(INFO_WIDTH);
        field3.prefHeight(INFO_HEIGHT);
        field3.getStyleClass().add("tableInfo");
        
        field4.setLayoutX(CAPACITY_X);
        field4.setLayoutY(INFO_Y);
        field4.setMinWidth(CAPACITY_WIDTH);
        field4.setMinHeight(INFO_HEIGHT);
        field4.getStyleClass().add("tableInfo");
        
        join.setLayoutX(BUTTON_X);
        join.setLayoutY(BUTTON_Y);
        join.setMinHeight(BUTTON_HEIGHT);
        join.setMinWidth(BUTTON_WIDTH);
        join.getStyleClass().add("joinTableBtn");
        
        contain.setMinHeight(50);
        contain.setMinWidth(943);
        
        contain.getChildren().add(field1);
        contain.getChildren().add(field2);
        contain.getChildren().add(field3);
        contain.getChildren().add(field4);
        contain.getChildren().add(join);
        
        return contain;
    }
    
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    @FXML
    private void goToLoginScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen2ID);
    }

    @FXML
    private void showHoldEmTables(ActionEvent event) {
        fivecarddrawTables.setVisible(false);
        euchreTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!holdemTables.isVisible()) {
            holdemTables.setVisible(true);
        } else {
            holdemTables.setVisible(false);
        }
    }

    @FXML
    private void showFiveCardDrawTables(ActionEvent event) {
        holdemTables.setVisible(false);
        euchreTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!fivecarddrawTables.isVisible()) {
            fivecarddrawTables.setVisible(true);
        } else {
            fivecarddrawTables.setVisible(false);
        }
    }

    @FXML
    private void showEuchreTables(ActionEvent event) {
        holdemTables.setVisible(false);
        fivecarddrawTables.setVisible(false);
        blackjackTables.setVisible(false);
        
        if (!euchreTables.isVisible()) {
            euchreTables.setVisible(true);
        } else {
            euchreTables.setVisible(false);
        }
    }

    @FXML
    private void showBlackjackTables(ActionEvent event) {
        holdemTables.setVisible(false);
        fivecarddrawTables.setVisible(false);
        euchreTables.setVisible(false);
        
        if (!blackjackTables.isVisible()) {
            blackjackTables.setVisible(true);
        } else {
            blackjackTables.setVisible(false);
        }
    }
    
    @FXML
    private void goToAccountSettingsScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen4ID);
    }
    
    @FXML
    private void goToHoldEmScreen(ActionEvent event) {
        HoldemDealer dealer = new HoldemDealer(20000, 200);
        controller.setScreen(DesktopCardGameGUI.screen5ID);
        
    }
    
    @FXML
    private void goToFiveCardDrawScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen6ID);
    }
    
    @FXML
    private void goToEuchreScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen7ID);
    }
    
    @FXML
    private void goToBlackjackScreen(ActionEvent event) {
        controller.setScreen(DesktopCardGameGUI.screen8ID);
    }
}