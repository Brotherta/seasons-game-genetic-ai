package com.game.playerassets.ia;

import com.game.engine.card.Card;
import com.game.engine.card.Deck;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.strategy.cards.SimpleCardStrategy;
import com.utils.loaders.cards.CardsLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleIATest {
    GameEngine engine;
    Player p;
    CardsLoader testLoader;
    Deck testDeck;

    @BeforeEach
    void setUp() {
        engine=new GameEngine(4);
        p = engine.getPlayersCentralManager().getPlayerByID(0);
        engine.getPlayersCardsManager().doPrelude();
        File f = new File(Objects.requireNonNull(CardsLoader.class.getClassLoader().getResource("cards.json")).getPath());
        testLoader = CardsLoader.getCardsLoader(f.getPath());
        testDeck = testLoader.loadDeck(4, engine);
    }

    @Test
    void sortCard() {
        SimpleCardStrategy strategy = new SimpleCardStrategy(null);
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(testDeck.getCard(9));
        testCards.add(testDeck.getCard(19));
        testCards.add(testDeck.getCard(29));
        testCards.add(testDeck.getCard(4));
        testCards.add(testDeck.getCard(27));
        testCards.add(testDeck.getCard(7));
        ArrayList<Card> testCards2 = strategy.sortCard(testCards);
        assertEquals(9, testCards2.get(0).getNumber());
        assertEquals(27, testCards2.get(1).getNumber());
    }
}