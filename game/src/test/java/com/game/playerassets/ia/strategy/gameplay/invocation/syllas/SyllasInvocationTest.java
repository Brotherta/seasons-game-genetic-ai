package com.game.playerassets.ia.strategy.gameplay.invocation.syllas;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SyllasInvocationTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);
    Card initialCard;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        initialCard = new Card("Syllas", 10, 0, 0, new int[4], Type.FAMILIAR);
        pb.getCardManager().addCard(initialCard);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(7);
    }


    @Test
    void actSyllasInvocationModerate() {
        player.setGameplayStrategy(new SyllasInvocationModerate(endStrat));
        List<Player> others = player.getFacadeIA().getPlayersF();
        Card c = new Card("Card-test", 0, 0, 0, new int[4], Type.OBJECT);
        for (Player p : others) {
            PersonalBoard personal = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(p.getNumPlayer());
            personal.getCardManager().getInvokeDeck().upInvocationGauge(1);
            personal.getCardManager().getInvokeDeck().addCard(c);
        }

        GameplayChoice gc = player.makeGameplayChoice();

        assertEquals(GameplayChoice.INVOKE, gc);

        Card chosen = player.getFacadeIA().getCardToUseF();

        assertEquals(initialCard, chosen);

    }

    @Test
    void actSyllasInvocationModerateButItFails() {
        player.setGameplayStrategy(new SyllasInvocationModerate(endStrat));
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }
    @Test
    void actSyllasInvocationAggressive() {
        player.setGameplayStrategy(new SyllasInvocationAggressive(endStrat));
        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE, gc);
        Card chosen = player.getFacadeIA().getCardToUseF();
        assertEquals(initialCard, chosen);
    }
    @Test
    void actSyllasInvocationAggressiveButitFails() {
        player.setGameplayStrategy(new SyllasInvocationAggressive(endStrat));
        pb.getCardManager().getInvokeDeck().getCards().remove(initialCard);
        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE, gc);
        Card chosen = player.getFacadeIA().getCardToUseF();
        assertEquals(initialCard, chosen);
    }
}