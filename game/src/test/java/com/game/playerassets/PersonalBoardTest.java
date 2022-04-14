package com.game.playerassets;

import com.game.engine.card.Card;
import com.game.engine.gamemanager.GameEngine;
import com.utils.loaders.cards.Type;
import com.utils.stats.StatsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PersonalBoardTest {
    PersonalBoard p;
    private final int INITIALCRYSTAL = 4;

    @Mock
    GameEngine gameEngine;

    @Mock
    StatsManager statsManager = mock(StatsManager.class);

    @BeforeEach
    void setUp() {
        p = new PersonalBoard(gameEngine);
        p.setStatsManager(statsManager);
        p.addCrystal(INITIALCRYSTAL);
    }

    @Test
    void getCrystal() {
        assertEquals(INITIALCRYSTAL,p.getCrystal());
    }

    @Test
    void spendCrystal() {
        p.spendCrystal(INITIALCRYSTAL);
        assertEquals(0,p.getCrystal());

    }

    @Test
    void getTotalCardsEnergyCost() {
        Card card1 = new Card("card1", 1, 0, 0, new int[] {1, 0, 0, 0}, Type.OBJECT);
        Card card2 = new Card("card2", 2, 0, 0, new int[] {0, 1, 0, 0}, Type.OBJECT);
        Card card3 = new Card("card3", 3, 0, 0, new int[] {0, 0, 1, 0}, Type.OBJECT);
        Card card4 = new Card("card4", 4, 0, 0, new int[] {0, 0, 0, 1}, Type.OBJECT);

        p.getCardManager().addCard(card1);
        p.getCardManager().addCard(card2);
        p.getCardManager().addCard(card3);
        p.getCardManager().addCard(card4);

        int[] totalEnergies = p.getTotalCardsEnergyCost();

        assertArrayEquals(new int[] {1,1,1,1}, totalEnergies);
    }
}