package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreludeStrategySingleEffectFirstYear extends AbstractPreludeStrategy {
    private final static int ID = 446;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public PreludeStrategySingleEffectFirstYear(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        Stack<Card> cardStack = new Stack<>();
        List<Card> tempC = new ArrayList<>();
        for(Card c : cards){
            if(c.getEffect().getIsSingleEffect()){
                tempC.add(c);
            }
        }
        cards.removeAll(tempC);
        cardStack.addAll(cards);
        cardStack.addAll(tempC);

        return cardStack;
    }
}
