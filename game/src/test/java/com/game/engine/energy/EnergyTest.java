package com.game.engine.energy;

import com.game.engine.SeasonType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyTest {

    @Test
    void getEnergyType() {
        assertEquals(EnergyType.WATER, new Energy(SeasonType.WINTER).getEnergyType());
        assertEquals(EnergyType.AIR, new Energy(SeasonType.AUTUMN).getEnergyType());
        assertEquals(EnergyType.EARTH, new Energy(SeasonType.SPRING).getEnergyType());
        assertEquals(EnergyType.FIRE, new Energy(SeasonType.SUMMER).getEnergyType());
    }

    @Test
    void testToString() {
        assertEquals(EnergyType.WATER.toString(), new Energy(SeasonType.WINTER).toString());
        assertEquals(EnergyType.AIR.toString(), new Energy(SeasonType.AUTUMN).toString());
        assertEquals(EnergyType.EARTH.toString(), new Energy(SeasonType.SPRING).toString());
        assertEquals(EnergyType.FIRE.toString(), new Energy(SeasonType.SUMMER).toString());
    }
}