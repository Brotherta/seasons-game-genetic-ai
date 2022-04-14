package com.game.playerassets.ia.strategy.freeinvocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Essaye d'invoquer une carte si elle coûte le plus cher en termes de cristaux.
 */
public class FreeMostExpensiveCrystals extends AbstractStrategy {
    private final static int ID = 445;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public FreeMostExpensiveCrystals(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        assert !cards.isEmpty() : "Free Strategy, empty list set, or not set at all";

        Card card = null;
        int maxCrystals = 0;
        for (int i = 0; i < cards.size(); i++) {
            Card cardTmp = cards.get(i);
            int crystalsTmp = cardTmp.getCrystalCost();

            if (crystalsTmp > maxCrystals) {
                card = cardTmp;
                maxCrystals = crystalsTmp;
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
