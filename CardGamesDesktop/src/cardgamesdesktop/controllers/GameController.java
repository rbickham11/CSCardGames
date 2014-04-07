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
        cardPos.getStyleClass().clear();
        cardPos.getStyleClass().add("card" + card);
    }
    
    public void removeCard(AnchorPane cardPos) {
        if(!cardPos.getStyleClass().isEmpty()) {
            cardPos.getStyleClass().remove(0);
            cardPos.getStyleClass().add("cardDefault");
        }
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
        String id;
        if(previous != null) {
            id = previous.getId();
            ((AnchorPane)previous.lookup("#" + id + "Image")).getStyleClass().remove(1);
            ((Label)previous.lookup("#" + id + "Name")).getStyleClass().remove(2);
            
            if(previous.lookup("#" + id + "ChipCount") != null) {
                ((Label)previous.lookup("#" + id + "ChipCount")).getStyleClass().remove(2);
            }
        }
        
        id = player.getId();
        ((AnchorPane)player.lookup("#" + id + "Image")).getStyleClass().add("playersTurn");
        ((Label)player.lookup("#" + id + "Name")).getStyleClass().add("playersTurn");
        
        if(player.lookup("#" + id + "ChipCount") != null) {
            ((Label)player.lookup("#" + id + "ChipCount")).getStyleClass().add("playersTurn");
        }
    }
}