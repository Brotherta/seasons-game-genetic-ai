package com.game.engine.dice;

import com.game.engine.SeasonType;
import com.utils.Util;

import java.util.Arrays;
import java.util.List;

public class Dice {

    private final SeasonType type;
    private final DiceFace[] faces;
    private DiceFace actualFace;
    private final int diceId;

    public Dice(SeasonType type, DiceFace[] faces, int diceId) {
        this.type = type;
        this.faces = faces;
        this.diceId = diceId;
        this.actualFace = faces[0];
    }

    public DiceFace rollFace() {
        actualFace = faces[Util.getRandomInt(faces.length)];
        return actualFace;
    }

    public int getDiceId() {
        return diceId;
    }

    public SeasonType getType() {
        return type;
    }

    public DiceFace getFace(int index) {
        return faces[index];
    }

    public DiceFace getActualFace() {
        return actualFace;
    }

    public int getFacesSize() {
        return faces.length;
    }

    public List<DiceFace> getFaces(){
        return Arrays.stream(faces).toList();
    }

    @Override
    public String toString() {
        return "Dice{" +
                "diceId=" + diceId +
                ", type=" + type +
                ", faces=" + Arrays.toString(faces) +
                "}\n";
    }
}
