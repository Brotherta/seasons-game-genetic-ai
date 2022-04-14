package com.game.engine.dice;

import com.game.engine.energy.EnergyType;

import java.util.Arrays;

public class DiceFace {

    public static final int FACES_AMOUNT = 6;

    private final int id;
    private final boolean sell;
    private final boolean drawCard;
    private final boolean invocation;
    private final int distance;
    private final int crystal;
    private final int[] energiesAmount;

    public DiceFace(int id, boolean sell, boolean drawCard, boolean invoc, int distance, int crystal, int[] energiesAmount) {
        this.id = id;
        this.sell = sell;
        this.drawCard = drawCard;
        this.invocation = invoc;
        this.distance = distance;
        this.crystal = crystal;
        this.energiesAmount = energiesAmount;
    }

    public int getId() {
        return id;
    }

    public boolean isSell() {
        return sell;
    }

    public boolean isDrawCard() {
        return drawCard;
    }

    public boolean isInvocation() {
        return invocation;
    }

    public int getDistance() {
        return distance;
    }

    public int getCrystal() {
        return crystal;
    }

    public int[] getEnergiesAmount() {
        return energiesAmount;
    }

    public String facePropertyToString() {
        StringBuilder properties = new StringBuilder();

        int[] energies = getEnergiesAmount();
        for (EnergyType energyType : EnergyType.values()) {
            if (energies[energyType.ordinal()] > 0) {
                properties.append(String.format(" | %d %s", energies[energyType.ordinal()], energyType));
            }
        }
        if (isInvocation()) {
            properties.append(" | INVOCATION");
        }
        if (isSell()) {
            properties.append(" | CRYSTALLISATION");
        }
        if (isDrawCard()) {
            properties.append(" | DRAWING");
        }
        if (getCrystal() > 0) {
            properties.append(String.format(" | %d CRYSTALS", getCrystal()));
        }
        return properties.toString();
    }

    @Override
    public String toString() {
        return "DiceFace{" +
                "id=" + id +
                ", sell=" + sell +
                ", drawCard=" + drawCard +
                ", invocation=" + invocation +
                ", distance=" + distance +
                ", crystal=" + crystal +
                ", energiesAmount=" + Arrays.toString(energiesAmount) +
                '}';
    }
}
