package com.game.engine.effects;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractEffectTest {
    GameEngine engine;
    PersonalBoard p;
    Card c = new Card("test card",0, 0, 0, new int[]{0,0,0,0}, Type.OBJECT);
    int initialEnergy = 4;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);

        p = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        p.getEnergyManager().addEnergy(EnergyType.FIRE, initialEnergy);
        p.getCardManager().addCard(c);
    }

    @Test
    void applyEffect() {
        UtilEffect.drawCards(engine, p.getPlayer(), 1);
        assertEquals(2, p.getCardManager().getCards().size());
        UtilEffect.gainCrystals(engine, p.getPlayer(), 1);
        assertEquals(1, p.getCrystal());
        UtilEffect.gainEnergies(engine, p.getPlayer(), 1);
        assertEquals(5, p.getEnergyManager().getAmountofEnergies());
    }
}