package cardgamesdesktop.utilities;

import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ryan Bickham
 */
public class PlayerPane {
    private final AnchorPane container;
    private final AnchorPane image;
    private final List<AnchorPane> cards;
    private final Label name;
    private final Label chips;
    private final Label betAmount;
    private final AnchorPane topCard;
    
    public PlayerPane(AnchorPane container, AnchorPane image, List<AnchorPane> cards, Label name, Label chips, Label betAmount) {
        this.container = container;
        this.image = image;
        this.cards = cards;
        this.name = name;
        this.chips = chips;
        this.betAmount = betAmount;
        this.topCard = null;
    }
    
    public PlayerPane(AnchorPane container, AnchorPane image, List<AnchorPane> cards, Label name, AnchorPane topCard) {
        this.container = container;
        this.image = image;
        this.cards = cards;
        this.name = name;
        this.chips = null;
        this.betAmount = null;
        this.topCard = topCard;
    }
    
    public AnchorPane getContainer() { return container; }
    public AnchorPane getImage() { return image; }
    public List<AnchorPane> getCards() { return cards; }
    public Label getName() { return name; }
    public Label getChips() { return chips; }
    public Label getBetAmount() { return betAmount; }
    public AnchorPane getTopCard() { return topCard; }
}
