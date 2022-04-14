package com.game.playerassets.ia.strategy.gameplay.activation;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.*;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.ishtar.IshtarActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.ishtar.IshtarActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.knowledge.KnowledgeActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.knowledge.KnowledgeActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationRetreat;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivationStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);
    }

    @Test
    void testIshtarModerateActivation() {
        player.setGameplayStrategy(new IshtarActivationModerate(endStrat));
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 3);
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        cardFire.setEffect(new EmptyEffect("test", EffectType.DEFAULT, engine));
        ishtar.setEffect(effect);
        effect.setCard(ishtar);

        pb.getCardManager().addCard(cardFire);

        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertNull(cardChosen);

        pb.getCardManager().reset();

        pb.getCardManager().getInvokeDeck().addCard(ishtar);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        player.makeGameplayChoice();
        cardChosen = player.getFacadeIA().getCardToUseF();
        assertEquals(ishtar, cardChosen);
    }

    @Test
    void testIshtarRetreatActivation() {
        engine.getBoard().timeForward(6);
        player.setGameplayStrategy(new IshtarActivationRetreat(endStrat));
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 3);
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        cardFire.setEffect(new EmptyEffect("test", EffectType.DEFAULT, engine));
        ishtar.setEffect(effect);
        effect.setCard(ishtar);

        pb.getCardManager().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        // This time the energies are involved in invocation or activation of any cards.
        GameplayChoice gc = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertNull(cardChosen);
        assertNull(gc);

        pb.getCardManager().reset();

        // This time the energies are not involved in invocation or activation of any cards.
        pb.getCardManager().getInvokeDeck().addCard(ishtar);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        gc = player.makeGameplayChoice();
        cardChosen = player.getFacadeIA().getCardToUseF();
        assertEquals(ishtar, cardChosen);
        assertEquals(GameplayChoice.ACTIVATE, gc);

        // This time the energies are useful to crystallize next season
        engine.getBoard().timeForward(6);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        gc = player.makeGameplayChoice();
        cardChosen = player.getFacadeIA().getCardToUseF();
        assertNull(cardChosen);
        assertNull(gc);
    }

    @Test
    void testIshtarAggressiveActivation() {
        player.setGameplayStrategy(new IshtarActivationModerate(endStrat));
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        ishtar.setEffect(effect);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        assertNull(gameplayChoice); // the player don't have enough energies.

        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 3);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        gameplayChoice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);
        Card chosenOne = player.getFacadeIA().getCardToUseF();
        assertEquals(ishtar, chosenOne);
    }

    @Test
    void actPowerPotionActivationAggressive() {
        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        player.setGameplayStrategy(new PowerPotionActivationAggressive(endStrat));
        Card c = new Card("PowerPotion", 23, 0, 0, new int[4], Type.OBJECT);
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate e = new PowerEffect("PowerPotion", EffectType.DEFAULT, engine);
        EffectTemplate effectIshtar = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        EffectTemplate effectFire = new IshtarEffect("Fire", EffectType.DEFAULT, engine);
        cardFire.setEffect(effectFire);
        effectFire.setCard(cardFire);
        ishtar.setEffect(effectIshtar);
        effectIshtar.setCard(ishtar);
        c.setEffect(e);
        e.setCard(c);

        pb.getCardManager().getInvokeDeck().addCard(c);
        pb.getCardManager().getInvokeDeck().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gc = player.makeGameplayChoice();
        Card cardchosen = player.getFacadeIA().getCardToUseF();
        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(c, cardchosen);
    }

    @Test
    void actPowerPotionActivationAggressiveButItFails() {
        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        player.setGameplayStrategy(new PowerPotionActivationAggressive(endStrat));
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effectIshtar = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        EffectTemplate effectFire = new IshtarEffect("Fire", EffectType.DEFAULT, engine);
        cardFire.setEffect(effectFire);
        effectFire.setCard(cardFire);
        ishtar.setEffect(effectIshtar);
        effectIshtar.setCard(ishtar);

        pb.getCardManager().getInvokeDeck().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);
    }

    @Test
    void actPowerPotionActivationModerate() {
        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        player.setGameplayStrategy(new PowerPotionActivationAggressive(endStrat));
        Card c = new Card("PowerPotion", 23, 0, 0, new int[4], Type.OBJECT);
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate e = new PowerEffect("PowerPotion", EffectType.DEFAULT, engine);
        EffectTemplate effectIshtar = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        EffectTemplate effectFire = new IshtarEffect("Fire", EffectType.DEFAULT, engine);
        cardFire.setEffect(effectFire);
        effectFire.setCard(cardFire);
        ishtar.setEffect(effectIshtar);
        effectIshtar.setCard(ishtar);
        c.setEffect(e);
        e.setCard(c);

        pb.getCardManager().getInvokeDeck().addCard(c);
        pb.getCardManager().getInvokeDeck().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gc = player.makeGameplayChoice();
        Card cardchosen = player.getFacadeIA().getCardToUseF();
        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(c, cardchosen);


    }

    @Test
    void actPowerPotionActivationModerateButItFails() {
        pb.getCardManager().getInvokeDeck().upInvocationGauge(4);
        player.setGameplayStrategy(new PowerPotionActivationModerate(endStrat));
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        Card c = new Card("PowerPotion", 23, 0, 0, new int[4], Type.OBJECT);

        EffectTemplate effectIshtar = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        EffectTemplate effectFire = new IshtarEffect("Fire", EffectType.DEFAULT, engine);
        EffectTemplate e = new PowerEffect("PowerPotion", EffectType.DEFAULT, engine);

        cardFire.setEffect(effectFire);
        effectFire.setCard(cardFire);
        ishtar.setEffect(effectIshtar);
        effectIshtar.setCard(ishtar);
        c.setEffect(e);
        e.setCard(c);

        pb.getCardManager().getInvokeDeck().addCard(c);
        pb.getCardManager().getInvokeDeck().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);
    }

    @Test
    void actPowerPotionActivationRetreat() {
        pb.getCardManager().getInvokeDeck().upInvocationGauge(3);
        player.setGameplayStrategy(new PowerPotionActivationRetreat(endStrat));
        Card c = new Card("PowerPotion", 23, 0, 0, new int[4], Type.OBJECT);
        Card cardFire = new Card("test", 55, 0, 0, new int[]{0, 0, 3, 0}, Type.OBJECT);
        Card ishtar = new Card("Ishtar", 5, 0, 0, new int[4], Type.OBJECT);
        Card cardInvokable = new Card("Invokable", 99, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effectRandom = new TemporalEffect("temp", EffectType.DEFAULT, engine);
        EffectTemplate e = new PowerEffect("PowerPotion", EffectType.DEFAULT, engine);
        EffectTemplate effectIshtar = new IshtarEffect("Ishtar", EffectType.DEFAULT, engine);
        EffectTemplate effectFire = new IshtarEffect("Fire", EffectType.DEFAULT, engine);

        cardFire.setEffect(effectFire);
        effectFire.setCard(cardFire);
        ishtar.setEffect(effectIshtar);
        effectIshtar.setCard(ishtar);
        c.setEffect(e);
        e.setCard(c);

        cardInvokable.setEffect(effectRandom);
        effectRandom.setCard(cardInvokable);

        pb.getCardManager().addCard(cardInvokable);

        pb.getCardManager().getInvokeDeck().addCard(c);
        pb.getCardManager().getInvokeDeck().addCard(cardFire);
        pb.getCardManager().getInvokeDeck().addCard(ishtar);

        GameplayChoice gc = player.makeGameplayChoice();
        Card cardchosen = player.getFacadeIA().getCardToUseF();
        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(c, cardchosen);


        // cette fois si nous ne disposons pas de carte à invoquer la stratégie ne s'effectue pas
        pb.getCardManager().getCards().clear();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gc2 = player.makeGameplayChoice();
        assertNull(gc2);

    }

    @Test
    void testLifePotionActivationAggressive() {
        player.setGameplayStrategy(new LifePotionActivationAggressive(endStrat));


        Card lifePotion = new Card("lifePotion", 26, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new LifeEffect("Life", EffectType.DEFAULT, engine);
        lifePotion.setEffect(effect);
        effect.setCard(lifePotion);

        // Cas où la stratégie marche
        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);

        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertEquals(lifePotion, cardChosen);

        // Cas où la stratégie ne marche pas car le joueur n'a pas d'énergies
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailEnergies = player.makeGameplayChoice();

        Card cardChosenFailEnergies = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailEnergies);
        assertNull(cardChosenFailEnergies);


        // Cas où la stratégie ne marche pas car le joueur n'a pas la carte dans ses cartes activables
        pb.getCardManager().reset();
        pb.getEnergyManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});
        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailCard = player.makeGameplayChoice();

        Card cardChosenFailCard = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailCard);
        assertNull(cardChosenFailCard);
    }

    @Test
    void testLifePotionActivationModerate() {
        player.setGameplayStrategy(new LifePotionActivationModerate(endStrat));


        Card lifePotion = new Card("lifePotion", 26, 0, 0, new int[4], Type.OBJECT);
        Card usefulCard = new Card("useful", 66, 0, 0, new int[]{1, 0, 1, 0}, Type.OBJECT);
        EffectTemplate effect = new LifeEffect("Life", EffectType.DEFAULT, engine);
        EffectTemplate fakeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        lifePotion.setEffect(effect);
        effect.setCard(lifePotion);
        usefulCard.setEffect(fakeEffect);

        // Cas où la stratégie marche
        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);

        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertEquals(lifePotion, cardChosen);

        // Cas où la stratégie ne marche pas car le joueur n'a pas assez d'énergies
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 0, 1, 0});
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailEnergies = player.makeGameplayChoice();

        Card cardChosenFailEnergies = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailEnergies);
        assertNull(cardChosenFailEnergies);

        // Cas où la stratégie ne marche pas car le joueur n'a pas la carte dans ses cartes activables
        pb.getCardManager().reset();
        pb.getEnergyManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailCard = player.makeGameplayChoice();

        Card cardChosenFailCard = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailCard);
        assertNull(cardChosenFailCard);

        // Cas où la stratégie ne marche pas car le joueur a en main une carte nécessitant un des types d'énergies du stock

        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});
        pb.getCardManager().addCard(usefulCard);
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailUseFulEnergies = player.makeGameplayChoice();

        Card cardChosenFailUsefulEnergies = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailUseFulEnergies);
        assertNull(cardChosenFailUsefulEnergies);
    }

    @Test
    void testLifePotionActivationRetreat() {
        player.setGameplayStrategy(new LifePotionActivationRetreat(endStrat));

        Card lifePotion = new Card("lifePotion", 26, 0, 0, new int[4], Type.OBJECT);
        Card usefulCard = new Card("useful", 66, 0, 0, new int[]{1, 0, 1, 0}, Type.OBJECT);
        EffectTemplate effect = new LifeEffect("Life", EffectType.DEFAULT, engine);
        EffectTemplate fakeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        lifePotion.setEffect(effect);
        effect.setCard(lifePotion);
        usefulCard.setEffect(fakeEffect);

        // Tests SANS le grimoire donc le joueur a un stock de 7

        // Cas où la stratégie marche
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getEnergyManager().addEnergy(new int[]{2, 2, 2, 1});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);

        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertEquals(lifePotion, cardChosen);

        //Cas où la stratégie ne marche pas car le joueur n'a pas assez d'énergies
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 0, 1, 0});
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailEnergies = player.makeGameplayChoice();

        Card cardChosenFailEnergies = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailEnergies);
        assertNull(cardChosenFailEnergies);

        // Cas où la stratégie ne marche pas car le joueur n'a pas la carte dans ses cartes activables
        pb.getCardManager().reset();
        pb.getEnergyManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{2, 2, 2, 1});

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailCard = player.makeGameplayChoice();

        Card cardChosenFailCard = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailCard);
        assertNull(cardChosenFailCard);

        // Cas où la stratégie ne marche pas car le joueur a en main une carte nécessitant un des types d'énergies du stock
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{2, 2, 2, 1});
        pb.getCardManager().addCard(usefulCard);
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailUseFulEnergies = player.makeGameplayChoice();

        Card cardChosenFailUsefulEnergies = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailUseFulEnergies);
        assertNull(cardChosenFailUsefulEnergies);
    }

    @Test
    void testLifePotionActivationRetreatGrimoir() {
        player.setGameplayStrategy(new LifePotionActivationRetreat(endStrat));

        Card lifePotion = new Card("lifePotion", 26, 0, 0, new int[4], Type.OBJECT);
        Card grimoir = new Card("grimoir", 18, 0, 0, new int[4], Type.OBJECT);
        Card usefulCard = new Card("useful", 66, 0, 0, new int[]{1, 0, 1, 0}, Type.OBJECT);
        EffectTemplate effect = new LifeEffect("Life", EffectType.DEFAULT, engine);
        EffectTemplate grimoirEffect = new GrimoirEffect("grimoir", EffectType.DEFAULT, engine);
        EffectTemplate fakeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        lifePotion.setEffect(effect);
        effect.setCard(lifePotion);
        grimoir.setEffect(grimoirEffect);
        usefulCard.setEffect(fakeEffect);

        // Test AVEC le grimoire donc le joueur a un stock de 10

        // Cas où la stratégie marche
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getCardManager().getInvokeDeck().addCard(grimoir);
        pb.getCardManager().getInvokeDeck().getCards().get(1).getEffect().applyEffect(engine, player);

        pb.getEnergyManager().addEnergy(new int[]{3, 3, 2, 0});

        GameplayChoice gameplayChoice = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);

        Card cardChosenGrimoir = player.getFacadeIA().getCardToUseF();
        assertEquals(lifePotion, cardChosenGrimoir);

        //Cas où la stratégie ne marche pas car le joueur n'a pas assez d'énergies
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getEnergyManager().addEnergy(new int[]{1, 0, 1, 0});
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getCardManager().getInvokeDeck().addCard(grimoir);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailEnergiesGrimoir = player.makeGameplayChoice();

        Card cardChosenFailEnergiesGrimoir = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailEnergiesGrimoir);
        assertNull(cardChosenFailEnergiesGrimoir);

        // Cas où la stratégie ne marche pas car le joueur n'a pas la carte dans ses cartes activables
        pb.getCardManager().reset();
        pb.getEnergyManager().reset();

        pb.getCardManager().getInvokeDeck().addCard(grimoir);
        pb.getEnergyManager().addEnergy(new int[]{3, 3, 3, 1});

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailCardGrimoir = player.makeGameplayChoice();

        Card cardChosenFailCardGrimoir = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailCardGrimoir);
        assertNull(cardChosenFailCardGrimoir);

        // Cas où la stratégie ne marche pas car le joueur a en main une carte nécessitant un des types d'énergies du stock
        pb.getEnergyManager().reset();
        pb.getCardManager().reset();

        pb.getCardManager().getInvokeDeck().addCard(grimoir);
        pb.getCardManager().getInvokeDeck().addCard(lifePotion);
        pb.getCardManager().addCard(usefulCard);
        pb.getEnergyManager().addEnergy(new int[]{3, 3, 3, 1});

        pb.getPlayerTurnManager().getConflictTable().initTable();
        GameplayChoice gameplayChoiceFailUseFulEnergiesGrimoir = player.makeGameplayChoice();

        Card cardChosenFailUsefulEnergiesGrimoir = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFailUseFulEnergiesGrimoir);
        assertNull(cardChosenFailUsefulEnergiesGrimoir);
    }

    @Test
    void testDreamPotionActivationAggressive() {
        player.setGameplayStrategy(new DreamPotionActivationAggressive(endStrat));
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);

        Card dreamPotion = new Card("dreamPotion", 24, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new DreamEffect("dream", EffectType.DEFAULT, engine);
        dreamPotion.setEffect(effect);
        effect.setCard(dreamPotion);
        Card freeCard = new Card("freeCard", 55, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate freeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        freeCard.setEffect(freeEffect);

        // Cas où la stratégie marche

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);
        pb.getCardManager().addCard(freeCard);

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);
        assertEquals(dreamPotion, cardChosen);

        // Cas où la stratégie ne marche pas car pas de cartes en main

        pb.getCardManager().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();
        Card cardChosenFail = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFail);
        assertNull(cardChosenFail);

    }

    @Test
    void testDreamPotionActivationModerate() {
        player.setGameplayStrategy(new DreamPotionActivationModerate(endStrat));
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);

        Card dreamPotion = new Card("dreamPotion", 24, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new DreamEffect("dream", EffectType.DEFAULT, engine);
        dreamPotion.setEffect(effect);
        effect.setCard(dreamPotion);
        Card freeCard = new Card("freeCard", 55, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate freeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        freeCard.setEffect(freeEffect);

        // Cas où la stratégie marche

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);
        pb.getCardManager().addCard(freeCard);

        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);
        assertEquals(dreamPotion, cardChosen);

        // Cas où la stratégie ne marche pas car pas de cartes en main

        pb.getCardManager().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();
        Card cardChosenFail = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFail);
        assertNull(cardChosenFail);

        // Cas où la stratégie ne marche pas car énergies utiles pour un coup d'activation
        pb.getCardManager().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(2);

        Card kairn = new Card("kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate kairnEffect = new KairnEffect("kairnEffect", EffectType.DEFAULT, engine);
        kairn.setEffect(kairnEffect);

        pb.getCardManager().addCard(freeCard);
        pb.getCardManager().getInvokeDeck().addCard(kairn);
        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);
        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        GameplayChoice gameplayChoiceFail2 = player.makeGameplayChoice();
        Card cardChosenFail2 = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFail2);
        assertNull(cardChosenFail2);
    }

    @Test
    void testDreamPotionActivationRetreat() {
        player.setGameplayStrategy(new DreamPotionActivationRetreat(endStrat));
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);

        Card dreamPotion = new Card("dreamPotion", 24, 0, 0, new int[4], Type.OBJECT);
        EffectTemplate effect = new DreamEffect("dream", EffectType.DEFAULT, engine);
        dreamPotion.setEffect(effect);
        effect.setCard(dreamPotion);
        Card freeCard = new Card("freeCard", 55, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate freeEffect = new EmptyEffect("empty", EffectType.DEFAULT, engine);
        freeCard.setEffect(freeEffect);

        // Cas où la stratégie marche

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);
        pb.getCardManager().addCard(freeCard);
        GameplayChoice gameplayChoice = player.makeGameplayChoice();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertEquals(GameplayChoice.ACTIVATE, gameplayChoice);
        assertEquals(dreamPotion, cardChosen);

        // Cas où la stratégie ne marche pas car pas de cartes en main

        pb.getCardManager().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);

        GameplayChoice gameplayChoiceFail = player.makeGameplayChoice();
        Card cardChosenFail = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFail);
        assertNull(cardChosenFail);

        //Cas où la stratégie ne marche pas car le joueur a des énergies

        pb.getCardManager().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);

        pb.getCardManager().getInvokeDeck().addCard(dreamPotion);
        pb.getCardManager().addCard(freeCard);
        pb.getEnergyManager().addEnergy(new int[]{1, 1, 1, 1});

        GameplayChoice gameplayChoiceFail2 = player.makeGameplayChoice();
        Card cardChosenFail2 = player.getFacadeIA().getCardToUseF();

        assertNull(gameplayChoiceFail2);
        assertNull(cardChosenFail2);
    }

    @Test
    void actKairnActivationAggressive() {
        player.setGameplayStrategy(new KairnActivationAggressive(endStrat));
        Card card = new Card("Kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KairnEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        pb.getEnergyManager().addEnergy(EnergyType.WATER, 4);
        GameplayChoice gc = player.makeGameplayChoice();

        Card chosen = player.getFacadeIA().getCardToUseF();

        int[] energyChosen = player.getFacadeIA().getChoosableEnergiesF();

        int[] energyExpected = new int[EnergyType.values().length];

        energyExpected[EnergyType.WATER.ordinal()] = 1;

        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(card, chosen);
        assertArrayEquals(energyExpected, energyChosen);

// this time the strategy fails
        pb.getCardManager().getInvokeDeck().getCards().remove(card);
        gc = player.makeGameplayChoice();
        assertNull(gc);
    }

    @Test
    void actKairnActivationModerate() {
        player.setGameplayStrategy(new KairnActivationModerate(endStrat));
        Card card = new Card("Kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KairnEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);
        int[] cost = new int[EnergyType.values().length];
        cost[EnergyType.WATER.ordinal()] = 4;

        Card cardInvokable = new Card("CARD-TEST", 404, 100, 0, cost, Type.OBJECT);

        pb.getCardManager().addCard(cardInvokable);
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        pb.getEnergyManager().addEnergy(EnergyType.WATER, 4);

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);


    }

    @Test
    void actKairnActivationModerateItWorks() {
        player.setGameplayStrategy(new KairnActivationModerate(endStrat));
        Card card = new Card("Kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KairnEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);

        pb.getCardManager().getInvokeDeck().getCards().add(card);
        pb.getEnergyManager().addEnergy(EnergyType.WATER, 4);

        int[] active = new int[EnergyType.values().length];
        active[EnergyType.WATER.ordinal()] = 1;
        GameplayChoice gc = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gc);

        int[] energychosen = player.getFacadeIA().getChoosableEnergiesF();
        Card cardChosen = player.getFacadeIA().getCardToUseF();
        assertArrayEquals(active, energychosen);
        assertEquals(card, cardChosen);

    }

    @Test
    void actKairnActivationRetreat() {
        player.setGameplayStrategy(new KairnActivationRetreat(endStrat));
        Card card = new Card("Kairn", 16, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KairnEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);

        pb.getCardManager().getInvokeDeck().getCards().add(card);
        pb.getEnergyManager().addEnergy(EnergyType.EARTH, 2);
        pb.getEnergyManager().addEnergy(EnergyType.WATER, 2);
        pb.getEnergyManager().addEnergy(EnergyType.AIR, 2);

        int[] cost = new int[EnergyType.values().length];
        cost[EnergyType.WATER.ordinal()] = 2;
        Card cardInvokable = new Card("CARD-TEST", 404, 100, 0, cost, Type.OBJECT);

        pb.getCardManager().addCard(cardInvokable);
        int[] active = new int[EnergyType.values().length];
        active[EnergyType.AIR.ordinal()] = 1; // énergie la moins chere ne servant pas a l'invocation d'une carte
        GameplayChoice gc = player.makeGameplayChoice();

        assertEquals(GameplayChoice.ACTIVATE, gc);

        int[] energychosen = player.getFacadeIA().getChoosableEnergiesF();
        Card cardChosen = player.getFacadeIA().getCardToUseF();

        assertArrayEquals(active, energychosen);
        assertEquals(card, cardChosen);
    }

    @Test
    public void KnowledgeActivationModerate() {
        player.setGameplayStrategy(new KnowledgeActivationModerate(endStrat));
        Card card = new Card("Knowledge", 25, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KnowledgeEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);
        Card cardCost = new Card("cost", 1, 0, 0, new int[]{2, 0, 0, 0}, Type.FAMILIAR);

        // The player don't have cards that cost energies.
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

        // The player have a card that cost 2 fires.
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getCards().add(cardCost);
        gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(card, player.getFacadeIA().getCardToUseF());

        // The player have already some energies in stock (at least 5).
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 5);
        gc = player.makeGameplayChoice();
        assertNull(gc);
    }

    @Test
    public void KnowledgeActivationRetreat() {
        player.setGameplayStrategy(new KnowledgeActivationRetreat(endStrat));
        Card card = new Card("Knowledge", 25, 0, 0, new int[4], Type.FAMILIAR);
        EffectTemplate e = new KnowledgeEffect(card.getName(), EffectType.DEFAULT, engine);
        e.setCard(card);
        card.setEffect(e);
        Card cardCost = new Card("cost", 1, 0, 0, new int[]{2, 0, 0, 0}, Type.FAMILIAR);

        // The player don't have cards that cost energies.
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

        // The player have a card that cost 2 fires.
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getCardManager().getCards().add(cardCost);
        gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.ACTIVATE, gc);
        assertEquals(card, player.getFacadeIA().getCardToUseF());

        // We are at the third year.
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        pb.getPlayerTurnManager().getConflictTable().initTable();
        gc = player.makeGameplayChoice();
        assertNull(gc);

        // The player have already some energies in stock (at least 5).
        engine.getBoard().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 5);
        gc = player.makeGameplayChoice();
        assertNull(gc);
    }
}
