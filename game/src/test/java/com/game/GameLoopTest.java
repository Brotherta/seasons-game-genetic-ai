package com.game;


import com.game.engine.Board;
import com.game.engine.GameLoop;
import com.game.engine.GameStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameLoopTest {

    GameLoop gameLoop;


    @BeforeEach
    void setUp() {
        gameLoop = new GameLoop(4, 1, false);
    }

    @Test
    void start() {
        gameLoop.start();
        assertEquals(gameLoop.getStatus(), GameStatus.QUITTING);

    }

    @Test
    void checkTurnAmount() {
        gameLoop.start();
        if(gameLoop.getStatus() == GameStatus.QUITTING){
            assertEquals(gameLoop.getEngine().getBoard().getYear(), Board.getNbYear()+1);}
    }

    @Test
    void loop() {
        gameLoop.start();
        assertEquals(gameLoop.getStatus(), GameStatus.QUITTING);
        assertEquals(gameLoop.getEngine().getBoard().getYear(), Board.getNbYear()+1);
    }
}