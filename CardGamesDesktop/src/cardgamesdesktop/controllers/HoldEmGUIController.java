package cardgamesdesktop.controllers;

import cardgamesdesktop.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.StringProperty;

import cardgamesdesktop.utilities.*;
import cardgameslib.games.poker.betting.PokerAction;
import cardgameslib.games.poker.holdem.HoldemDealer;
import cardgameslib.utilities.*;
/**
 * FXML Controller class
 *
 * @author Andrew Haeger
 */
public class HoldEmGUIController extends GameController implements Initializable, Screens {

    //  Seat Configuration
    //      9       1
    //  8               2
    //  7               3
    //      6   5   4
    
    // <editor-fold defaultstate="collapsed" desc="GUI Components">
    ScreensController controller;
    String previous;
    
    @FXML
    private Label loggedInHeader;
    
    @FXML
    private AnchorPane player1;
    @FXML
    private AnchorPane player1Image;
    @FXML
    private AnchorPane player1Card1;
    @FXML
    private AnchorPane player1Card2;
    @FXML
    private Label player1Name;
    @FXML
    private Label player1ChipCount;
    @FXML
    private AnchorPane player1DealerButton;
    @FXML
    private Label player1BetAmount;
    @FXML
    private AnchorPane player1ChipBetImage;
    
    @FXML
    private AnchorPane player2;
    @FXML
    private AnchorPane player2Image;
    @FXML
    private AnchorPane player2Card1;
    @FXML
    private AnchorPane player2Card2;
    @FXML
    private Label player2Name;
    @FXML
    private Label player2ChipCount;
    @FXML
    private AnchorPane player2DealerButton;
    @FXML
    private Label player2BetAmount;
    @FXML
    private AnchorPane player2ChipBetImage;
    
    @FXML
    private AnchorPane player3;
    @FXML
    private AnchorPane player3Image;
    @FXML
    private AnchorPane player3Card1;
    @FXML
    private AnchorPane player3Card2;
    @FXML
    private Label player3Name;
    @FXML
    private Label player3ChipCount;
    @FXML
    private AnchorPane player3DealerButton;
    @FXML
    private Label player3BetAmount;
    @FXML
    private AnchorPane player3ChipBetImage;
    
    @FXML
    private AnchorPane player4;
    @FXML
    private AnchorPane player4Image;
    @FXML
    private AnchorPane player4Card1;
    @FXML
    private AnchorPane player4Card2;
    @FXML
    private Label player4Name;
    @FXML
    private Label player4ChipCount;
    @FXML
    private AnchorPane player4DealerButton;
    @FXML
    private Label player4BetAmount;
    @FXML
    private AnchorPane player4ChipBetImage;
    
    @FXML
    private AnchorPane player5;
    @FXML
    private AnchorPane player5Image;
    @FXML
    private AnchorPane player5Card1;
    @FXML
    private AnchorPane player5Card2;
    @FXML
    private Label player5Name;
    @FXML
    private Label player5ChipCount;
    @FXML
    private AnchorPane player5DealerButton;
    @FXML
    private Label player5BetAmount;
    @FXML
    private AnchorPane player5ChipBetImage;
    
    @FXML
    private AnchorPane player6;
    @FXML
    private AnchorPane player6Image;
    @FXML
    private AnchorPane player6Card1;
    @FXML
    private AnchorPane player6Card2;
    @FXML
    private Label player6Name;
    @FXML
    private Label player6ChipCount;
    @FXML
    private AnchorPane player6DealerButton;
    @FXML
    private Label player6BetAmount;
    @FXML
    private AnchorPane player6ChipBetImage;
    
    @FXML
    private AnchorPane player7;
    @FXML
    private AnchorPane player7Image;
    @FXML
    private AnchorPane player7Card1;
    @FXML
    private AnchorPane player7Card2;
    @FXML
    private Label player7Name;
    @FXML
    private Label player7ChipCount;
    @FXML
    private AnchorPane player7DealerButton;
    @FXML
    private Label player7BetAmount;
    @FXML
    private AnchorPane player7ChipBetImage;
    
