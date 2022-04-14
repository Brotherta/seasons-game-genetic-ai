package com.game.playerassets.ia.strategy.gameplay.invocation.grismine;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
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

class CopyStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        Card c = new Card("Lewisgrimfin",21,0,0,new int[4], Type.FAMILIAR);
        pb.getCardManager().addCard(c);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        pb.getCardManager().getInvokeDeck().upInvocationGauge(7);
    }

    @Test
    void actMostValueCopyStrategy() {
        player.setGameplayStrategy(new MostValueCopyStrategy(endStrat));

        PersonalBoard pbRichest = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2);
        pbRichest.getEnergyManager().addEnergy(new int[]{1,2,1,1});

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.FIRE,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.FIRE, 4);
        pb.getPlayerTurnManager().getConflictTable().initTable();

        GameplayChoice gc = player.makeGameplayChoice();
        Player chosen = player.getFacadeIA().getPlayerToCopyF();
        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(pbRichest.getPlayer(), chosen);
    }

    @Test
    void actMostValueCopyStrategyButItFails() {
        player.setGameplayStrategy(new MostValueCopyStrategy(endStrat));

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getEnergyManager().addEnergy(EnergyType.FIRE,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.FIRE,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.FIRE,4);

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }


    @Test
    void actNextValueCopyStrategy(){
        player.setGameplayStrategy(new NextValueCopyStrategy(endStrat));

        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getEnergyManager().addEnergy(new int[]{1,2,1,1});
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.EARTH,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.FIRE,4);
        Player nextRichest = engine.getPlayersCentralManager().getPlayerByID(3);

        GameplayChoice gc = player.makeGameplayChoice();
        Player chosen = player.getFacadeIA().getPlayerToCopyF();

        assertEquals(GameplayChoice.INVOKE,gc);
        assertEquals(nextRichest,chosen);
    }
    @Test
    void actNextValueCopyStrategyButItFails() {
        player.setGameplayStrategy(new NextValueCopyStrategy(endStrat));
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2).getEnergyManager().addEnergy(EnergyType.WATER,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.WATER,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.WATER,4);

        GameplayChoice gc = player.makeGameplayChoice();
        assertNull(gc);

    }

    @Test
    void actMostValuableFromDateToStep(){
        engine.getBoard().timeForward(2);
        player.setGameplayStrategy(new MostValuableFromDateToStep(endStrat));

        DiceFace actual = new DiceFace(0,false,false,false,2,0,new int[4]);
        Dice d = new Dice(SeasonType.WINTER,new DiceFace[]{actual},0);
        engine.getBoard().setBoardDice(d);
        engine.getBoard().getBoardDice().rollFace();
        pb.setActualDice(d);


        PersonalBoard pbRichest = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2);
        pbRichest.getEnergyManager().addEnergy(EnergyType.FIRE,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.WATER,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.EARTH,4);

        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE,gc);

        Player chosen = player.getFacadeIA().getPlayerToCopyF();
        assertEquals(pbRichest.getPlayer(),chosen);


    }
    @Test
    void actMostValuableButWeChangeSeasons(){
        engine.getBoard().timeForward(10);

        player.setGameplayStrategy(new MostValuableFromDateToStep(endStrat));

        DiceFace actual = new DiceFace(0,false,false,false,2,0,new int[4]);
        Dice d = new Dice(SeasonType.WINTER,new DiceFace[]{actual},0);
        engine.getBoard().setBoardDice(d);
        engine.getBoard().getBoardDice().rollFace();



        PersonalBoard pbRichest = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(2);
        pbRichest.getEnergyManager().addEnergy(EnergyType.EARTH,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1).getEnergyManager().addEnergy(EnergyType.WATER,4);
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(3).getEnergyManager().addEnergy(EnergyType.FIRE,4);
        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE,gc);

        Player chosen = player.getFacadeIA().getPlayerToCopyF();

        assertEquals(pbRichest.getPlayer(),chosen);
    }

}