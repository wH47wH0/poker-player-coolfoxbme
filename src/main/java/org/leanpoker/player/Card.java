package org.leanpoker.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    @JsonProperty("rank") private String rank;
    @JsonProperty("suit") private String suit;

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }
}
