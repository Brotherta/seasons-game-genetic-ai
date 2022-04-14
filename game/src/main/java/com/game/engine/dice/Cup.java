package com.game.engine.dice;

import com.game.engine.SeasonType;

public class Cup {

    private final Dice[] dices;

    private final SeasonType type;

    public Cup(SeasonType type, Dice[] dices) {
        this.type = type;
        this.dices = dices;
    }

    public Dice[] getDices() {
        return dices;
    }

    public SeasonType getType() {
        return type;
    }
}
