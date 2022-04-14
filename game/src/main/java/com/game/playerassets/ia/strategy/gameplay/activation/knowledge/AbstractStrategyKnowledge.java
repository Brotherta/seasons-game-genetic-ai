package com.game.playerassets.ia.strategy.gameplay.activation.knowledge;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

public class AbstractStrategyKnowledge extends AbstractStrategy {
    private final static int CONFLICT = 4;

    public AbstractStrategyKnowledge(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }

    protected Object knowledgeUtils(FacadeIA facadeIA, Card knowledge) {
        List<Card> cardsInvokable = facadeIA.getAllCardF();
        for (Card card : cardsInvokable) {
            if (Arrays.stream(card.getEnergyCost()).sum() > 0) {
                facadeIA.setCardToUseF(knowledge);
                return GameplayChoice.ACTIVATE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
