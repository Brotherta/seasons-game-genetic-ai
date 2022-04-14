package com.game.playerassets.ia.strategy.gameplay.invocation.priority;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * S'il peut invoquer plusieurs cartes, il priorise les cartes à effets permanents.
 */

public class PriorityPermanentInvocation extends AbstractStrategy {
    private static final int ID = 408;
    private static final TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public PriorityPermanentInvocation(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> invokableCards = facadeIA.getInvokableCardsF();
        if (!invokableCards.isEmpty()) {
            boolean hasPermanentCard = false;
            Card cardToInvoke = null;
            for (Card card : invokableCards) {
                if (card.getEffect().getIsPermanentEffect()) {
                    hasPermanentCard = true;
                    cardToInvoke = card;
                    break;
                }
            }
            if (hasPermanentCard) {
                facadeIA.setCardToUseF(cardToInvoke);
                return GameplayChoice.INVOKE;
            }
        }
        return getNextStrategy().act(facadeIA);
    }
}
