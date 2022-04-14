package com.game.playerassets.ia.strategy.sacrificeenergy;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.FireEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SacrificeEnergiesTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setup() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
    }

    @Test
    void testSacrificeWorthLessToSell() {
        player.setSacrificeEnergyStrategy(new SacrificeWorthLessToSell(endStrat));

        pb.getEnergyManager().addEnergy(new int[] {1,2,0,3});
        player.getFacadeIA().setChoosableEnergiesF(pb.getEnergyManager().getAmountOfEnergiesArray(), 3);

        int[] energiesToSacrifice = player.chooseEnergiesToSacrifice();

        assertArrayEquals(new int[] {1,2,0,0}, energiesToSacrifice);
    }

    @Test
    void testSacrificeWorthLessToSellFail() {
        player.setSacrificeEnergyStrategy(new SacrificeWorthLessToSell(endStrat));

        pb.getEnergyManager().addEnergy(new int[] {0,0,1,2});
        player.getFacadeIA().setChoosableEnergiesF(pb.getEnergyManager().getAmountOfEnergiesArray(), 3);

        int[] energiesToSacrifice = player.chooseEnergiesToSacrifice();

        assertNull(energiesToSacrifice);
    }

    @Test
    void testSacrificeUselessForInvoke() {
        player.setSacrificeEnergyStrategy(new SacrificeUselessForInvoke(endStrat));

        Card fire = new Card("fire amulet", 2, 6, 0, new int[] {0,0,2,0}, Type.OBJECT);
        EffectTemplate fireEffect = new FireEffect("fire effect", EffectType.DEFAULT, engine);
        fire.setEffect(fireEffect);
        pb.getCardManager().addCard(fire);
        pb.getEnergyManager().addEnergy(new int[] {1,1,2,1});

        player.getFacadeIA().setChoosableEnergiesF(pb.getEnergyManager().getAmountOfEnergiesArray(), 3);

        int[] energiesToSacrifice = player.chooseEnergiesToSacrifice();

        assertArrayEquals(new int[] {1,1,0,1}, energiesToSacrifice);
    }

    @Test
    void testSacrificeUselessForInvokeFail() {
        player.setSacrificeEnergyStrategy(new SacrificeUselessForInvoke(new SacrificeWorthLessToSell(endStrat)));

        Card fire = new Card("fire amulet", 2, 6, 0, new int[]{0, 1, 1, 0}, Type.OBJECT);
        EffectTemplate fireEffect = new FireEffect("fire effect", EffectType.DEFAULT, engine);
        fire.setEffect(fireEffect);
        pb.getCardManager().addCard(fire);
        pb.getEnergyManager().addEnergy(new int[]{2, 1, 1, 0});

        player.getFacadeIA().setChoosableEnergiesF(pb.getEnergyManager().getAmountOfEnergiesArray(), 3);

        int[] energiesToSacrifice = player.chooseEnergiesToSacrifice();

        assertArrayEquals(new int[] {2,1,0,0}, energiesToSacrifice);
    }
}