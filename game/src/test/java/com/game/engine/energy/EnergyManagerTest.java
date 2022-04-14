package com.game.engine.energy;

import com.game.engine.SeasonType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class EnergyManagerTest {

    private PersonalBoard p;
    private EnergyManager em;
    private PersonalBoard b;
    private Player player ;

    @Mock
    GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        p = new PersonalBoard(gameEngine);
        GameEngine engine = new GameEngine(2);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        b = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        b.getEnergyManager().addEnergy(EnergyType.FIRE, 3);
        em = b.getEnergyManager();
    }

    @Test
    void addEnergy() {
        p.getEnergyManager().addEnergy(EnergyType.FIRE);
        p.getEnergyManager().addEnergy(EnergyType.AIR, 2);
        p.getEnergyManager().addEnergy(new int[]{0, 1, 0, 1});
        assertEquals(1, p.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE));
        assertEquals(2, p.getEnergyManager().getAmountOfEnergyType(EnergyType.AIR));
        assertEquals(1, p.getEnergyManager().getAmountOfEnergyType(EnergyType.WATER));
        assertEquals(1, p.getEnergyManager().getAmountOfEnergyType(EnergyType.EARTH));
    }

    @Test
    void consumeEnergy() {
        p.getEnergyManager().addEnergy(EnergyType.FIRE, 5);
        p.getEnergyManager().consumeEnergy(EnergyType.FIRE, 2);
        assertEquals(3, p.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE));
    }

    @Test
    void hasEnoughEnergy() {
        p.getEnergyManager().addEnergy(EnergyType.FIRE, 5);
        assertTrue(p.getEnergyManager().hasEnoughEnergy(EnergyType.FIRE, 5));
        assertFalse(p.getEnergyManager().hasEnoughEnergy(EnergyType.FIRE, 11));
    }

    @Test
    void getEnergyPrice() {
        PersonalBoard p = new PersonalBoard(gameEngine);
        EnergyManager energyManager = p.getEnergyManager();
        // Winter

        assertEquals(1, energyManager.getEnergyPrice(EnergyType.WATER, SeasonType.WINTER));
        assertEquals(2, energyManager.getEnergyPrice(EnergyType.FIRE, SeasonType.WINTER));
        assertEquals(3, energyManager.getEnergyPrice(EnergyType.EARTH, SeasonType.WINTER));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.AIR, SeasonType.WINTER));

        assertEquals(1, energyManager.getEnergyPrice(EnergyType.WATER, SeasonType.SPRING));
        assertEquals(3, energyManager.getEnergyPrice(EnergyType.FIRE, SeasonType.SPRING));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.EARTH, SeasonType.SPRING));
        assertEquals(2, energyManager.getEnergyPrice(EnergyType.AIR, SeasonType.SPRING));

        assertEquals(2, energyManager.getEnergyPrice(EnergyType.WATER, SeasonType.SUMMER));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.FIRE, SeasonType.SUMMER));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.EARTH, SeasonType.SUMMER));
        assertEquals(3, energyManager.getEnergyPrice(EnergyType.AIR, SeasonType.SUMMER));

        assertEquals(3, energyManager.getEnergyPrice(EnergyType.WATER, SeasonType.AUTUMN));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.FIRE, SeasonType.AUTUMN));
        assertEquals(2, energyManager.getEnergyPrice(EnergyType.EARTH, SeasonType.AUTUMN));
        assertEquals(1, energyManager.getEnergyPrice(EnergyType.AIR, SeasonType.AUTUMN));
    }

    @Test
    void chooseRandomsEnergies() {
        p.getEnergyManager().addEnergy(new int[]{0, 4, 0, 2});
        int[] randoms = EnergyManager.chooseRandomsEnergies(p.getEnergyManager());

        assertEquals(0, randoms[0]);
        assertTrue(randoms[1] <= 4 && randoms[1] >= 0);
        assertEquals(0, randoms[2]);
        assertTrue(randoms[3] <= 2 && randoms[3] >= 0);
    }

    @Test
    void testHasEnoughEnergy() {
        assertTrue(em.hasEnoughEnergy(EnergyType.FIRE,0));
        assertTrue(em.hasEnoughEnergy(EnergyType.FIRE,1));
        assertTrue(em.hasEnoughEnergy(EnergyType.FIRE,2));
        assertTrue(em.hasEnoughEnergy(EnergyType.FIRE,3));
        assertFalse(em.hasEnoughEnergy(EnergyType.FIRE,4));
        assertFalse(em.hasEnoughEnergy(EnergyType.FIRE,-1));
        assertTrue(em.hasEnoughEnergy(new int[]{0,0,1,0}));
        assertTrue(em.hasEnoughEnergy(new int[]{0,0,2,0}));
        assertTrue(em.hasEnoughEnergy(new int[]{0,0,3,0}));
        assertFalse(em.hasEnoughEnergy(new int[]{0,0,4,0}));
        assertFalse(em.hasEnoughEnergy(new int[]{0,0,-1,0}));

    }

    @Test
    void testHasEnoughRoom() {
        GameEngine engine = new GameEngine(2);
        Player p = engine.getPlayersCentralManager().getPlayerByID(0);
        PersonalBoard b = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(p.getNumPlayer());
        b.getEnergyManager().addEnergy(EnergyType.FIRE, 3);
        EnergyManager em = b.getEnergyManager();
        assertTrue(em.hasEnoughRoom(4));
        assertTrue(em.hasEnoughRoom(1));
        assertFalse(em.hasEnoughRoom(10));
        assertFalse(em.hasEnoughRoom(5));
        assertFalse(em.hasEnoughRoom(-1));

    }

    @Test
    void testAddEnergy() {
        em.addEnergy(EnergyType.AIR,10);
        assertEquals(em.getGauge(),em.getAmountofEnergies());

    }

    @Test
    void testAddEnergy2() {
        em.addEnergy(EnergyType.AIR,1);
        assertTrue(em.getGauge() > em.getAmountofEnergies());

    }
}