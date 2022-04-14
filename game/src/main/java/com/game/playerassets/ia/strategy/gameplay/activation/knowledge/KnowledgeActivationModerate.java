package com.game.playerassets.ia.strategy.gameplay.activation.knowledge;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Sacrifie la potion, si il lui reste au moins 3 emplacements vides et qu'il lui reste des cartes à invoquer qui coûtent de l'énergie.
 */
public class KnowledgeActivationModerate extends AbstractStrategyKnowledge {

    private final static int ID = 348;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int KNOWLEDGE = 25;

    public KnowledgeActivationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card knowledge = Card.getCardInList(KNOWLEDGE, canActivateCards);

        if (knowledge != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)
                && facadeIA.getAmountOfEnergiesLeftF() >= 3) {
            return knowledgeUtils(facadeIA, knowledge);
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
