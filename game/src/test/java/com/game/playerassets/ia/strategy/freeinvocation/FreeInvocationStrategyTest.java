package com.game.playerassets.ia.strategy.freeinvocation;

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

class FreeInvocationStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);
    Card cost_1E_1C_W9;
    Card cost_2E_2C_W3;
    Card cost_3E_3C_W1;
    Card cost_4E_0C_W1;
    Card cost_0E_0C_W1;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);
        cost_1E_1C_W9 = new Card("cost1worth6", 0, 9, 1, new int[]{0, 0, 1, 0}, Type.FAMILIAR);
        cost_2E_2C_W3 = new Card("cost2worth3", 0, 3, 2, new int[]{0, 0, 1, 1}, Type.FAMILIAR);
        cost_3E_3C_W1 = new Card("cost3worth1", 0, 1, 3, new int[]{1, 1, 1, 0}, Type.FAMILIAR);
        cost_4E_0C_W1 = new Card("cost3worth1", 0, 1, 0, new int[]{1, 1, 1, 1}, Type.FAMILIAR);
        cost_0E_0C_W1 = new Card("cost3worth1", 0, 1, 0, new int[4], Type.FAMILIAR);
        pb.getCardManager().addCard(cost_1E_1C_W9);
        pb.getCardManager().addCard(cost_2E_2C_W3);
        pb.getCardManager().addCard(cost_3E_3C_W1);
        pb.getCardManager().addCard(cost_4E_0C_W1);
    }

    @Test
    void FreeMostExpensiveTest() {
        player.setInvocationFreeStrategy(new FreeMostExpensive(endStrat));
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        Card card = player.chooseCardToInvokeForFree();
        assertEquals(cost_4E_0C_W1, card);

        pb.getCardManager().getCards().clear();
        pb.getCardManager().addCard(cost_0E_0C_W1);
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        card = player.chooseCardToInvokeForFree();
        assertNull(card);
    }

    @Test
    void FreeMostPointCardTest() {
        player.setInvocationFreeStrategy(new FreeMostPointCard(endStrat));
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        Card card = player.chooseCardToInvokeForFree();
        assertEquals(cost_1E_1C_W9, card);
    }

    @Test
    void FreeMostExpensiveCrystalsTest() {
        player.setInvocationFreeStrategy(new FreeMostExpensiveCrystals(endStrat));
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        Card card = player.chooseCardToInvokeForFree();
        assertEquals(cost_3E_3C_W1, card);

        pb.getCardManager().getCards().clear();
        pb.getCardManager().addCard(cost_4E_0C_W1);
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        card = player.chooseCardToInvokeForFree();
        assertNull(card);
    }

    @Test
    void FreeEnergyAndCrystalsTest() {
        player.setInvocationFreeStrategy(new FreeEnergyAndCrystals(endStrat));
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        Card card = player.chooseCardToInvokeForFree();
        assertEquals(cost_3E_3C_W1, card);

        pb.getCardManager().getCards().clear();
        pb.getCardManager().addCard(cost_0E_0C_W1);
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());

        card = player.chooseCardToInvokeForFree();
        assertNull(card);
    }
}