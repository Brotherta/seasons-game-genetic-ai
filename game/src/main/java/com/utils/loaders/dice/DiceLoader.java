package com.utils.loaders.dice;

import com.game.engine.SeasonType;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import org.json.simple.JSONObject;

class DiceLoader {

    private final SeasonType type;
    private final JSONObject diceJson;
    private final int diceId;

    public DiceLoader(JSONObject diceJson, SeasonType type, int diceId) {
        this.diceJson = diceJson;
        this.type = type;
        this.diceId = diceId;
    }

    public Dice loadDice() {
        return new Dice(type, loadFaces(), diceId);
    }

    private DiceFace[] loadFaces() {
        DiceFace[] dices = new DiceFace[6];
        JSONObject facesJson = (JSONObject) diceJson.get("faces");

        for (int i = 0; i < dices.length; i++) {
            JSONObject faceJson = (JSONObject) facesJson.get("face" + (i + 1));
            dices[i] = new FaceLoader(faceJson, type, (i + 1)).loadFace();
        }

        return dices;
    }
}
