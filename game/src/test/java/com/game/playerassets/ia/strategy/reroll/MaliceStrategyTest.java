package com.game.playerassets.ia.strategy.reroll;

import com.game.engine.SeasonType;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaliceStrategyTest {

    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);


    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(1);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(1);

    }

    @Test
    void actMaliceStrategyAggressive() {
        player.setRerollStrategy(new MaliceStrategyAggressive(endStrat));
        assertTrue(player.chooseIfReRollDice());

    }


    @Test
    void actMaliceStrategyModerateEnergy() {
        player.setRerollStrategy(new MaliceStrategyModerateEnergy(endStrat));
        DiceFace f = new DiceFace(0, true, false, false, 2, 0, new int[]{0, 0, 2, 0});
        Dice d = new Dice(SeasonType.WINTER, new DiceFace[]{f}, 0);
        d.rollFace();
        assertEquals(f, d.getActualFace());
        pb.setActualDice(d);
        assertFalse(player.chooseIfReRollDice());
        pb.getPlayerTurnManager().getConflictTable().initTable();
        DiceFace f2 = new DiceFace(0,true,false,false,2,6,new int[]{0,0,0,0});
        Dice d2 = new Dice(SeasonType.WINTER,new DiceFace[]{f2},1);
        d2.rollFace();
        assertEquals(f2,d2.getActualFace());
        pb.setActualDice(d2);
        assertTrue(player.chooseIfReRollDice());
    }

    @Test
    void actMaliceStrategyModerateInvoke(){
        player.setRerollStrategy(new MaliceStrategyModerateInvoke(endStrat));
        DiceFace f = new DiceFace(0,true,false,true,2,0,new int[]{0,0,2,0});
        Dice d = new Dice(SeasonType.WINTER,new DiceFace[]{f},0);
        d.rollFace();
        pb.setActualDice(d);
        assertEquals(f,d.getActualFace());
        assertFalse(player.chooseIfReRollDice());
        pb.getPlayerTurnManager().getConflictTable().initTable();
        DiceFace f2 = new DiceFace(0,true,false,false,2,6,new int[]{0,0,0,0});
        Dice d2 = new Dice(SeasonType.WINTER,new DiceFace[]{f2},1);
        d2.rollFace();
        assertEquals(f2,d2.getActualFace());
        pb.setActualDice(d2);
        assertTrue(player.chooseIfReRollDice());


    }


}