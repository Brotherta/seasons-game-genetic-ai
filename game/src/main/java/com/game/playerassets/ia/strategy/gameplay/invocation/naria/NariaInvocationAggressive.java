package com.game.playerassets.ia.strategy.gameplay.invocation.naria;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Invoque Naria la Prophétesse dès que possible.
 */

public class NariaInvocationAggressive extends AbstractStrategyNaria {

    private final static int ID = 403;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_AGGRESSIVE;
    private final static int NARIA_NB = 12;

    public NariaInvocationAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(NARIA_NB, facadeIA.getCardsF());

        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE) && !facadeIA.mustWaitF(NARIA_NB)) {
            if (facadeIA.canInvokeThisCardF(card)) {
                facadeIA.setCardToUseF(card);
                return GameplayChoice.INVOKE;
            }
        }
        facadeIA.setMustWaitF(NARIA_NB);
        return getNextStrategy().canAct(facadeIA);
    }
}
