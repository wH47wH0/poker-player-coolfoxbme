package org.leanpoker.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    @JsonProperty("rank") private String rank;
    @JsonProperty("suit") private String suit;

    public int getRank() {
        switch (rank) {
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            default:
                return Integer.parseInt(rank);
        }
    }

    public String getSuit() {
        return suit;
    }
}
