package com.game.engine.bonus;

import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.StrategyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BonusManagerTest {

    private Player p;
    GameEngine engine;
    PersonalBoard personalBoard;
    private int oldCardAmount;
    private int oldScore;
    private int oldCrystal;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(2);
        this.p = engine.getPlayersCentralManager().getPlayerByID(0);
        personalBoard = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        personalBoard.getEnergyManager().addEnergy(new int[] {0, 2, 0, 2});
        personalBoard.addCrystal(50);
        oldCardAmount = personalBoard.getCardManager().getCards().size();
        oldScore = p.getScore();
        oldCrystal = personalBoard.getCrystal();
    }

    @Test
    void testExchangeEnergy() {
        assertTrue(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 1, 0, 1 }, new int[] { 1, 0, 1, 0 }));
        assertArrayEquals          (new int[] {1, 1, 1, 1}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 1, 1, 1, 1 }, new int[] { 1, 0, 1, 0 }));
        assertArrayEquals          (new int[] {1, 1, 1, 1}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 0, 0, 0 }, new int[] { 1, 0, 1, 0 }));
        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 1, 0, 0, 0 }, new int[] { 1, 0, 1, 0 }));
        assertArrayEquals          (new int[] {1, 1, 1, 1}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 1, 0, 1 }, new int[] { 0, 0, 0, 0 }));
        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 1, 0, 1 }, new int[] { 1, 0, 0, 0 }));
        assertArrayEquals          (new int[] { 1, 1, 1, 1 }, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        assertTrue(personalBoard.getBonusManager().exchangeEnergy(new int[] { 1, 0, 1, 0 }, new int[] { 0, 1, 0, 1 }));
        assertArrayEquals          (new int[] {0, 2, 0, 2}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        // One more to set the bonus amount to 0
        assertTrue(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 1, 0, 1 }, new int[] { 2, 0, 0, 0 }));
        assertArrayEquals          (new int[] {2, 1, 0, 1}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        // No bonus anymore
        assertFalse(personalBoard.getBonusManager().exchangeEnergy(new int[] { 0, 1, 0, 1 }, new int[] { 2, 0, 0, 0 }));
        assertArrayEquals          (new int[] {2, 1, 0, 1}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());
    }

    @Test
    void testCrystallizeEnergy() {
        int[] from = new int[] {0, 2, 0, 2};

        int oldCrystal = personalBoard.getCrystal();
        assertTrue(personalBoard.getBonusManager().crystallizeEnergy(from));
        int newCrystal = personalBoard.getCrystal();

        int sum = 0;
        for(int i = 0; i < from.length; i++) {
            sum += from[i] * personalBoard.getEnergyManager().getEnergyPrice(EnergyType.values()[i], personalBoard.getCurrentSeason());
        }
        assertEquals(sum, newCrystal - oldCrystal);

        assertFalse(personalBoard.getBonusManager().crystallizeEnergy(from));
        personalBoard.getBonusManager().decreaseBonusAmount();
        personalBoard.getBonusManager().decreaseBonusAmount();
        assertFalse(personalBoard.getBonusManager().crystallizeEnergy(new int[] { 0, 0, 0, 0}));
    }

    @Test
    void invocation() {
        if(personalBoard.getCardManager().canInvokeAnyCard()) {
            personalBoard.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(p.getFacadeIA()));
            int cardScore = personalBoard.getCardManager().getCards().get(personalBoard.getCardManager().getCards().size() - 1).getPoints();
            int cardCrystal = personalBoard.getCardManager().getCards().get(personalBoard.getCardManager().getCards().size() - 1).getCrystalCost();

            assertEquals(oldCrystal + cardCrystal, personalBoard.getCrystal());
            assertEquals(oldScore + cardScore, p.getScore());
            assertEquals(oldCardAmount + 1, personalBoard.getCardManager().getCards().size());
        }

    }

}