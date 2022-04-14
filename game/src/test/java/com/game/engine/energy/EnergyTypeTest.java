package com.game.engine.energy;

import com.utils.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyTypeTest {

    @Test
    void getName() {
        assertEquals("AIR", EnergyType.getName(0));
        assertEquals("WATER", EnergyType.getName(1));
        assertEquals("FIRE", EnergyType.getName(2));
        assertEquals("EARTH", EnergyType.getName(3));
        int random = Util.getRandomInt(4);
        assertEquals(EnergyType.values()[random].toString(), EnergyType.getName(random));
        assertNull(EnergyType.getName(Util.getRandomInt() + 4));
    }
}