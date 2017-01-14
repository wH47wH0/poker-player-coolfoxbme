package org.leanpoker.player;

/**
 * Created by miki on 2017. 01. 14..
 */
public class HoleCards {


    private static boolean pocketPair(Integer val1, Integer val2) {
        return val1.equals(val2);
    }

    private static boolean aceXhads(Integer val1, Integer val2) {
        return val1 == 14 || val2 == 14;
    }

    private static boolean highcards(Integer val1, Integer val2, Integer high) {
        high = 8;
        return val1 >= high && val2 >= high;
    }

    private static boolean connector(Integer val1, Integer val2) {
        return Math.abs(val1 - val2) == 1;
    }

    private static boolean oneGapper(Integer val1, Integer val2) {
        return Math.abs(val1 - val2) == 2;
    }

    private static boolean twoGapper(Integer val1, Integer val2) {
        return Math.abs(val1 - val2) == 3;
    }

    private static boolean sameSuit(String suit1, String suit2) {
        return suit1.equals(suit2);
    }
}
