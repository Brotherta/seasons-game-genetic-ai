package com.utils.loaders.dice;

import com.game.engine.SeasonType;
import com.game.engine.energy.EnergyType;
import com.game.engine.dice.DiceFace;
import org.json.simple.JSONObject;

class FaceLoader {

    private final JSONObject faceJson;
    private DiceFace face;
    private final int faceId;

    public FaceLoader(JSONObject faceJson, SeasonType type, int faceId) {
        this.faceJson = faceJson;
        this.faceId = faceId;
    }

    public DiceFace loadFace() {
        return new DiceFace(
                faceId,
                (Boolean) faceJson.get("sell"),
                (Boolean) faceJson.get("drawCard"),
                (Boolean) faceJson.get("invocation"),
                ((Long) faceJson.get("distance")).intValue(),
                ((Long) faceJson.get("crystal")).intValue(),
                loadEnergiesAmount()
        );
    }

    private int[] loadEnergiesAmount() {
        int[] energiesAmount = new int[EnergyType.values().length];
        JSONObject energiesAmountJson = (JSONObject) faceJson.get("energiesAmount");
        for (int i = 0; i < EnergyType.values().length; i++) {
            energiesAmount[i] = ((Long) energiesAmountJson.get(EnergyType.values()[i].toString())).intValue();
        }

        return energiesAmount;
    }
}
