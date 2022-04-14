package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.effects.MendiantEffect;
import com.game.engine.effects.effects.TemporalEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreludeStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);
    ArrayList<Card> cards;
    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);

    }

    @Test
    void PreludeStrategyPermanentEffectactLastYear(){
        player.setPreludeStrategy(new PreludeStrategyPermanentEffectLastYear(endStrat));
        Card c = new Card("perma",0,0,0,new int[4], Type.OBJECT);
        EffectTemplate perma = new MendiantEffect("test perma effect ", EffectType.SEASON,engine);
        EffectTemplate randEffect = new TemporalEffect("testeffect ", EffectType.DEFAULT,engine);
        c.setEffect(perma);
        perma.setCard(c);
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < 8 ; i++){
            Card tempC = new Card("cardTest",i,20,0,new int[4],Type.OBJECT);
            tempC.setEffect(randEffect);
            cards.add(tempC);
        }
        cards.add(c);

        player.getFacadeIA().getTemporaryHandF().addAll(cards);
        Stack<Card> s = player.manageCard();
        Card selected = s.firstElement();
        assertEquals(c,selected);

    }
    @Test
    void PreludeStrategyPermanentEffectact(){
        player.setPreludeStrategy(new PreludeStrategyPermanentEffect(endStrat));
        Card c = new Card("perma",0,0,0,new int[4], Type.OBJECT);
        EffectTemplate perma = new MendiantEffect("test perma effect ", EffectType.SEASON,engine);
        EffectTemplate randEffect = new TemporalEffect("testeffect ", EffectType.DEFAULT,engine);
        c.setEffect(perma);
        perma.setCard(c);
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < 8 ; i++){
            Card tempC = new Card("cardTest",i,20,0,new int[4],Type.OBJECT);
            tempC.setEffect(randEffect);
            cards.add(tempC);
        }
        cards.add(c);

        player.getFacadeIA().getTemporaryHandF().addAll(cards);
        Stack<Card> s = player.manageCard();
        assertEquals(c,s.pop());

    }
    @Test
    void PreludeStrategyPermanentEffectSecondYearact(){
        player.setPreludeStrategy(new PreludeStrategyPermanentEffectSecondYear(endStrat));
        Card c = new Card("perma",0,0,0,new int[4], Type.OBJECT);
        EffectTemplate perma = new MendiantEffect("test perma effect ", EffectType.SEASON,engine);
        EffectTemplate randEffect = new TemporalEffect("testeffect ", EffectType.DEFAULT,engine);
        c.setEffect(perma);
        perma.setCard(c);
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < 8 ; i++){
            Card tempC = new Card("cardTest",i,20,0,new int[4],Type.OBJECT);
            tempC.setEffect(randEffect);
            cards.add(tempC);
        }
        cards.add(c);

        player.getFacadeIA().getTemporaryHandF().addAll(cards);
        Stack<Card> s = player.manageCard();


        assertEquals(c,s.get(4));

    }
}