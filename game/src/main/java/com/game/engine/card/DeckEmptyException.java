package com.game.engine.card;

public class DeckEmptyException extends RuntimeException {

    public DeckEmptyException(String message) {
        super(message);
    }
}
