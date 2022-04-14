package com.utils.loaders.dice;

import com.game.engine.SeasonType;
import com.utils.Util;
import com.game.engine.dice.Cup;
import com.game.engine.dice.Dice;
import org.json.simple.JSONObject;

import java.util.HashSet;

class CupLoader {

    private final int diceAmountWanted;
    private final SeasonType type;
    private final JSONObject cupJson;

    public CupLoader(JSONObject cupJson, SeasonType type, int diceAmountWanted) {
        this.cupJson = cupJson;
        this.type = type;
        this.diceAmountWanted = Math.min(diceAmountWanted, Util.MAX_AMOUNT_OF_DICE);
    }

    public Cup loadCup() {
        return new Cup(type, loadDices());
    }

    private Dice[] loadDices() {
        Dice[] dices = new Dice[diceAmountWanted];

        HashSet<Integer> dicesToLoad = new HashSet<>();
        while (dicesToLoad.size() < diceAmountWanted) {
            dicesToLoad.add(Util.getRandomInt(Util.MAX_AMOUNT_OF_DICE) + 1);
        }

        int diceIndex = 0;
        for (Integer i : dicesToLoad) {
            JSONObject diceJson = (JSONObject) cupJson.get("dice" + i);
            dices[diceIndex] = new DiceLoader(diceJson, type, i).loadDice();
            diceIndex++;
        }
        return dices;
    }
}
