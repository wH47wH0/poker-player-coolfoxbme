package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.List;

public class Player {

    static final String VERSION = "CoolFoxBme";
    public static final String IN_ACTION = "in_action";
    public static final double BIG_BET_TRESHOLD = 0.2;

    public static int betRequest(GameState gameState) {
        double ourBet = getOurPlayer(gameState).getBet();
        double betToCall = gameState.getCurrentBuyIn();

        List<Card> holeCards = getOurPlayer(gameState).getHoleCards();

        // ALLIN ES MAGAS PAR, STACKET BERAKNI
        if (isBigBet(gameState) && HoleCards.isHighPair(holeCards)) {
            return (int) getOurPlayer(gameState).getStack();
        } else if (isBigBet(gameState) && !HoleCards.isHighPair(holeCards)) {
            return 0; // allin, de nincs magas kartyank
        } else if (shouldRaise(holeCards)) {
            if (goodStartingCards(holeCards)) {
                return callValue(ourBet, betToCall) + gameState.getPot();
            } else if (gameState.getCurrentBuyIn() <= bigBlind(gameState)) {
                return minimumRaise(gameState, ourBet, betToCall);
            } else {
                callValue(ourBet, betToCall);
            }
        } else if (mikiMagic2(gameState)) {
            return callValue(ourBet, betToCall);
        }
        return 0;
    }

    private static boolean goodStartingCards(List<Card> cards) {
        return (HoleCards.pocketPair(cards) && HoleCards.highcards(cards, 8)) ||
                (HoleCards.aceXhands(cards) && HoleCards.highcards(cards, 10)) ||
                (HoleCards.connector(cards) && HoleCards.sameSuit(cards) && HoleCards.highcards(cards, 9)) ||
                HoleCards.facecards(cards);
    }

    private static int minimumRaise(GameState gameState, double ourBet, double betToCall) {
        return callValue(ourBet, betToCall) + gameState.getMinimumRaise();
    }

    private static int callValue(double ourBet, double betToCall) {
        return (int)(betToCall - ourBet);
    }

    public static void showdown(JsonElement game) {
    }

    // Miki magic logika :)
    private static boolean shouldRaise(List<Card> cards) {
        return HoleCards.pocketPair(cards) ||
                HoleCards.aceXhands(cards) ||
                (HoleCards.connector(cards) && HoleCards.sameSuit(cards)) ||
                (HoleCards.connector(cards) && HoleCards.highcards(cards, 7)) ||
                HoleCards.facecards(cards);
    }

    private static boolean mikiMagic2(GameState gameState) {
        int bigBlind = bigBlind(gameState);
        return gameState.getCurrentBuyIn() > bigBlind && !isBigBet(gameState);
    }

    private static int bigBlind(GameState gameState) {
        return gameState.getSmallBlind() * 2;
    }

    private static boolean isBigBet(GameState gameState) {
        double betNeeded = gameState.getCurrentBuyIn() - getOurPlayer(gameState).getBet();
        double betNeededToStackRatio = betNeeded / getOurPlayer(gameState).getStack();
        return betNeededToStackRatio > BIG_BET_TRESHOLD;
    }

    private static Opponent getOurPlayer(GameState gameState) {
        return gameState.getPlayers().get(gameState.getInAction());
    }

}
