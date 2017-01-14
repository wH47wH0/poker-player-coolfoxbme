package org.leanpoker.player;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "CoolFoxBme";
    public static final String IN_ACTION = "in_action";

    public static int betRequest(GameState gameState) {
        double ourBet =0;

        List<Opponent> players = gameState.getPlayers();
        for (Opponent player : players) {
            if (player.getStatus().equals(IN_ACTION)) {
                ourBet = player.getBet();
            }
        }
        double betToCall = gameState.getCurrentBuyIn();
        return (int) (betToCall - ourBet + gameState.getMinimumRaise());

    }

    public static void showdown(JsonElement game) {
    }
}
