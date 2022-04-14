package com.game.playerassets.ia.strategy.gameplay.combo;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.AmsungEffect;
import com.game.engine.effects.effects.LifeEffect;
import com.game.engine.effects.effects.MaliceEffect;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Combo;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComboTest {

    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
    }

    @Test
    void GrismineLifeMegaCombo() {
        PersonalBoard richest = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1);
        richest.getEnergyManager().addEnergy(EnergyType.FIRE, 5);

        player.setGameplayStrategy(new GrismineLifeMegaCombo(endStrat));
        Card grismine = new Card("grimsine", 21, 0, 0, new int[4], Type.FAMILIAR);
        Card life = new Card("life", 26, 0, 0, new int[4], Type.FAMILIAR);
        LifeEffect emptyEffect = new LifeEffect("test", EffectType.DEFAULT, engine);
        life.setEffect(emptyEffect);
        grismine.setEffect(emptyEffect);

        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        pb.getCardManager().addCard(grismine);
        pb.getCardManager().getInvokeDeck().addCard(life);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);

        GameplayChoice choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.COMBO, choice);

        Combo combo = player.getFacadeIA().getComboToUse();
        assertEquals(grismine, combo.getCardQueue().poll());
        assertEquals(life, combo.getCardQueue().poll());

        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
        assertEquals(GameplayChoice.ACTIVATE, combo.getGameplayChoices().poll());
    }

    @Test
    void testAmsugMaliceCombo() {   //Test the case when malice is already in game
        player.setGameplayStrategy(new AmsugMaliceCombo(endStrat));

        Card amsug = new Card("amsug", 17, 0, 0, new int[4], Type.FAMILIAR);
        Card malice = new Card("malice", 15, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate amsugEffect = new AmsungEffect("effect", EffectType.DEFAULT, engine);
        EffectTemplate maliceEffect = new MaliceEffect("maliceEffect", EffectType.DEFAULT, engine);
        amsug.setEffect(amsugEffect);
        malice.setEffect(maliceEffect);

        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        pb.getCardManager().addCard(amsug);
        pb.getCardManager().getInvokeDeck().addCard(malice);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);

        GameplayChoice choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.COMBO, choice);

        Combo combo = player.getFacadeIA().getComboToUse();
        assertEquals(amsug, combo.getCardQueue().poll());
        assertEquals(malice, combo.getCardQueue().poll());

        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
    }

    @Test
    void testAmsugMaliceCombo2() {   //Test the case when malice is in hand
        player.setGameplayStrategy(new AmsugMaliceCombo(endStrat));

        Card amsug = new Card("amsug", 17, 0, 0, new int[4], Type.FAMILIAR);
        Card malice = new Card("malice", 15, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate amsugEffect = new AmsungEffect("effect", EffectType.DEFAULT, engine);
        EffectTemplate maliceEffect = new MaliceEffect("maliceEffect", EffectType.DEFAULT, engine);
        amsug.setEffect(amsugEffect);
        malice.setEffect(maliceEffect);

        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        pb.getCardManager().addCard(amsug);
        pb.getCardManager().addCard(malice);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);

        GameplayChoice choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.COMBO, choice);

        Combo combo = player.getFacadeIA().getComboToUse();
        assertEquals(malice, combo.getCardQueue().poll());
        assertEquals(amsug, combo.getCardQueue().poll());
        assertEquals(malice, combo.getCardQueue().poll());

        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
        assertEquals(GameplayChoice.INVOKE, combo.getGameplayChoices().poll());
    }
}