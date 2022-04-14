package com.game.playerassets.ia.strategy.returnInHand;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.FireEffect;
import com.game.engine.effects.effects.IshtarEffect;
import com.game.engine.effects.effects.SpringEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnInHandStrategyTest {

    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setup() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(15);
    }

    @Test
    void testReturnInHandLessExpensive() {
        Card fireCard = new Card("fire amulet", 2, 6, 0,  new int[] {0,0,2,0}, Type.OBJECT);
        EffectTemplate fireEffect = new FireEffect("fire effect", EffectType.DEFAULT, engine);
        fireCard.setEffect(fireEffect);
        Card ishtarCard = new Card("ishtar", 5,4, 0, new int[] {0,0,0,0}, Type.OBJECT);
        EffectTemplate ishtarEffect = new IshtarEffect("ishtar effect", EffectType.DEFAULT, engine);
        ishtarCard.setEffect(ishtarEffect);

        player.setReturnInHandStrategy(new ReturnInHandLessExpensive(endStrat));

        pb.getCardManager().getInvokeDeck().addCard(fireCard);
        pb.getCardManager().getInvokeDeck().addCard(ishtarCard);

        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getInvokeDeck().getCards());

        Card cardChosen = player.chooseCardToReturnInHand();
        assertEquals(ishtarCard, cardChosen);
    }

    @Test
    void testReturnInHandSingleEffect() {
        Card fireCard = new Card("fire amulet", 2, 6, 0,  new int[] {0,0,2,0}, Type.OBJECT);
        EffectTemplate fireEffect = new FireEffect("fire effect", EffectType.DEFAULT, engine);
        fireCard.setEffect(fireEffect);
        Card springCard = new Card("staff of spring", 6,9, 0, new int[] {0,0,0,3}, Type.OBJECT);
        EffectTemplate springEffect = new SpringEffect("spring effect", EffectType.DEFAULT, engine);
        springCard.setEffect(springEffect);
        pb.getCardManager().getInvokeDeck().addCard(fireCard);
        pb.getCardManager().getInvokeDeck().addCard(springCard);

        player.setReturnInHandStrategy(new ReturnInHandSingleEffect(endStrat));
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getInvokeDeck().getCards());

        Card cardChosen = player.chooseCardToReturnInHand();
        assertEquals(fireCard, cardChosen);
    }

    @Test
    void testReturnInHandSingleEffectFail() {

        player.setReturnInHandStrategy(new ReturnInHandSingleEffect(endStrat));

        Card springCard = new Card("staff of spring", 6,9, 0, new int[] {0,0,0,3}, Type.OBJECT);
        EffectTemplate springEffect = new SpringEffect("spring effect", EffectType.INVOKE, engine);
        springCard.setEffect(springEffect);

        pb.getCardManager().getInvokeDeck().addCard(springCard);

        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getInvokeDeck().getCards());

        Card cardChosen = player.chooseCardToReturnInHand();
        assertNull(cardChosen);
    }
}