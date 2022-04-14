package com.game.playerassets.ia.strategy.returnInHand;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Ne concerne que les cartes de type objet magique retour en main d'une carte à single effect.
 */

public class ReturnInHandSingleEffect extends AbstractReturnInHandStrategy{

    private final static int ID = 424;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public ReturnInHandSingleEffect(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();

        if (!cards.isEmpty()) {
            for (Card card : cards) {
                if (card.getEffect().getIsSingleEffect()) {
                    Card cardToReturn = Card.getCardInList(card.getNumber(), cards);
                    return Card.getCardInList(card.getNumber(), cards);
                }
            }
        }
        facadeIA.setChoosableCardsF(facadeIA.MagicObjectInvokedF());
        return getNextStrategy().canAct(facadeIA);
    }
}
