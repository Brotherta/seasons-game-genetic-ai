package com.game.playerassets.ia.strategy.gameplay.invocation.priority;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.EmptyEffect;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class PriorityTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(7);
    }

    @Test
    void PriorityPermanentInvocation() {
        player.setGameplayStrategy(new PriorityPermanentInvocation(endStrat));
        Card card1 = new Card("card1", 0, 0, 0, new int[4], Type.FAMILIAR);
        Card card2 = new Card("card2", 0, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate permanentEffect = new EmptyEffect("test", EffectType.DEFAULT, engine);
        EffectTemplate singleEffect = new EmptyEffect("test", EffectType.DEFAULT, engine);
        permanentEffect.setPermanentEffect(true);
        singleEffect.setSingleEffect(true);
        card1.setEffect(permanentEffect);
        card2.setEffect(singleEffect);

        // priority set to the permanent effect
        pb.getCardManager().addCard(card1);
        pb.getCardManager().addCard(card2);
        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(card1, player.getFacadeIA().getCardToUseF());
        assertEquals(GameplayChoice.INVOKE, gc);

        // no priority found
        pb.getCardManager().getCards().remove(card1);
        gc = player.makeGameplayChoice();
        assertNull(player.getFacadeIA().getCardToUseF());
        assertNull(gc);
    }
}
