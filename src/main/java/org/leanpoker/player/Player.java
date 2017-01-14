package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "CoolFoxBme";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        int bet=0;
        JsonArray jsonArray = json.get("players").getAsJsonArray();
        for (JsonElement array : jsonArray){
            String playerStatus = array.getAsJsonObject().get("status").toString();
            if (playerStatus.equals("in_action")){
                bet = array.getAsJsonObject().get("bet").getAsInt();
            }
        }
        int betToCall = json.get("current_buy_in").getAsInt();
        return betToCall-bet+json.get("minimum_raise").getAsInt();
    }

    public static void showdown(JsonElement game) {
    }
}
