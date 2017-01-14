package org.leanpoker.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 14..
 */
public class Hand {
    private final static String STRAIGHT = "234567891011121314";

    public static boolean hasPoker(List<Card> hand) {
        return sameRanksCheck(hand).contains(4);
    }

    public static boolean hasDrill(List<Card> hand){
        return sameRanksCheck(hand).contains(3);
    }

    public static boolean hasPair(List<Card> hand) {
        return sameRanksCheck(hand).contains(2);
    }

    public static boolean hasFull(List<Card> hand) {
        return hasPair(hand) && hasDrill(hand);
    }

    public static boolean hasFlush(List<Card> hand){
        return sameSuitsCheck(hand).contains(5);
    }

    public static boolean hasStraight(List<Card> hand){
        List<Integer> values = new ArrayList<>();
        String valueString = "";
        for (Card card : hand) {
            values.add(card.getRank());
        }
        Collections.sort(values);
        for (int value : values) {
            valueString += value;
        }
        return STRAIGHT.contains(valueString);
    }


    private static List<Integer> sameRanksCheck(List<Card> hand) {
        List<Integer> sameRanks = new ArrayList<>();
        for (Card card : hand) {
            int timesInHand = 0;
            for (Card compareCard : hand) {
                if (card.getRank() == compareCard.getRank()) {
                    timesInHand += 1;
                }
            }
            sameRanks.add(timesInHand);
        }
        return sameRanks;
    }

    private static List<Integer> sameSuitsCheck(List<Card> hand) {
        List<Integer> sameSuits = new ArrayList<>();
        for (Card card : hand) {
            int timesInHand = 0;
            for (Card compareCard : hand) {
                if (card.getSuit().equals(compareCard.getSuit())) {
                    timesInHand += 1;
                }
            }
            sameSuits.add(timesInHand);
        }
        return sameSuits;
    }
}
