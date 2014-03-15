package cardgamesdesktop.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Andrew Haeger
 */
public class GameController {
    public void showCard(AnchorPane cardPos, String card) {
        cardPos.getStylesheets().add("cardgamesdesktop/css/Cards.css");
        cardPos.getStyleClass().add("card" + card);
    }
    
    public void removeCard(AnchorPane cardPos) {
        cardPos.getStyleClass().remove(0);
    }
    
    public void deactivatePlayer(AnchorPane player) {
        String id = player.getId();
        player.setOpacity(0.5);
        
        ((Label)player.lookup("#" + id + "Name")).setText("Empty");
        
        if(player.lookup("#" + id + "ChipCount") != null) {
            ((Label)player.lookup("#" + id + "ChipCount")).setText("");
        }
    }
    
    public void activatePlayer(AnchorPane player, String name, String chips) {
        String id = player.getId();
        player.setOpacity(1);
        ((Label)player.lookup("#" + id + "Name")).setText(name);
        
        if(player.lookup("#" + id + "ChipCount") != null) {
            ((Label)player.lookup("#" + id + "ChipCount")).setText(chips);
        }
    }
    
    public void showPlayersTurn(AnchorPane player, AnchorPane previous) {
        player.getStylesheets().add("cardgamesdesktop/css/HoldEm.css");
        player.getStyleClass().add("playersTurn");
        
        if(previous != null) {
            previous.getStyleClass().remove(0);
        }
    }
}