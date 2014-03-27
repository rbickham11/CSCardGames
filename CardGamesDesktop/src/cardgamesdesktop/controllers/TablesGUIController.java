package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import cardgameslib.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import cardgameslib.utilities.Game;
import java.rmi.registry.*;
import java.util.*;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class TablesGUIController implements Initializable, Screens {

    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
    
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
    private Label loggedInHeader;
    @FXML
    private ScrollPane holdemTableScroll;
    @FXML
    private ScrollPane fivecarddrawTableScroll;
    @FXML
    private ScrollPane euchreTableScroll;
    @FXML
    private ScrollPane blackjackTableScroll;
    
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
    // </editor-fold>
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        
        loggedInHeader.setVisible(false);

        List<TableDescription> holdemTables = new ArrayList<>();
        List<TableDescription> euchreTables = new ArrayList<>();
        List<TableDescription> blackjackTables = new ArrayList<>();
        
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ITableManager tableManager = (ITableManager)registry.lookup(ITableManager.class.getSimpleName());
            holdemTables = tableManager.getHoldemTables();
            euchreTables = tableManager.getEuchreTables();
            blackjackTables = tableManager.getBlackjackTables();
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
        
        for(TableDescription t : holdemTables) {
            addNewHoldemTable(t.getCol1(), t.getCol2(), t.getCol3(), t.getCol4());
        }
        for(TableDescription t : euchreTables) {
            addNewEuchreTable(t.getCol1(), t.getCol2(), t.getCol3(), t.getCol4());
        }
        for(TableDescription t : blackjackTables) {
            addNewBlackjackTable(t.getCol1(), t.getCol2(), t.getCol3(), t.getCol4());
        }
    }

    @Override
    public void setPreviousScreen(String previous) {
        this.previous = previous;
    }

    public void addNewHoldemTable(String tableName, String blind, String maxBuyIn, String capacity) {
        AnchorPane contain = createNewTableEntry(Game.HOLDEM, tableName, blind, maxBuyIn, capacity);
        contain.setLayoutY((holdem.size()) * 50);
        holdemTablesList.getChildren().add(contain);
        holdemTablesList.setPrefHeight(holdemTablesList.getPrefHeight() + 50);
        holdem.add(contain);
    }
    
    public void addNewFiveCardDrawTable(String tableName, String ante, String maxBuyIn, String capacity) {
        AnchorPane contain = createNewTableEntry(Game.FIVECARDDRAW, tableName, ante, maxBuyIn, capacity);
        contain.setLayoutY((fivecarddraw.size()) * 50);
        fivecarddrawTablesList.getChildren().add(contain);
        fivecarddrawTablesList.setPrefHeight(fivecarddrawTablesList.getPrefHeight() + 50);
        fivecarddraw.add(contain);
    }
    
    public void addNewEuchreTable(String tableName, String info1, String info2, String capacity) {
        AnchorPane contain = createNewTableEntry(Game.EUCHRE, tableName, info1, info2, capacity);
        contain.setLayoutY((euchre.size()) * 50);
        euchreTablesList.getChildren().add(contain);
        euchreTablesList.setPrefHeight(euchreTablesList.getPrefHeight() + 50);
        euchre.add(contain);
    }
    
    public void addNewBlackjackTable(String tableName, String minBet, String maxBet, String capacity) {
        AnchorPane contain = createNewTableEntry(Game.BLACKJACK, tableName, minBet, maxBet, capacity);
        contain.setLayoutY((blackjack.size()) * 50);
        blackjackTablesList.getChildren().add(contain);
        blackjackTablesList.setPrefHeight(blackjackTablesList.getPrefHeight() + 50);
        blackjack.add(contain);
    }
    
    private AnchorPane createNewTableEntry(final Game game, String name, String info1, String info2, String capacity) {
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
        join.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch(game)
                {
                    case HOLDEM:
                        controller.setScreen(DesktopCardGameGUI.holdemScreen);
                        break;
                    case FIVECARDDRAW:
                        controller.setScreen(DesktopCardGameGUI.fivecarddrawScreen);
                        break;
                    case EUCHRE:
                        controller.setScreen(DesktopCardGameGUI.euchreScreen);
                        break;
                    case BLACKJACK:
                        controller.setScreen(DesktopCardGameGUI.blackjackScreen);
                        break;
                    default: break;
                }
            }
        });
        
        contain.setMinHeight(50);
        contain.setMinWidth(943);
        
        contain.getChildren().add(field1);
        contain.getChildren().add(field2);
        contain.getChildren().add(field3);
        contain.getChildren().add(field4);
        contain.getChildren().add(join);
        
        return contain;
    }
    
    @FXML
    private void goToLoginScreen() {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }

    @FXML
    private void showHoldEmTables() {
        fivecarddrawTableScroll.setVisible(false);
        euchreTableScroll.setVisible(false);
        blackjackTableScroll.setVisible(false);
        
        if (!holdemTableScroll.isVisible()) {
            holdemTableScroll.setVisible(true);
        } else {
            holdemTableScroll.setVisible(false);
        }
    }

    @FXML
    private void showFiveCardDrawTables() {
        holdemTableScroll.setVisible(false);
        euchreTableScroll.setVisible(false);
        blackjackTableScroll.setVisible(false);
        
        if (!fivecarddrawTableScroll.isVisible()) {
            fivecarddrawTableScroll.setVisible(true);
        } else {
            fivecarddrawTableScroll.setVisible(false);
        }
    }

    @FXML
    private void showEuchreTables() {
        holdemTableScroll.setVisible(false);
        fivecarddrawTableScroll.setVisible(false);
        blackjackTableScroll.setVisible(false);
        
        if (!euchreTableScroll.isVisible()) {
            euchreTableScroll.setVisible(true);
        } else {
            euchreTableScroll.setVisible(false);
        }
    }

    @FXML
    private void showBlackjackTables() {
        holdemTableScroll.setVisible(false);
        fivecarddrawTableScroll.setVisible(false);
        euchreTableScroll.setVisible(false);
        
        if (!blackjackTableScroll.isVisible()) {
            blackjackTableScroll.setVisible(true);
        } else {
            blackjackTableScroll.setVisible(false);
        }
    }
    
    @FXML
    private void goToAccountSettingsScreen() {
        controller.setScreen(DesktopCardGameGUI.accountScreen);
    }
}