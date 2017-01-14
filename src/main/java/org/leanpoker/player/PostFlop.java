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
        int handStrength = Hand.combinationValue(hand);

//        return checkStrength(handStrength, gameState);

        if (betToCall == gameState.getSmallBlind() * 2) {
            return Player.minimumRaise(ourBet, betToCall);
        } else if (betToCall < ourStack/3) {
            if ((commCardStrength != handStrength) && handStrength >= 2) {
                if (handStrength >= 4) {
                    return (int) ourStack;
                } else {
                    return  Player.callValue(ourBet, betToCall);
                }
            } else return Player.minimumRaise(ourBet, betToCall);
        } else return 0;
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

    private static int MikiPostFlopMagic(List<Card> hand) {
        if (hand.size()<7) {
            if (Hand.combinationValue(hand)<5 && Hand.hasFlushDraw(hand)) {
                // TODO:
            }
        }
        return 0;
    }
}
