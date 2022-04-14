package com.game.engine.energy;

public class Amulet {

    private final int[] energyList;

    public Amulet(int[] energyList) {
        this.energyList = energyList;
    }

    public int[] getEnergyList() {
        return energyList;
    }

    public void consumeEnergyInAmulet(EnergyType type, int quantity) {
        for (int i = 0; i < quantity; i++) {
            energyList[type.ordinal()]--;
        }
    }
}
