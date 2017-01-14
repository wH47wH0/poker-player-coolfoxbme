package org.leanpoker.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 14..
 */
public class Hand {
    private final static String STRAIGHT = "234567891011121314";

    public static int combinationValue(List<Card> hand) {
        if (hasFull(hand)) {
            return 7;
        } else if (hasFlush(hand)) {
            return 6;
        } else if (hasStraight(hand)) {
            return 5;
        } else if (hasDrill(hand)) {
            return 4;
        } else if (hasTwoPairs(hand)) {
            return 3;
        } else if (hasPair(hand)) {
            return 2;
        } else return 1;
    }

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

    public static boolean hasFlush(List<Card> hand) {
        return sameSuitsCheck(hand).contains(5);
    }

    public static boolean hasFlushDraw(List<Card> hand) {
        // only use when we have 3 or 4 community cards
        return sameSuitsCheck(hand).contains(4);
    }

    public static boolean hasStraight(List<Card> hand) {
        return STRAIGHT.contains(sortRanks(hand));
    }

    public static boolean hasTwoPairs(List<Card> hand) {
        int pairs = 0;
        for (int ranks : sameRanksCheck(hand)) {
            if (ranks==2) {
                pairs += 1;
            }
        }
        return pairs > 1;
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

    private static String sortRanks(List<Card> hand) {
        List<Integer> ranks = allRanks(hand);
        String sortedRanks = "";
        Collections.sort(ranks);
        for (int rank : ranks) {
            sortedRanks += rank;
        }
        return sortedRanks;
    }

    private static List<Integer> allRanks(List<Card> hand) {
        List<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            ranks.add(card.getRank());
        }
        return ranks;
    }
}
