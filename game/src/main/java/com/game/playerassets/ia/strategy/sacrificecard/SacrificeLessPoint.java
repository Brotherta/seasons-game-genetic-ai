package com.game.playerassets.ia.strategy.sacrificecard;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Sacrifice de la carte rapportant le moins de points.
 */

public class SacrificeLessPoint extends AbstractSacrificeCardStrategy{

    private final static int ID = 422;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public SacrificeLessPoint(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        if (!cards.isEmpty()) {
            int minPoint = cards.get(0).getPoints();
            int nbCard = 0;

            for (Card card : cards) {
                if (card.getPoints() <= minPoint) {
                    minPoint = card.getPoints();
                    nbCard = card.getNumber();
                }
            }
            if (nbCard != 0) {
                return Card.getCardInList(nbCard, cards);
            }
        }
        facadeIA.setChoosableCardsF(facadeIA.getInvokedCardsF());
        return getNextStrategy().canAct(facadeIA);
    }
}
