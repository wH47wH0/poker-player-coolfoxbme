package org.leanpoker.player;

/**
 * Created by hamargyuri on 2017. 01. 14..
 */
public abstract class ValueConverter {

    public static int convertFaceValue(String rawValue) {
        switch (rawValue) {
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            default:
                return Integer.parseInt(rawValue);
        }
    }
}
