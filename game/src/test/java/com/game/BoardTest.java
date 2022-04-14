package com.game;

import com.game.engine.Board;
import com.game.engine.SeasonType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void timeForward() {
        Board board = new Board();
        assertEquals(1, board.getYear());
        board.timeForward(12);
        assertEquals(2, board.getYear());
        board.timeForward(12);
        assertEquals(3, board.getYear());
    }

    @Test
    void getSeasonByMonth() {
        Board board = new Board();
        assertEquals(SeasonType.WINTER, board.getSeasonByMonth());
        board.timeForward(3);
        assertEquals(SeasonType.SPRING, board.getSeasonByMonth());
        board.timeForward(3);
        assertEquals(SeasonType.SUMMER, board.getSeasonByMonth());
        board.timeForward(3);
        assertEquals(SeasonType.AUTUMN, board.getSeasonByMonth());
    }

    @Test
    void reset() {
        Board board = new Board();
        board.timeForward(15);
        assertEquals(2, board.getYear());
        assertEquals(3, board.getMonth());
        assertEquals(SeasonType.SPRING, board.getSeasonByMonth());

        board.reset();
        assertEquals(1, board.getYear());
        assertEquals(0, board.getMonth());
        assertEquals(SeasonType.WINTER, board.getSeasonByMonth());
    }
}