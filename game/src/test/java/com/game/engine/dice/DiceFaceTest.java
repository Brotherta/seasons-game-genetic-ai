package com.game.engine.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceFaceTest {

    @Test
    void facePropertyToString() {
        DiceFace df1 = new DiceFace(0, true, false, true, 2, 3, new int[] {0,0,0,1});
        String res = " | 1 EARTH | INVOCATION | CRYSTALLISATION | 3 CRYSTALS";
        assertEquals(res, df1.facePropertyToString());

        DiceFace df2 = new DiceFace(0, false, true, false, 1, 0, new int[] {1,1,1,0});
    }
}