package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Privilégie les cartes lors du prélude qui lui rapporte le plus de points.
 */

public class ChooseMaximumPointForPrelude extends AbstractCardStrategy {
    private final static int ID = 448;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public ChooseMaximumPointForPrelude(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        if (facadeIA.isPreludeF()) {
            ArrayList<Card> cards = facadeIA.getChoosableCardsF();
            return chooseMaxCard(cards);
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
