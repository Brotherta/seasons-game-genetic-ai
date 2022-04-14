package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Privilégie l'arrangement des cartes a single effet en année 1
 * et des cartes a effet permanent en année 3 .
 */

public class PreludeStrategySEFYAndPLY extends AbstractPreludeStrategy{
    private final static int ID = 447;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public PreludeStrategySEFYAndPLY(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        Stack<Card> cardStack = new Stack<>();
        List<Card> single = new ArrayList<>();
        for(Card c : cards){
            if(c.getEffect().getIsPermanentEffect()){
                cardStack.add(c);
                continue;
            }
            if(c.getEffect().getIsSingleEffect()){
                single.add(c);
            }
        }
        cards.removeAll(cardStack);
        cards.removeAll(single);
        cardStack.addAll(cards);
        cardStack.addAll(single);
        return cardStack;
    }
}
