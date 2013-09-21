package cardgames.lib;

import cardgames.lib.games.poker.holdem.HoldemDealer;

public class TempMain {
    public static void main(String[] args) {
        HoldemDealer dealer = new HoldemDealer(5000);
        
        dealer.addPlayer(101, 4, 5000);
        dealer.addPlayer(112, 2, 2500);
        dealer.addPlayer(103, 8, 4765);
        
        dealer.startHand();
    }
}
