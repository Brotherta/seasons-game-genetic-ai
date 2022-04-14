package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * Stratégie aléatoire de choix de cartes.
 */
public class RandomCardStrategy extends AbstractStrategy {

    private final static int ID = 1;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomCardStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Card> cards = facadeIA.getChoosableCardsF();
        return cards.get(Util.getRandomInt(cards.size()));
    }
}
