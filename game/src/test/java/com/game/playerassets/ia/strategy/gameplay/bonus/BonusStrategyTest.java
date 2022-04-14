package com.game.playerassets.ia.strategy.gameplay.bonus;

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
import com.game.playerassets.ia.strategy.gameplay.bonus.crystallization.CrystallizeBonusMax;
import com.game.playerassets.ia.strategy.gameplay.bonus.draw.DrawBonusNoCard;
import com.game.playerassets.ia.strategy.gameplay.bonus.exchange.ExchangeBonusUseless;
import com.game.playerassets.ia.strategy.gameplay.bonus.gauge.GaugeBonusStrategy;
import com.game.playerassets.ia.strategy.sacrificeenergy.RandomSacrificeEnergyStrategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BonusStrategyTest {

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
    void gaugeBonusStrategyTest() {
        player.setGameplayStrategy(new GaugeBonusStrategy(endStrat));
        Card cardTest = new Card("test", 0, 0, 0, new int[4], Type.FAMILIAR);
        for (PersonalBoard pbOthers : engine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!pbOthers.equals(pb)) {
                pbOthers.addCrystal(5);
            }
        }
        pb.getCardManager().addCard(cardTest);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE_BONUS);

        GameplayChoice choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE_BONUS, choice);

        pb.getCardManager().getInvokeDeck().upInvocationGauge(1);

        choice = player.makeGameplayChoice();
        assertNull(choice);

        pb.getCardManager().getInvokeDeck().reset();
        pb.getPlayerTurnManager().getConflictTable().initTable();
        choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.INVOKE_BONUS, choice);

        pb.getBonusManager().decreaseBonusAmount();
        choice = player.makeGameplayChoice();
        assertNull(choice);
    }

    @Test
    void crystallizeBonusMaxTest() {
        DiceFace diceFaceSell = new DiceFace(0, true, false, false, 0, 0, new int[4]);
        DiceFace diceFaceNotSell = new DiceFace(0, false, false, false, 0, 0, new int[4]);
        Dice d = new Dice(SeasonType.WINTER, new DiceFace[]{diceFaceSell}, 1);
        engine.getBoard().timeForward(12);
        for (PersonalBoard pbOthers : engine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!pbOthers.equals(pb)) {
                pbOthers.addCrystal(5);
            }
        }
        d.rollFace();
        pb.setActualDice(d);

        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.CRYSTALLIZE_BONUS);
        player.setGameplayStrategy(new CrystallizeBonusMax(endStrat));

        pb.getEnergyManager().addEnergy(EnergyType.EARTH, 4);
        GameplayChoice choice = player.makeGameplayChoice();
        assertNull(choice); // he has a die with sell

        d = new Dice(SeasonType.WINTER, new DiceFace[]{diceFaceNotSell}, 1);
        d.rollFace();
        pb.setActualDice(d);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.CRYSTALLIZE_BONUS, choice); // he didn't have the selling die

        Card card = new Card("test", 0, 0, 0, new int[]{0, 0, 0, 1}, Type.FAMILIAR);
        pb.getCardManager().addCard(card);

        pb.getPlayerTurnManager().getConflictTable().initTable();
        choice = player.makeGameplayChoice();
        assertNull(choice); // he has a card that cost 1 earth.
    }

    @Test
    void DrawBonusNoCardTest() {
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.DRAW_CARD_WITH_BONUS);
        player.setGameplayStrategy(new DrawBonusNoCard(endStrat));
        for (PersonalBoard pbOthers : engine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!pbOthers.equals(pb)) {
                pbOthers.addCrystal(5);
            }
        }
        GameplayChoice choice = player.makeGameplayChoice();
        assertEquals(GameplayChoice.DRAW_CARD_WITH_BONUS, choice); // he has no card in hands and the year is 1.

        Card card = new Card("test", 0, 0, 0, new int[4], Type.FAMILIAR);
        pb.getCardManager().addCard(card);
        choice = player.makeGameplayChoice();
        assertNull(choice); // he has a card in hand

        pb.getCardManager().reset();
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        choice = player.makeGameplayChoice();
        assertNull(choice); // we are in the third year
    }

    @Test
    void ExchangeBonusUselessTest() {
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.EXCHANGE_BONUS);
        player.setGameplayStrategy(new ExchangeBonusUseless(endStrat));
        player.setSacrificeEnergyStrategy(new RandomSacrificeEnergyStrategy(endStrat));
        engine.getBoard().timeForward(12);
        engine.getBoard().timeForward(12);
        for (PersonalBoard pbOthers : engine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!pbOthers.equals(pb)) {
                pbOthers.addCrystal(5);
            }
        }
        // positive test exchange 2 fire with 1 air and 1 earth
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 5);
        Card card = new Card("2cost", 1, 0, 0, new int[]{1, 0, 0, 1}, Type.FAMILIAR);
        pb.getCardManager().addCard(card);
        GameplayChoice gc = player.makeGameplayChoice();
        assertEquals(GameplayChoice.EXCHANGE_BONUS, gc);
        int[] chosenEnergy = player.getFacadeIA().getChoosableEnergiesF();
        assertArrayEquals(new int[]{0, 0, 2, 0, 1, 0, 0, 1}, chosenEnergy);

        pb.getCardManager().reset();

        //negative test he can't only exchange 2 energies
        Card cardNeg = new Card("tooMuch", 2, 0, 0, new int[]{1, 1, 0, 1}, Type.FAMILIAR);
        pb.getCardManager().addCard(cardNeg);
        gc = player.makeGameplayChoice();
        assertNull(gc);

        // doesn't have enough energy.
        pb.getEnergyManager().reset();
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 1);
        pb.getCardManager().reset();
        pb.getCardManager().addCard(card);
        gc = player.makeGameplayChoice();
        assertNull(gc);
    }
}