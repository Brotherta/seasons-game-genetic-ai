package com.game.playerassets;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FacadeIATest {
    private final int NB_PLAYER = 2;
    private final int SCORE = 10;
    GameEngine engine;
    @Mock
    Dice d = mock(Dice.class);


    @BeforeEach
    void setUp() {
        engine = new GameEngine(NB_PLAYER);
        engine.getPlayersCardsManager().doPrelude();
    }

    @Test
    void getScore() {
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).addScore(SCORE);
        assertEquals(SCORE,engine.getPlayersCentralManager().getPlayerByID(0).getFacadeIA().getScoreF());
        assertNotEquals(0,engine.getPlayersCentralManager().getPlayerByID(0).getFacadeIA().getScoreF());
    }

    @Test
    void getCards() {
        assertEquals(3,engine.getPlayersCentralManager().getPlayerByID(1).getFacadeIA().getCardsF().size());
    }

    @Test
    void getTotalCardsEnergyCost() {
        int[] totalCostCalculated = new int[4];
        Player p = engine.getPlayersCentralManager().getPlayerByID(0);
        List<Card> cards = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0).getCardManager().getCards();
        for(Card c : cards){
            for(int i =0; i < 4 ; i++){
                totalCostCalculated[i] += c.getEnergyCost()[i];
            }

        }
        assertArrayEquals(totalCostCalculated,p.getFacadeIA().getTotalCardsEnergyCostF());

    }
}