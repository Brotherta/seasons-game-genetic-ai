package com.game.playerassets.ia.strategy.gameplay.activation.dream;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Activation de la carte si le joueur a bien une carte à invoquer,
 */

public class DreamPotionActivationAggressive extends AbstractStrategyDream{

    private final static int ID = 350;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;
    private final static int DREAM_NB = 24;

    public DreamPotionActivationAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(DREAM_NB, canActivateCards);

        if (card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            List<Card> cardsInHand = facadeIA.getCardsF();
            if (!cardsInHand.isEmpty()) {
                facadeIA.setCardToUseF(card);
                return GameplayChoice.ACTIVATE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
