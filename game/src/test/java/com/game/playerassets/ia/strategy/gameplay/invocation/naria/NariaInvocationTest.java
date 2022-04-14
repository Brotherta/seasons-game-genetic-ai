package com.game.playerassets.ia.strategy.gameplay.invocation.naria;

import com.game.engine.card.Card;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NariaInvocationTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);


    @BeforeEach
    void setup() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(7);
    }

    @Test
    void testNariaInvocationAggressive() {
        player.setGameplayStrategy(new NariaInvocationAggressive(endStrat));

        Card nariaCard = new Card("Naria", 12, 0, 0, new int[] {1,1,1,1}, Type.FAMILIAR);
        pb.getCardManager().addCard(nariaCard);

        // Cas où la stratégie marche
        pb.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertEquals(gameplayChoice, GameplayChoice.INVOKE);
        assertEquals(cardChosen, nariaCard);

    }

    @Test
    void testNariaInvocationAggressiveFail1() {
        player.setGameplayStrategy(new NariaInvocationAggressive(endStrat));

        Card nariaCard = new Card("Naria", 12, 0, 0, new int[] {1,1,1,1}, Type.FAMILIAR);
        pb.getCardManager().addCard(nariaCard);

        // Cas où la stratégie ne marche pas car pas assez d'énergies
        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoice);
        assertNull(cardChosen);
    }

    @Test
    void testNariaInvocationAggressiveFail2() {
        player.setGameplayStrategy(new NariaInvocationAggressive(endStrat));

        // Cas où la stratégie ne marche pas car pas la carte en main
        pb.getEnergyManager().addEnergy(new int[] {1,0,0,0});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoice);
        assertNull(cardChosen);
    }

    @Test
    void testNariaInvocationModerateThirdYear() {
        player.setGameplayStrategy(new NariaInvocationModerateThirdYear(endStrat));

        // Cas où la stratégie marche
        Card nariaCard = new Card("Naria", 12, 0, 0, new int[] {1,1,1,1}, Type.FAMILIAR);
        pb.getCardManager().addCard(nariaCard);
        pb.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        int initialYear = 1;
        assertEquals(initialYear, player.getFacadeIA().getYearF());
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(11);
        int monthExpected = 11;
        int yearExpected = 3;
        assertEquals(monthExpected, player.getFacadeIA().getMonthF());
        assertEquals(yearExpected, player.getFacadeIA().getYearF());

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertEquals(gameplayChoice, GameplayChoice.INVOKE);
        assertEquals(cardChosen, nariaCard);
    }

    @Test
    void testNariaInvocationModerateThirdYearFail() {
        player.setGameplayStrategy(new NariaInvocationModerateThirdYear(endStrat));

        //Cas où la srtatégie ne marche pas
        Card nariaCard = new Card("Naria", 12, 0, 0, new int[] {1,1,1,1}, Type.FAMILIAR);
        pb.getCardManager().addCard(nariaCard);
        pb.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        int initialYear = 1;
        assertEquals(initialYear, player.getFacadeIA().getYearF());
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(8);
        int monthExpected = 8;
        int yearExpected = 3;
        assertEquals(monthExpected, player.getFacadeIA().getMonthF());
        assertEquals(yearExpected, player.getFacadeIA().getYearF());

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoice);
        assertNull(cardChosen);

    }

    @Test
    void testNariaInvocationModerateThirdYearFail2() {
        player.setGameplayStrategy(new NariaInvocationModerateThirdYear(endStrat));

        //Cas où la srtatégie ne marche pas
        Card nariaCard = new Card("Naria", 12, 0, 0, new int[] {1,1,1,1}, Type.FAMILIAR);
        pb.getCardManager().addCard(nariaCard);
        pb.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        int initialYear = 1;
        assertEquals(initialYear, player.getFacadeIA().getYearF());
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(8);
        int monthExpected = 8;
        int yearExpected = 2;
        assertEquals(monthExpected, player.getFacadeIA().getMonthF());
        assertEquals(yearExpected, player.getFacadeIA().getYearF());

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoice);
        assertNull(cardChosen);

    }
}