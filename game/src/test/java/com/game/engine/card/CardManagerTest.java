package com.game.engine.card;

import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.EmptyEffect;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.utils.Util;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardManagerTest {
    Card c;
    ArrayList<Card> cards;
    PersonalBoard p;
    int value = 10;
    int number = 1;
    int[] energies = {0, 0, 2, 0};
    int initialEnergy = 4;
    GameEngine engine;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(2);
        p = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        p.getEnergyManager().addEnergy(EnergyType.FIRE, initialEnergy);
        c = new Card("test card", number, value, 0, energies, Type.OBJECT);
        c.setEffect(new EmptyEffect("empty", EffectType.DEFAULT, engine));
        p.getCardManager().addCard(c);
        p.getCardManager().getInvokeDeck().upInvocationGauge(1);
    }

    @Test
    void invoc() {
        int initialScore = p.getScore();
        assertEquals(initialScore, p.getScore());

        p.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(p.getPlayer().getFacadeIA()));
        assertEquals(initialEnergy - energies[EnergyType.FIRE.ordinal()], p.getEnergyManager().getAmountOfEnergyType(EnergyType.FIRE));
        //le score n'est plus ajouté après une invocation, mais à la fin de la partie.
        assertEquals(p.getScore(), initialScore);
    }

    @Test
    void drawCard() {
        int oldSize = p.getCardManager().getCards().size();
        Card c = p.getCardManager().drawCard(engine);
        assertEquals(oldSize + 1, p.getCardManager().getCards().size());
        assertTrue(p.getCardManager().getCards().contains(c));
    }

    @Test
    void drawCards() {
        int randomAmount = Util.getRandomInt(10);
        int oldSize = p.getCardManager().getCards().size();
        List<Card> c = p.getCardManager().drawCards(engine, randomAmount);
        p.getCardManager().addCards(c);
        for (Card card : c) {
            assertTrue(p.getCardManager().getCards().contains(card));
        }
        assertEquals(randomAmount, c.size());
        assertEquals(oldSize + randomAmount, p.getCardManager().getCards().size());
    }

    @Test
    void sacrificeCard() {
        Card c2 = new Card("test card 2", number, value, 0, energies, Type.OBJECT);
        c2.setEffect(new EmptyEffect("empty", EffectType.DEFAULT, engine));
        Card c3 = new Card("test card 3", number, value, 0, energies, Type.OBJECT);
        c3.setEffect(new EmptyEffect("empty", EffectType.DEFAULT, engine));
        p.getCardManager().invoke(c);
        p.getCardManager().invoke(c2);
        p.getCardManager().sacrificeCard(c3);
        assertEquals(p.getCardManager().getInvokeDeck().getCards().size(), 2);
        p.getCardManager().sacrificeCard(c);
        assertFalse(p.getCardManager().getInvokeDeck().getCards().contains(c));
    }
}