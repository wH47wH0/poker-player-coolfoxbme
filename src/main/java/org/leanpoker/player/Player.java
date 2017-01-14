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

        if (HoleCards.pocketPair(holeCards) || (HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 10))) {

        return (int) (betToCall - ourBet + gameState.getMinimumRaise());
        }
        return 0;
    }

    public static void showdown(JsonElement game) {
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
