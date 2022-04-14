package com.game.playerassets.ia;

import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.utils.Util;
import com.utils.loaders.dice.DicesLoader;
import com.utils.stats.StatsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BasicIATest {

    GameEngine engine;
    Player p ;
    PersonalBoard personalBoard;
    int initialScore = 3;
    @BeforeEach
    void setUp() {
        engine = new GameEngine(2);
        p = engine.getPlayersCentralManager().getPlayerByID(0);
        personalBoard = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        personalBoard.setScore(initialScore);
        DicesLoader dl = DicesLoader.getDicesLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("dices.json")).getPath());
        Dice d = dl.loadCups(Util.MAX_AMOUNT_OF_DICE)[0].getDices()[0];
        personalBoard.setActualDice(d);
    }

    @Test
    void getScore() {
        assertEquals(p.getScore(),initialScore);
        assertNotEquals(99, p.getScore());
        assertTrue(p.getScore() >= 0 );
    }

    @Test
    void setScore() {
        personalBoard.setScore(12);
        assertEquals(12, p.getScore());
    }

    @Test
    void addScore() {
        personalBoard.addScore(10);
        assertEquals(13, p.getScore());
    }

    @Test
    void testEnergy() {
        int energyAmount = personalBoard.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE);
        personalBoard.getEnergyManager().addEnergy(EnergyType.FIRE);
        assertTrue(personalBoard.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE) > energyAmount);
        personalBoard.getEnergyManager().consumeEnergy(EnergyType.FIRE, 1);
        assertEquals(personalBoard.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE), energyAmount);
    }


    @Test
    void testGetPlayersClassement() {
        GameEngine engine = new GameEngine(4);
        for(PersonalBoard pb : engine.getPlayersCentralManager().getPersonalBoardList()) {
            pb.setScore(Util.getRandomInt(100));
        }

        int[] classements = StatsManager.getPlayersClassement(engine.getPlayersCentralManager().getPlayerList());

        assertEquals(classements[0], engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).getScore());
        assertEquals(classements[1], engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getScore());
        assertEquals(classements[2], engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getScore());
        assertEquals(classements[3], engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getScore());

        assertEquals(classements[0], engine.getPlayersCentralManager().getPlayerByID(0).getScore());
        assertEquals(classements[1], engine.getPlayersCentralManager().getPlayerByID(1).getScore());
        assertEquals(classements[2], engine.getPlayersCentralManager().getPlayerByID(2).getScore());
        assertEquals(classements[3], engine.getPlayersCentralManager().getPlayerByID(3).getScore());
    }

    @Test
    void testIsTheLast() {
        GameEngine engine = new GameEngine(4);
        ArrayList<PersonalBoard> boardList = engine.getPlayersCentralManager().getPersonalBoardList();

        int last = 0;

        for(int i = 0; i < boardList.size(); i++) {
            if(boardList.get(i).getScore() < boardList.get(last).getScore()) {
                last = i;
            }
        }

        assertTrue(StatsManager.isTheLast(engine.getPlayersCentralManager().getPlayerList(), boardList.get(last).getPlayer()));
    }
}