    @FXML
    private AnchorPane player8;
    @FXML
    private AnchorPane player8Image;
    @FXML
    private AnchorPane player8Card1;
    @FXML
    private AnchorPane player8Card2;
    @FXML
    private Label player8Name;
    @FXML
    private Label player8ChipCount;
    @FXML
    private AnchorPane player8DealerButton;
    @FXML
    private Label player8BetAmount;
    @FXML
    private AnchorPane player8ChipBetImage;
    
    @FXML
    private AnchorPane player9;
    @FXML
    private AnchorPane player9Image;
    @FXML
    private AnchorPane player9Card1;
    @FXML
    private AnchorPane player9Card2;
    @FXML
    private Label player9Name;
    @FXML
    private Label player9ChipCount;
    @FXML
    private AnchorPane player9DealerButton;
    @FXML
    private Label player9BetAmount;
    @FXML
    private AnchorPane player9ChipBetImage;
    
    @FXML
    private Label potSize;
    @FXML
    private AnchorPane houseCard1;
    @FXML
    private AnchorPane houseCard2;
    @FXML
    private AnchorPane houseCard3;
    @FXML
    private AnchorPane houseCard4;
    @FXML
    private AnchorPane houseCard5;
    
    @FXML
    private TextArea gameInfo;
    @FXML
    private Label handInformation;
    @FXML
    private TextArea chatBox;
    @FXML
    private TextField chatMessage;
    @FXML
    private Slider betAmountSlider;
    @FXML
    private TextField betAmount;
    
    @FXML
    private Button betButton;
    @FXML
    private Button callButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button foldButton;
    @FXML
    private Button raiseButton;
    
    // </editor-fold>
    
    private ChatClient chatClient;
    private HoldemDealer dealer;
    private final int bigBlind = 200;
    private PlayerPane[] playerPanes;
    private AnchorPane[] boardCardPanes;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ScreensController.getInstance();
        playerPanes = new PlayerPane[] {
            new PlayerPane(player1, player1Image, Arrays.asList(player1Card1, player1Card2), player1Name, player1ChipCount, player1BetAmount),
            new PlayerPane(player2, player2Image, Arrays.asList(player2Card1, player2Card2), player2Name, player2ChipCount, player2BetAmount),
            new PlayerPane(player3, player3Image, Arrays.asList(player3Card1, player3Card2), player3Name, player3ChipCount, player3BetAmount),
            new PlayerPane(player4, player4Image, Arrays.asList(player4Card1, player4Card2), player4Name, player4ChipCount, player4BetAmount),
            new PlayerPane(player5, player5Image, Arrays.asList(player5Card1, player5Card2), player5Name, player5ChipCount, player5BetAmount),
            new PlayerPane(player6, player6Image, Arrays.asList(player6Card1, player6Card2), player6Name, player6ChipCount, player6BetAmount),
            new PlayerPane(player7, player7Image, Arrays.asList(player7Card1, player7Card2), player7Name, player7ChipCount, player7BetAmount),
            new PlayerPane(player8, player8Image, Arrays.asList(player8Card1, player8Card2), player8Name, player8ChipCount, player8BetAmount),     
            new PlayerPane(player9, player9Image, Arrays.asList(player9Card1, player9Card2), player9Name, player9ChipCount, player9BetAmount)
        };
        boardCardPanes = new AnchorPane[] { houseCard1, houseCard2, houseCard3, houseCard4, houseCard5 };
        StringProperty chatBoxText = chatBox.textProperty();

        try{
            chatClient = new ChatClient(chatBoxText);
        }
        catch(RemoteException ex) {
            ex.printStackTrace(System.out);
        }
        
