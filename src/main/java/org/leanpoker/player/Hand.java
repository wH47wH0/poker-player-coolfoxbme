package org.leanpoker.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 14..
 */
public class Hand {
    private final String STRAIGHT = "234567891011121314";

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
        return false;
    }

    public static boolean hasStraight(List<Card> hand){
        return false;
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
}
