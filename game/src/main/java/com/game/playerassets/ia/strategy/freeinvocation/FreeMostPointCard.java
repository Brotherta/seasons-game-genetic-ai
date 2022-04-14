package com.game.playerassets.ia.strategy.freeinvocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Invoque la carte de sa main rapportant le plus de points.
 */
public class FreeMostPointCard extends AbstractStrategy {
    private final static int ID = 427;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public FreeMostPointCard(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        assert !cards.isEmpty() : "Free Strategy, empty list set, or not set at all";

        Card card = null;
        int max = 0;
        for (Card card_tmp : cards) {
            int max_tmp = card_tmp.getPoints();
            if (max_tmp > max) {
                card = card_tmp;
                max = max_tmp;
            }
        }

        if (card != null) {
            return card;
        } else {
            facadeIA.setChoosableCardsF(cards);
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
