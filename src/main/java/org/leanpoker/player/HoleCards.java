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
}
