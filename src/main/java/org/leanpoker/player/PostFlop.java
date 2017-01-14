package org.leanpoker.player;

import java.util.ArrayList;
import java.util.List;

import static org.leanpoker.player.Player.*;

/**
 * Created by hamargyuri on 2017. 01. 14..
 */
public class PostFlop {
    public static int postFlop(GameState gameState, List<Card> holeCards) {
        List<Card> hand = getHand(gameState, holeCards);
        double ourBet = getOurPlayer().getBet();
        double betToCall = gameState.getCurrentBuyIn();
        double ourStack = Player.getOurPlayer().getStack();
        int commCardStrength = Hand.combinationValue(gameState.getCommunityCards());
        double handStrength = Hand.combinationValue(hand);

        if (handStrength == 2 && Hand.hasFlushDraw(hand)) {
            handStrength *= 2.5d;
        }

//        return checkStrength(handStrength, gameState);

        if (betToCall == gameState.getSmallBlind() * 2) {
            // raise if no bet
            return Player.minimumRaise(ourBet, betToCall);
        } else if (betToCall < ourStack/3) {
            if ((commCardStrength != handStrength) && handStrength >= 2) {
                // if we have at least a pair in hand
                if (handStrength >= 4) {
                    // if we have at least a drill
                    return (int) ourStack;
                    // all in
                } else return Player.callValue(ourBet, betToCall);
                // or call
            } return 0;
            // we don't have anything within the hole cards, we fold
        } else if ((commCardStrength != handStrength) && handStrength >= 4) {
            // we have at least a drill with our hole cards
            return (int) ourStack;
            // all in
        } else return 0;
        // fold
    }

    private static List<Card> getHand(GameState gameState, List<Card> holeCards) {
        List<Card> hand = new ArrayList<>();
        for (Card card : gameState.getCommunityCards()) {
            hand.add(card);
        }
        for (Card card : holeCards) {
            hand.add(card);
        }
        return hand;
    }

    //not in use
    private static int checkStrength(int handStrength, int commCardStrength, GameState gameState, double ourBet, double betToCall){
        if (handStrength == commCardStrength) {
            return 0;
        } else if (handStrength >= 5) {
            // TODO: all in
            return  0;
        } else if (handStrength >= 3) {
            // small bet
            return Player.minimumRaise(ourBet, betToCall);
        } else if (handStrength >= 2) {
            // call
            return  Player.callValue(ourBet, betToCall);
        } else if (true) {
            // TODO: call if there wasn't a bet
            return 0;
        } else return 0;
    }

//    private static double flushDrawLogic(List<Card> hand, int ourStack, int ourBet, int betToCall) {
//        if (Hand.combinationValue(hand)<5 && Hand.hasFlushDraw(hand)) {
//            if (hand.size()<7) {
//                // turn
//                return 1.15d;
//            } else if (hand.size()<6) {
//                // flop
//                return 1.5d;
//            }
//        }
//        return 1;
//    }
}
