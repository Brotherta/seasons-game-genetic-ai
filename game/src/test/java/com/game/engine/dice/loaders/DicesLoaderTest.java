package com.game.engine.dice.loaders;

import com.game.engine.SeasonType;
import com.utils.Util;
import com.game.engine.dice.Cup;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.utils.loaders.dice.DicesLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DicesLoaderTest {

    private DicesLoader rootLoader;
    private Cup[] cups;
    private int diceAmountWanted;

    @BeforeEach
    void setUp() {
        rootLoader = DicesLoader.getDicesLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("dices.json")).getPath());
        diceAmountWanted = Util.getRandomInt(Util.MAX_AMOUNT_OF_DICE);
        cups = rootLoader.loadCups(diceAmountWanted);
    }

    @Test
    void loadCups() {
        assertNotNull(rootLoader.loadCups(Util.MAX_AMOUNT_OF_DICE));
    }

    @Test
    void validSeasonCup() {
        assertEquals(cups.length, SeasonType.values().length);
        for(int i = 0; i < cups.length; i++) {
            assertEquals(cups[i].getType(), SeasonType.values()[i]);
        }
    }

    @Test
    void validAmountOfDice() {
        for(Cup c : cups) {
            assertEquals(c.getDices().length, diceAmountWanted);
        }
    }

    @Test
    void validAmountOfFaces() {
        for(Cup c : cups) {
            for(Dice d : c.getDices()) {
                assertEquals(d.getFacesSize(), DiceFace.FACES_AMOUNT);
            }
        }
    }

    @Test
    void validAllNotNull() {
        for (Cup c : cups) {
            assertNotNull(c.getDices());
            assertNotNull(c.getType());

            for (Dice d : c.getDices()) {
                assertNotNull(d.getType());
                assertNotNull(d.getFace(Util.getRandomInt(DiceFace.FACES_AMOUNT)));
                assertNotNull(d.getFace(Util.getRandomInt(DiceFace.FACES_AMOUNT)).toString());
                assertEquals(d.getFacesSize(), DiceFace.FACES_AMOUNT);
            }
        }
    }

    @Test
    void validAllTypes() {
        for(Cup c : cups) {
            for(Dice d : c.getDices()) {
                assertEquals(c.getType(), d.getType());
            }
        }
    }

    @Test
    void validAllFacesValues() {
        for(Cup c : cups) {
            for(Dice d : c.getDices()) {
                for(int i = 0; i < d.getFacesSize(); i++) {
                    DiceFace face = d.getFace(0);

                    assertTrue(face.getDistance() > 0);
                    assertTrue(face.getDistance() <= 3);

                    int sumEnergies = Arrays.stream(face.getEnergiesAmount()).sum();
                    assertTrue(sumEnergies >= 0 && sumEnergies <= 2);

                    assertTrue(face.getCrystal() >= 0 && face.getCrystal() <= 6);

                    assertTrue(face.getId() >= 1 && face.getId() <= 6);
                }
            }
        }
    }
}