package com.game.engine.effects;

public class TooManyEnergyException extends RuntimeException{
    public TooManyEnergyException(String message) {
        super(message);
    }
}
