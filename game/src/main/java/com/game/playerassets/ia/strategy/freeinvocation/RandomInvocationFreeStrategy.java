package com.game.playerassets.ia.strategy.freeinvocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Choix aléatoire de choix d'invocation gratuite.
 */
public class RandomInvocationFreeStrategy extends AbstractStrategy {

    private final static int ID = 8;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomInvocationFreeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getCardsF();
        Collections.shuffle(cards);
        return cards.get(0);
    }
}
