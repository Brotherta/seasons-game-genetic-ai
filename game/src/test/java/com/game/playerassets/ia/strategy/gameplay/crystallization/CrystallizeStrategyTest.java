package com.game.playerassets.ia.strategy.gameplay.crystallization;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.FireEffect;
import com.game.engine.effects.effects.KairnEffect;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CrystallizeStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard personalBoard;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        personalBoard = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        personalBoard.getPlayerTurnManager().addGameplayChoice(GameplayChoice.CRYSTALLIZE);
    }

    @Test
    void testMostValuableEnergyStrategy() {
        player.setGameplayStrategy(new MostValuableEnergyStrategy(endStrat));

        // Cas où la stratégie marche
        personalBoard.getEnergyManager().addEnergy(EnergyType.EARTH, 3);
        personalBoard.getEnergyManager().addEnergy(EnergyType.WATER, 2);
        assertArrayEquals(new int[]{0, 2, 0, 3}, personalBoard.getEnergyManager().getAmountOfEnergiesArray());

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.CRYSTALLIZE, gameplayChoice);
        assertArrayEquals(new int[]{0, 0, 0, 3}, player.getFacadeIA().getEnergiesToCrystallizeF());


        personalBoard.getEnergyManager().reset();

        // Cas où la stratégie ne marche pas
        personalBoard.getEnergyManager().addEnergy(EnergyType.WATER, 2);

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();

        assertNull(gameplayChoiceFail);
        assertArrayEquals(new int[]{0, 0, 0, 0}, player.getFacadeIA().getEnergiesToCrystallizeF());
    }

    @Test
    void testLeastValuableEnergyStrategy() {
        player.setGameplayStrategy(new LeastValuableEnergyStrategy(endStrat));

        // Cas où la stratégie marche
        personalBoard.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(gameplayChoice, GameplayChoice.CRYSTALLIZE);
        assertArrayEquals(new int[]{1, 1, 0, 0}, player.getFacadeIA().getEnergiesToCrystallizeF());

        // Cas où la stratégie ne marche pas

        personalBoard.getEnergyManager().reset();

        personalBoard.getEnergyManager().addEnergy(new int[]{0, 0, 1, 1});

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();

        assertNull(gameplayChoiceFail);
        assertArrayEquals(new int[]{0, 0, 0, 0}, player.getFacadeIA().getEnergiesToCrystallizeF());
    }

    @Test
    void testUselessEnergyStrategy() {
        player.setGameplayStrategy(new UselessEnergyStrategy(endStrat));

        // Cas où la stratégie marche
        personalBoard.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(gameplayChoice, GameplayChoice.CRYSTALLIZE);
        assertArrayEquals(new int[]{1, 1, 1, 1}, player.getFacadeIA().getEnergiesToCrystallizeF());

        // Cas où la stratégie ne marche pas
        personalBoard.getPlayerTurnManager().getConflictTable().initTable();
        personalBoard.getEnergyManager().reset();

        personalBoard.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        Card kairn = new Card("kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate kairnEffect = new KairnEffect("kairnEffect", EffectType.DEFAULT, engine);
        kairn.setEffect(kairnEffect);

        personalBoard.getCardManager().getInvokeDeck().upInvocationGauge(1);
        personalBoard.getCardManager().getInvokeDeck().addCard(kairn);

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();

        assertNull(gameplayChoiceFail);
        assertArrayEquals(new int[]{0, 0, 0, 0}, player.getFacadeIA().getEnergiesToCrystallizeF());

        // Cas où la stratégie marche
        personalBoard.getPlayerTurnManager().getConflictTable().initTable();

        personalBoard.getEnergyManager().reset();
        personalBoard.getCardManager().reset();

        personalBoard.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});


        Card fireAmulet = new Card("fireAmulet", 2, 0, 0, new int[]{1, 0, 0, 0}, Type.OBJECT);
        EffectTemplate fireEffect = new FireEffect("fireEffect", EffectType.DEFAULT, engine);
        fireAmulet.setEffect(fireEffect);

        personalBoard.getCardManager().addCard(fireAmulet);

        GameplayChoice gameplayChoice1 = player.makeGameplayChoice();

        assertEquals(GameplayChoice.CRYSTALLIZE, gameplayChoice1);
        assertArrayEquals(new int[]{0, 1, 1, 1}, player.getFacadeIA().getEnergiesToCrystallizeF());
    }

    @Test
    void testMostInStockEnergyStrategy() {
        player.setGameplayStrategy(new MostInStockEnergyStrategy(endStrat));

        // Cas où la stratégie marche
        personalBoard.getEnergyManager().reset();
        personalBoard.getEnergyManager().addEnergy(new int[] {4,0,1,1});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.CRYSTALLIZE, gameplayChoice);
        assertArrayEquals(new int[] {4,0,0,0}, player.getFacadeIA().getEnergiesToCrystallizeF());



        //Cas où la stratégie ne marche pas
        personalBoard.getPlayerTurnManager().getConflictTable().initTable();
        personalBoard.getEnergyManager().reset();

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();

        assertNull(gameplayChoiceFail);
        assertArrayEquals(new int[] {0,0,0,0}, player.getFacadeIA().getEnergiesToCrystallizeF());


        // Cas où je ne sais pas
        personalBoard.getPlayerTurnManager().getConflictTable().initTable();
        personalBoard.getEnergyManager().reset();

        personalBoard.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        GameplayChoice gameplayChoice1 = player.makeGameplayChoice();

        assertEquals(GameplayChoice.CRYSTALLIZE, gameplayChoice1);
        assertArrayEquals(new int[] {1,0,0,0}, player.getFacadeIA().getEnergiesToCrystallizeF());

    }

    @Test
    void testCurrentAndNextSeasonsEnergyStrategy() {
        player.setGameplayStrategy(new CurrentAndNextSeasonsEnergyStrategy(endStrat));

        //Cas où la stratégie marche
        personalBoard.getEnergyManager().addEnergy(new int[] {1,1,2,2});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.CRYSTALLIZE, gameplayChoice);
        assertArrayEquals(new int[] {0,0,2,2}, player.getFacadeIA().getEnergiesToCrystallizeF());

        // Cas où la stratégie ne marche pas

        personalBoard.getEnergyManager().reset();
        personalBoard.getPlayerTurnManager().getConflictTable().initTable();

        personalBoard.getEnergyManager().addEnergy(new int[] {1,1,0,0});

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();

        assertNull(gameplayChoiceFail);
        assertArrayEquals(new int[] {0,0,0,0}, player.getFacadeIA().getEnergiesToCrystallizeF());
    }
}