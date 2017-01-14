package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.List;

public class Player {

    static final String VERSION = "CoolFoxBme";
    public static final double BIG_BET_TRESHOLD = 0.2;

    private static GameState gameState;

    public static int betRequest(GameState gameState) {
        Player.gameState = gameState;
        double ourBet = getOurPlayer().getBet();
        double betToCall = gameState.getCurrentBuyIn();

        List<Card> holeCards = getOurPlayer().getHoleCards();


        if (Player.gameState.getCommunityCards() == null || Player.gameState.getCommunityCards().size() == 0) {
            return preFlop(ourBet, betToCall, holeCards);
        } else { // POSTFLOP
            // TODO: postflop
            return preFlop(ourBet, betToCall, holeCards);
        }
    }

    private static int preFlop(double ourBet, double betToCall, List<Card> holeCards) {
        if (isAllin() && HoleCards.isHighPair(holeCards)) { // // ALLIN ES MAGAS PAR, STACKET BERAKNI
            return (int) getOurPlayer().getStack();
        } else if (isAllin() && !HoleCards.isHighPair(holeCards)) {
            return 0; // allin, de nincs magas kartyank
        } else if (firstPlayer() && gameState.getCurrentBuyIn() <= bigBlind()) {
            return minimumRaise(ourBet, betToCall);
        } else if (shouldRaise(holeCards)) {
            if (goodStartingCards(holeCards)) {
                return callValue(ourBet, betToCall) + gameState.getPot();
            } else if (gameState.getCurrentBuyIn() <= bigBlind()) {
                return minimumRaise(ourBet, betToCall);
            } else {
                callValue(ourBet, betToCall);
            }
        } else if (mikiMagic2()) {
            return callValue(ourBet, betToCall);
        }
        return 0;
    }

    private static int postFlop() {
        // TODO: implement me
        return 0;
    }

    private static boolean firstPlayer() {
        return ((gameState.getDealer()+1) % gameState.getPlayers().size()) == gameState.getInAction();
    }

    private static boolean goodStartingCards(List<Card> cards) {
        return (HoleCards.pocketPair(cards) && HoleCards.highcards(cards, 8)) ||
                (HoleCards.aceXhands(cards) && HoleCards.highcards(cards, 10)) ||
                (HoleCards.connector(cards) && HoleCards.sameSuit(cards) && HoleCards.highcards(cards, 9)) ||
                HoleCards.facecards(cards);
    }

    public static int minimumRaise(double ourBet, double betToCall) {
        return callValue(ourBet, betToCall) + gameState.getMinimumRaise();
    }

    public static int callValue(double ourBet, double betToCall) {
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

    private static boolean mikiMagic2() {
        int bigBlind = bigBlind();
        return gameState.getCurrentBuyIn() > bigBlind && !isAllin();
    }

    private static int bigBlind() {
        return gameState.getSmallBlind() * 2;
    }

    private static boolean isAllin() {
        double betNeeded = gameState.getCurrentBuyIn() - getOurPlayer().getBet();
        double betNeededToStackRatio = betNeeded / getOurPlayer().getStack();
        return betNeededToStackRatio > BIG_BET_TRESHOLD;
    }

    public static Opponent getOurPlayer() {
        return gameState.getPlayers().get(gameState.getInAction());
    }

}
