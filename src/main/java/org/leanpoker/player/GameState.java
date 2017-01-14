package org.leanpoker.player;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GameState {
    @JsonProperty("tournament_id") private String tournamentId;

    /**
     * Id of the current sit'n'go game. You can use this to link a
     sequence of game states together for logging purposes, or to
     make sure that the same strategy is played for an entire game
     */
    @JsonProperty("game_id") private String gameId;

    /**
     * Index of the current round within a sit'n'go
     */
    @JsonProperty("round") private int round;

    /**
     * Index of the betting opportunity within a round
     */
    @JsonProperty("bet_index") private int betIndex;
    /**
     * The small blind in the current round. The big blind is twice the
     */
    @JsonProperty("small_blind") private int smallBlind;
    /**
     * The amount of the largest current bet from any one player
     */
    @JsonProperty("current_buy_in") private int currentBuyIn;
    /**
     * The size of the pot (sum of the player bets)
     */
    @JsonProperty("pot") private int pot;
    /**
     * Minimum raise amount. To raise you have to return at least:
     */
    @JsonProperty("minimum_raise") private int minimumRaise;
    /**
     * The index of the player on the dealer button in this round
     */
    @JsonProperty("dealer") private int dealer;
    /**
     * Number of orbits completed. (The number of times the dealer
     */
    @JsonProperty("orbits") private int orbits;
    /**
     * The index of your player, in the players array
     */
    @JsonProperty("in_action") private int inAction;

    @JsonProperty("players") private List<Opponent> otherPlayers;

    /**
     * Finally the array of community cards.
     */
    @JsonProperty("community_cards") private List<Card> communityCards;

    public String getTournamentId() {
        return tournamentId;
    }

    public String getGameId() {
        return gameId;
    }

    public int getRound() {
        return round;
    }

    public int getBetIndex() {
        return betIndex;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getCurrentBuyIn() {
        return currentBuyIn;
    }

    public int getPot() {
        return pot;
    }

    public int getMinimumRaise() {
        return minimumRaise;
    }

    public int getDealer() {
        return dealer;
    }

    public int getOrbits() {
        return orbits;
    }

    public int getInAction() {
        return inAction;
    }

    public List<Opponent> getPlayers() {
        return otherPlayers;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }
}
