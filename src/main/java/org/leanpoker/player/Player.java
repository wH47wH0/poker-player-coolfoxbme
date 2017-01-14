package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Comparator;
import java.util.List;

public class Player {

    static final String VERSION = "CoolFoxBme";
    public static final double BIG_BET_TRESHOLD = 0.2;
    public static final boolean USE_ALTERNATIVE_MAGIC = true;

    private static GameState gameState;

    public static int betRequest(GameState gameState) {
        Player.gameState = gameState;
        double ourBet = getOurPlayer().getBet();
        double betToCall = gameState.getCurrentBuyIn();

        List<Card> holeCards = getOurPlayer().getHoleCards();


        if (Player.gameState.getCommunityCards() == null || Player.gameState.getCommunityCards().size() == 0) {
            return USE_ALTERNATIVE_MAGIC ? preFlopAlternative(ourBet, betToCall, holeCards) : preFlop(ourBet, betToCall, holeCards);
        } else { // POSTFLOP
            return PostFlop.postFlop(gameState,holeCards);
        }
    }

    private static int preFlopAlternative(double ourBet, double betToCall, List<Card> holeCards) {
        System.out.println("Pot: " + gameState.getPot() + ", CurrentBuyIn: " + gameState.getCurrentBuyIn());
        if (maximumStackLessThanOurs() / bigBlind() < 10) {
            if (HoleCards.facecards(holeCards) ||
                    HoleCards.aceXhands(holeCards) ||
                    HoleCards.pocketPair(holeCards) ||
                    (HoleCards.connector(holeCards) && HoleCards.sameSuit(holeCards) && HoleCards.highcards(holeCards, 7))) {
                return (int)getOurPlayer().getStack();
            } else {
                return 0;
            }
        } else {
            if (gameState.getCurrentBuyIn() <= bigBlind()) {
                return minimumRaise(ourBet, betToCall);
            } else if (gameState.getCurrentBuyIn() < gameState.getPot() / 4) {
                return callValue(ourBet, betToCall);
            } else if (gameState.getCurrentBuyIn() < getOurPlayer().getStack()/5) {
                if (gameState.getBetIndex() < numberOfActiveOrFoldedPlayers()) {
                    if (HoleCards.pocketPair(holeCards) && HoleCards.highcards(holeCards, 11) ||
                            HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 12)) {
                        return (int)getOurPlayer().getStack();
                    } else if (HoleCards.facecards(holeCards) ||
                            HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 10)) {
                        return callValue(ourBet, betToCall);
                    } else {
                        return 0;
                    }
                } else {
                    if (HoleCards.pocketPair(holeCards) && HoleCards.highcards(holeCards, 12) ||
                            HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 13)) {
                        return (int)getOurPlayer().getStack();
                    } else if (HoleCards.facecards(holeCards) ||
                            HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 10)) {
                        return callValue(ourBet, betToCall);
                    } else {
                        return 0;
                    }
                }
            } else {
                if (HoleCards.pocketPair(holeCards) && HoleCards.highcards(holeCards, 12) ||
                        HoleCards.aceXhands(holeCards) && HoleCards.highcards(holeCards, 13)) {
                    return (int)getOurPlayer().getStack();
                } else {
                    return 0;
                }
            }

        }
    }

    private static int numberOfActiveOrFoldedPlayers() {
        return (int)gameState.getPlayers().stream().filter(p -> !"out".equals(p.getStatus())).count();
    }

    private static int preFlop(double ourBet, double betToCall, List<Card> holeCards) {
        if (avgStackToBigBlindRation() < 10) {
            if (shouldAllinIfAvgStackToBigBlindIsSmall(holeCards)) {
                return (int)getOurPlayer().getStack();
            } else {
                return 0;
            }
        } /*else if (gameState.getCurrentBuyIn() < gameState.getPot() && gameState.getBetIndex() > gameState.getPlayers().size()) {
            return callValue(ourBet, betToCall);
        } */
        else if (gameState.getCurrentBuyIn() <= bigBlind()) {
            return minimumRaise(ourBet, betToCall);
        } else if (isAllin() && HoleCards.isHighPair(holeCards)) { // // ALLIN ES MAGAS PAR, STACKET BERAKNI
            return (int) getOurPlayer().getStack();
        } else if (isAllin() && !HoleCards.isHighPair(holeCards)) {
            return 0; // allin, de nincs magas kartyank
        } else if (firstPlayer() && gameState.getCurrentBuyIn() <= bigBlind()) {
            return minimumRaise(ourBet, betToCall);
        } else if (shouldRaise(holeCards)) {
            if (goodStartingCards(holeCards)) {
                System.out.println("callValue: " + callValue(ourBet, betToCall) + " pot: " + gameState.getPot() + " ratio: " + avgStackToBigBlindRation());
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

    private static boolean shouldAllinIfAvgStackToBigBlindIsSmall(List<Card> holeCards) {
        return HoleCards.facecards(holeCards) ||
                HoleCards.aceXhands(holeCards) ||
                HoleCards.pocketPair(holeCards) ||
                (HoleCards.connector(holeCards) && HoleCards.sameSuit(holeCards) && HoleCards.highcards(holeCards, 5));
    }

//    private static int postFlop(List<Card> holeCards) {
//        return PostFlop.postFlop(gameState,holeCards);
//    }

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

    private static double avgStackToBigBlindRation() {
        long count = gameState.getPlayers().stream().filter(p -> !"out".equals(p.getStatus())).count();
        int sum = 0;
        for (Opponent player : gameState.getPlayers()) {
            sum += stackToBigBlindRatio(player);
        }
        return sum / count;
    }

    private static double stackToBigBlindRatio(Opponent player) {
        return bigBlind() / player.getStack();
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

    private static double maximumStackLessThanOurs() {
        Opponent ourPlayer = getOurPlayer();
        return gameState.getPlayers().stream()
                .filter(p -> p.getStack() < ourPlayer.getStack())
                .map(Opponent::getStack)
                .max(Comparator.naturalOrder()).orElse(0.0);
    }
}
