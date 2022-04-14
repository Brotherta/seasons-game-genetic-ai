package com.game.playerassets.ia.strategy.gameplay.invocation.syllas;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Invoque Sylass dè qu'il l'a.
 */

public class SyllasInvocationAggressive extends AbstractStrategySyllas {
    private final static int ID = 400;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_AGGRESSIVE;
    private final static int CARD_NUMBER = 10;

    public SyllasInvocationAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF());

        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE)
                && facadeIA.canInvokeThisCardF(card)
                && !facadeIA.mustWaitF(CARD_NUMBER)) {

            facadeIA.setCardToUseF(card);
            return GameplayChoice.INVOKE;
        }

        facadeIA.setMustWaitF(CARD_NUMBER);
        return getNextStrategy().canAct(facadeIA);
    }
}