        loggedInHeader.setText(UserSessionVars.getUsername());
        betAmountSlider.setMajorTickUnit(bigBlind);
        betAmountSlider.setMinorTickCount(0);
        betAmountSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                betAmount.setText(Integer.toString(newValue.intValue()));
            }
        });
        
        dealer = new HoldemDealer(20000, bigBlind);
        dealer.addPlayer(111, "Ryan", 5, 20000);
        dealer.addPlayer(222, "Andrew", 1, 18500);
        dealer.addPlayer(333, "Nick", 7, 15000);
        dealer.addPlayer(444, "RyanG", 3, 10000);
        
        startNewHand();
    }
    
    public void startNewHand() {
        List<BettingPlayer> players = dealer.getPlayers();
        for(BettingPlayer p : players) {
            activatePlayer(playerPanes[p.getSeatNumber() - 1].getContainer(), p.getUsername(), Integer.toString(p.getChips()));
        }
        for(AnchorPane card : boardCardPanes) {
            removeCard(card);
        }
        for(PlayerPane p : playerPanes) {
            p.getBetAmount().setText("");
            removeCard(p.getCards().get(0));
            removeCard(p.getCards().get(1));
        }
        dealer.startHand();
        dealHands();
        updateChipValues();
        players = dealer.getActivePlayers();
        playerPanes[players.get(players.size() - 1).getSeatNumber() - 1].getBetAmount().setText(Integer.toString(bigBlind));
        playerPanes[players.get(players.size() - 2).getSeatNumber() - 1].getBetAmount().setText(Integer.toString(bigBlind / 2));
        showPlayersTurn(playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getContainer(), null);
        
        betButton.setDisable(true);
        checkButton.setDisable(true);
        callButton.setDisable(false);
        raiseButton.setDisable(false);
        betAmountSlider.setMin(dealer.getCurrentBet());
        betAmountSlider.setMax(dealer.getCurrentPlayer().getChips());
    }
    
    public void dealHands() {
        List<AnchorPane> cards;
        for(BettingPlayer p : dealer.getActivePlayers()) {
            cards = playerPanes[p.getSeatNumber() - 1].getCards();
            for(int i = 0; i < cards.size(); i++) {
                showCard(cards.get(i), Deck.cardToString(p.getHand().get(i)));
            }
        }
    }
    
    @Override
    public void setPreviousScreen(String previous) {
        this.previous = previous;
    }
    
    @FXML
    private void goToTablesScreen() {
        controller.setScreen(DesktopCardGameGUI.tablesScreen);
    }
    
    @FXML
    private void goToLoginScreen() {
        controller.setScreen(DesktopCardGameGUI.loginScreen);
    }
    
    @FXML
    private void bet() {
        handleAction(PokerAction.BET);
    }
    
    @FXML
    private void call() {
        handleAction(PokerAction.CALL);
    }
    
    @FXML
    private void raise() {
        handleAction(PokerAction.RAISE);
    }
    
    @FXML
    private void check() {
        handleAction(PokerAction.CHECK);
    }
    
    @FXML
    private void fold() {
        handleAction(PokerAction.FOLD);
    }
    
    private void handleAction(PokerAction action) {
        int amount = 0;
        int currentSeat = dealer.getCurrentPlayer().getSeatNumber();
        
        switch(action) {
            case BET:
            case RAISE:
                try {
                    amount = Integer.parseInt(betAmount.getText());
                }
                catch(NumberFormatException ex) {
                    ex.printStackTrace(System.out);
                    return;
                }
                break;
            case FOLD:
                List<AnchorPane> cards = playerPanes[currentSeat - 1].getCards();
                for(int i = 0; i < cards.size(); i++) {
                    removeCard(cards.get(i));
                }
        }
        
        BettingPlayer p = dealer.getCurrentPlayer();
        try {
            dealer.takeAction(action, amount);
        }
        catch(IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        
        if(action != PokerAction.FOLD && action != PokerAction.CHECK) {
            updateChipValues();
        }      
        updateGameSummary(p, action);
        if(action == PokerAction.BET) {
            checkButton.setDisable(true);
            betButton.setDisable(true);
            callButton.setDisable(false);
            raiseButton.setDisable(false);
        }
        if(dealer.bettingComplete()) {
            completeRound();
        }
        int nextSeat = dealer.getCurrentPlayer().getSeatNumber();
        showPlayersTurn(playerPanes[nextSeat - 1].getContainer(), playerPanes[currentSeat - 1].getContainer());
        betAmountSlider.setMin(dealer.getCurrentBet());
        betAmountSlider.setMax(dealer.getCurrentPlayer().getChips());
        betAmount.setText("");
    }
    
    private void updateGameSummary(BettingPlayer player, PokerAction action) {
        switch(action) {
            case BET:
                gameInfo.appendText(String.format("%s bets %d\n", player.getUsername(), player.getCurrentBet()));
                return;
            case CALL:
                gameInfo.appendText(String.format("%s calls %d\n", player.getUsername(), player.getCurrentBet()));
                return;
            case CHECK:
                gameInfo.appendText(String.format("%s checks\n", player.getUsername()));
                return;
            case FOLD:
                gameInfo.appendText(String.format("%s folds\n", player.getUsername()));
                return;
            case RAISE:
                gameInfo.appendText(String.format("%s raises to %d\n", player.getUsername(), player.getCurrentBet()));
        }
    }
    
    private void completeRound() {
        for(PlayerPane p : playerPanes) {
            p.getBetAmount().setText("");
        }
        
        if(dealer.isWinner()) {
            gameInfo.appendText(String.format("The winner is %s\n", dealer.getCurrentPlayer().getUsername()));
            awardPot();
        }
        else {
            List<Integer> board = dealer.getBoard();
            if(board.isEmpty()) {
                dealer.dealFlopToBoard();
                gameInfo.appendText("The flop is: ");
                for(int i = 0; i < 3; i++) {
                    showCard(boardCardPanes[i], Deck.cardToString(board.get(i)));
                    gameInfo.appendText(Deck.cardToString(board.get(i)) + " ");
                }
                gameInfo.appendText("\n");
            }
            else if(board.size() == 5) {
                gameInfo.appendText(dealer.findWinner());
                awardPot();
                return;
            }
            else {
                dealer.dealCardToBoard();
                int cardIndex = 3;
                String street = "turn";
                if(dealer.getBoard().size() == 5) {
                    street = "river";
                    cardIndex = 4;
                }
                gameInfo.appendText(String.format("The %s is %s\n", street, Deck.cardToString(board.get(cardIndex))));
                showCard(boardCardPanes[cardIndex], Deck.cardToString(board.get(cardIndex)));
            }
            callButton.setDisable(true);
            raiseButton.setDisable(true);
            betButton.setDisable(false);
            checkButton.setDisable(false);
        }
    }
    
    private void awardPot() {
        updateChipValues();
        //Implement 5 second delay, and show winning hand
        playerPanes[dealer.getCurrentPlayer().getSeatNumber() - 1].getBetAmount().setText(Integer.toString(dealer.getPotSize()));       
        startNewHand();
    }
    
    private void updateChipValues() {
        BettingPlayer p = dealer.getActivePlayers().get(dealer.getActivePlayers().size() - 1);
        playerPanes[p.getSeatNumber() - 1].getBetAmount().setText(Integer.toString(p.getCurrentBet()));
        playerPanes[p.getSeatNumber() - 1].getChips().setText(Integer.toString(p.getChips()));
        potSize.setText(String.format("Pot Size: $%d", dealer.getPotSize()));
    }
    
    @FXML
    private void sendMessage() {
        try {
            String message = UserSessionVars.getDisplayName() + ": " + chatMessage.getText();
            chatClient.sendChatMessage(message);
        }
        catch(NullPointerException ex) {
            chatBox.setText("The chat server is currently unavailable.");
        }
        chatMessage.setText("");
    }
}