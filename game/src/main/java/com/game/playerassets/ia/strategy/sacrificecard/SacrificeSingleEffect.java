package com.game.playerassets.ia.strategy.sacrificecard;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Sacrifice d'une carte qui a un single effect.
 */

public class SacrificeSingleEffect extends AbstractSacrificeCardStrategy{

    private final static int ID = 423;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public SacrificeSingleEffect(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();

        if (!cards.isEmpty()) {
            for (Card card : cards) {
                if(card.getEffect().getIsSingleEffect()) {
                    return Card.getCardInList(card.getNumber(), cards);
                }
            }
        }

        facadeIA.setChoosableCardsF(facadeIA.getInvokedCardsF());
        return getNextStrategy().canAct(facadeIA);
    }
}
