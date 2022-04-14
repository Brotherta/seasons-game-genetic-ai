package com.game.playerassets.ia.strategy.gameplay.invocation.naria;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Invoque Naria la Prophétesse en fin de 3 ème année.
 */

public class NariaInvocationModerateThirdYear extends AbstractStrategyNaria {

    private final static int ID = 401;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_MODERATE;
    private final static int NARIA_NB = 12;

    public NariaInvocationModerateThirdYear(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(NARIA_NB, facadeIA.getCardsF());

        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE) && !facadeIA.mustWaitF(NARIA_NB)) {
            if (facadeIA.getYearF() > 2 && facadeIA.getMonthF() >= 10) {
                if (facadeIA.canInvokeThisCardF(card)) {
                    facadeIA.setCardToUseF(card);
                    return GameplayChoice.INVOKE;
                }
            }
        }

        facadeIA.setMustWaitF(NARIA_NB);
        return getNextStrategy().canAct(facadeIA);
    }

}
