package org.leanpoker.player;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Opponent {
    @JsonProperty("id") private String id;
    @JsonProperty("name") private String name;
    @JsonProperty("status") private String status;
    @JsonProperty("version") private String version;
    @JsonProperty("stack") private double stack;
    @JsonProperty("bet") private double bet;
    @JsonProperty("hole_cards") private List<Card> holeCards;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public double getStack() {
        return stack;
    }

    public double getBet() {
        return bet;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }
}
