package com.game.playerassets.ia.strategy.freeinvocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Essaye d'invoquer une carte si elle coûte, à la fois des énergies et des cristaux.
 * Priorise d'abord le coût en énergie.
 */
public class FreeEnergyAndCrystals extends AbstractStrategy {
    private final static int ID = 444;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public FreeEnergyAndCrystals(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        assert !cards.isEmpty() : "Free Strategy, empty list set, or not set at all";

        Card card = null;
        int maxEnergies = 0;
        for (int i = 0; i < cards.size(); i++) {
            Card cardTmp = cards.get(i);
            int crystalsTmp = cardTmp.getCrystalCost();
            int energiesTmp = Arrays.stream(cardTmp.getEnergyCost()).sum();

            if (energiesTmp != 0 && crystalsTmp != 0 && (energiesTmp > maxEnergies)) {
                card = cardTmp;
                maxEnergies = energiesTmp;
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
