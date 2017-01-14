package org.leanpoker.player;

import java.util.List;

/**
 * Created by miki on 2017. 01. 14..
 */
public class HoleCards {

    public static boolean isHighPair(List<Card> cards) {
        return HoleCards.pocketPair(cards) && HoleCards.highcards(cards, 11);
    }

    public static boolean pocketPair(List<Card> cards) {
        return cards.get(0).getRank() == cards.get(1).getRank();
    }

    public static boolean aceXhands(List<Card> cards) {
        return cards.get(0).getRank() == 14 || cards.get(1).getRank() == 14;
    }

    public static boolean highcards(List<Card> cards, Integer high) {
        return cards.get(0).getRank() >= high && cards.get(1).getRank() >= high;
    }

    public static boolean facecards(List<Card> cards) {
        return highcards(cards, 10);
    }

    public static boolean connector(List<Card> cards) {
        return Math.abs(cards.get(0).getRank() - cards.get(1).getRank()) == 1;
    }

    public static boolean oneGapper(List<Card> cards) {
        return Math.abs(cards.get(0).getRank() - cards.get(1).getRank()) == 2;
    }

    public static boolean twoGapper(List<Card> cards) {
        return Math.abs(cards.get(0).getRank() - cards.get(1).getRank()) == 3;
    }

    public static boolean sameSuit(List<Card> cards) {
        return cards.get(0).getSuit().equals(cards.get(1).getSuit());
    }
}
