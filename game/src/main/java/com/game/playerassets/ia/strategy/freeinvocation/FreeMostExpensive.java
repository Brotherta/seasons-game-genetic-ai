package com.game.playerassets.ia.strategy.freeinvocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Invoque la carte la plus chère de sa main sans payer grâce à l'effet "Invoke for free".
 */
public class FreeMostExpensive extends AbstractStrategy {
    private final static int ID = 426;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public FreeMostExpensive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        assert !cards.isEmpty() : "Free Strategy, empty list set, or not set at all";

        Card card = null;
        int max = 0;
        for (Card card_tmp : cards) {
            int max_tmp = Arrays.stream(card_tmp.getEnergyCost()).sum();
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
