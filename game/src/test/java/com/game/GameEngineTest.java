package com.game;

import com.game.engine.gamemanager.GameEngine;
import com.utils.stats.StatsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameEngineTest {
    GameEngine engine;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).addScore(2);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).addScore(8);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).addScore(3);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).addScore(4);

        engine.getPlayersCardsManager().doPrelude();
    }

    @Test
    void findWinner() {
        assertEquals(engine.getPlayersCentralManager().getPlayerByID(1), StatsManager.findWinner(engine));
    }

    @Test
    void testMalus() {
        engine = new GameEngine(4);
        engine.setDescription(new StringBuilder());

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).addScore(50);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).addScore(50);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).addScore(50);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).addScore(50);

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getBonusManager().decreaseBonusAmount();
        engine.getPlayersCentralManager().countPenalties(engine.getDescription());

        assertEquals(45, engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).getScore());
        assertEquals(38, engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getScore());
        assertEquals(30, engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getScore());
        assertEquals(50, engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getScore());
    }
}