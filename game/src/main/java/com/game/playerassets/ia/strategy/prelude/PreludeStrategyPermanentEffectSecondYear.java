package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Privilégie l'arrangement des cartes en mettant des cartes à effets permanent en année 2.
 */
public class PreludeStrategyPermanentEffectSecondYear extends AbstractPreludeStrategy {

    private final static int ID = 443;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public PreludeStrategyPermanentEffectSecondYear(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        Stack<Card> cardStack = new Stack<>();
        List<Card> tempC = new ArrayList<>();
        for(Card c : cards){
            if(c.getEffect().getIsPermanentEffect()){
                tempC.add(c);
            }
        }
        cards.removeAll(tempC);
        cardStack.addAll(cards.subList(0, cards.size()/2));
        cardStack.addAll(tempC);
        cardStack.addAll(cards.subList((cards.size()/2) , cards.size()));

        return cardStack;
    }
}
