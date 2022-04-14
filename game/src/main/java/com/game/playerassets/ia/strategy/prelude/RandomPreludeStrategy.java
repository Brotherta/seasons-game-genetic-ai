package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Choix aléatoire des cartes lors du prélude.
 */
public class RandomPreludeStrategy extends AbstractStrategy {

    private final static int ID = 11;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomPreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        Collections.shuffle(cards);
        Stack<Card> cardStack = new Stack<>();
        for (Card card : cards) {
            cardStack.push(card);
        }
        return cardStack;
    }
}
