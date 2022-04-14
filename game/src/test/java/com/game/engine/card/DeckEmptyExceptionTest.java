package com.game.engine.card;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DeckEmptyExceptionTest {

    @Test
    void testDeckEmptyExceptionTest() {
        assertThrows(DeckEmptyException.class, () -> new Deck(new ArrayList<>()).drawCard());
    }

}