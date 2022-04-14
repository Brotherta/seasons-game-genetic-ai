package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.engine.card.Card;
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

class ChangeTimeTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);
    Card temporalBoots = new Card("temporalBoots",7,0,0,new int[4], Type.OBJECT);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);

        pb.getCardManager().addCard(temporalBoots);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);
    }


    @Test
    void actChangeTimeRetreat() {
        /**
         * nous nous assurons d'être au mois 11 de l'année 3
         */

        int initialYear = 1;
        assertEquals(initialYear, player.getFacadeIA().getYearF());
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(11);
        int monthExpected = 11;
        int yearExpected = 3;
        assertEquals(monthExpected, player.getFacadeIA().getMonthF());
        assertEquals(yearExpected, player.getFacadeIA().getYearF());

        player.setGameplayStrategy(new ChangeTimeRetreat(endStrat));
        GameplayChoice gc = player.makeGameplayChoice();


        int stepExpected = 3 ;
        int stepChosen = player.getFacadeIA().getTimeStepF();
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(stepExpected,stepChosen);


    }

    @Test
    void actChangeTimeRetreatButItFails() {
        engine.getBoard().timeForward(12);
        player.setGameplayStrategy(new ChangeTimeRetreat(endStrat));

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }


    @Test
    void actChangeTimeAggressiveForward(){
        player.setGameplayStrategy(new ChangeTimeAggressiveForward(endStrat));
        GameplayChoice gc = player.makeGameplayChoice();
        int stepExpected = 3 ;
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(stepExpected,player.getFacadeIA().getTimeStepF());
    }
    @Test
    void actChangeTimeAggressiveForwardButItFails(){
        player.setGameplayStrategy(new ChangeTimeAggressiveForward(endStrat));
        pb.getCardManager().getCards().remove(temporalBoots);
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }
    @Test
    void actChangeTimeAggressiveBackward(){
        player.setGameplayStrategy(new ChangeTimeAggressiveBackward(endStrat));
        GameplayChoice gc = player.makeGameplayChoice();
        int stepExpected = -3 ;
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(stepExpected,player.getFacadeIA().getTimeStepF());
    }
    @Test
    void actChangeTimeAggressiveBackwardButItFails(){
        player.setGameplayStrategy(new ChangeTimeAggressiveBackward(endStrat));
        pb.getCardManager().getCards().remove(temporalBoots);
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }


    @Test
    void actChangeTimeModerateBackward(){
        player.setGameplayStrategy(new ChangeTimeModerate(endStrat));
        pb.getEnergyManager().addEnergy(EnergyType.WATER,4);
        GameplayChoice gc = player.makeGameplayChoice();
        int stepExpected = -3;
        int stepchosen = player.getFacadeIA().getTimeStepF();
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(stepExpected,stepchosen);

    }
    @Test
    void actChangeTimeModerateForward(){
        player.setGameplayStrategy(new ChangeTimeModerate(endStrat));
        pb.getEnergyManager().addEnergy(EnergyType.FIRE,4);
        GameplayChoice gc = player.makeGameplayChoice();
        int stepExpected = 3;
        int stepchosen = player.getFacadeIA().getTimeStepF();
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(stepExpected,stepchosen);

    }

    @Test
    void actChangeTimeModerateButItfails(){
        player.setGameplayStrategy(new ChangeTimeModerate(endStrat));
        pb.getEnergyManager().addEnergy(EnergyType.EARTH,4);
        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);
    }





}