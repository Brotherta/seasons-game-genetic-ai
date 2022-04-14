package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;
import java.util.Stack;

/**
 * Privilégie l'arrangement des cartes en mettant des cartes à effets permanent en année 3.
 */

public class PreludeStrategyPermanentEffectLastYear extends AbstractPreludeStrategy {
    private final static int ID = 440;

    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public PreludeStrategyPermanentEffectLastYear(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        Stack<Card> cardStack = new Stack<>();
        for(Card c : cards){
            if(c.getEffect().getIsPermanentEffect()){
                cardStack.add(c);}
        }
        cards.removeAll(cardStack);
        cardStack.addAll(cards);
        return cardStack;
    }
}
