package com.utils.loaders.dice;

import com.game.engine.SeasonType;
import com.game.engine.dice.Cup;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Arrays;


public class DicesLoader {

    private static DicesLoader instance;
    private JSONObject dicesRoot;

    private DicesLoader(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            dicesRoot = (JSONObject) parser.parse(new FileReader(filePath));
            dicesRoot = (JSONObject) dicesRoot.get("Dices");
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

    public Cup[] loadCups(int dicesAmountWanted) {
        Cup[] cups = new Cup[SeasonType.values().length];

        for (int i = 0; i < cups.length; i++) {
            SeasonType season = SeasonType.values()[i];
            JSONObject cupJson = (JSONObject) dicesRoot.get(season.toString());
            cups[i] = new CupLoader(cupJson, season, dicesAmountWanted).loadCup();
        }

        return cups;
    }

    public static DicesLoader getDicesLoader(String filePath) {
        if (instance == null) {
            instance = new DicesLoader(filePath);
        }
        return instance;
    }
}
