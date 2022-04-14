package com.game.engine.energy;

import com.game.engine.SeasonType;
import com.utils.Util;

public class Energy {

    private final EnergyType energyType;

    public Energy() {
        this(EnergyType.values()[Util.getRandomInt(EnergyType.values().length)]);
    }

    public Energy(SeasonType seasonType) {
        EnergyType e;
        switch (seasonType) {
            case WINTER -> e = EnergyType.WATER;
            case AUTUMN -> e = EnergyType.AIR;
            case SPRING -> e = EnergyType.EARTH;
            default -> e = EnergyType.FIRE;
        }
        this.energyType = e;
    }

    public Energy(EnergyType type) {
        this.energyType = type;
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }

    @Override
    public String toString() {
        return energyType.toString();
    }
}

