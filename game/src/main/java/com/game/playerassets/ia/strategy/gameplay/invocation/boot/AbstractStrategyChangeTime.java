package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

public class AbstractStrategyChangeTime extends AbstractStrategy {
    private final static int CONFLICT = 1;

    public AbstractStrategyChangeTime(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }

    protected static Object timeStrategy(AbstractStrategy strat, int Step, FacadeIA facadeIA, int CARD_NUMBER) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF());

        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE)
                && !facadeIA.mustWaitF(CARD_NUMBER)
                && facadeIA.canInvokeThisCardF(card)) {
            facadeIA.setCardToUseF(Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF()));
            facadeIA.setTimeStepF(Step);
            return GameplayChoice.INVOKE;
        } else {
            if(card != null){
            facadeIA.setMustWaitF(CARD_NUMBER);}
            return strat.getNextStrategy().canAct(facadeIA);
        }
    }
}
