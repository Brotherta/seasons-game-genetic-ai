package com.game.engine.energy;

public enum EnergyType {
    AIR, WATER, FIRE, EARTH;

    public static String getName(int i) {
        return switch (i) {
            case 0 -> "AIR";
            case 1 -> "WATER";
            case 2 -> "FIRE";
            case 3 -> "EARTH";
            default -> null;
        };
    }
}
