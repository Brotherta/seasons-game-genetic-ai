package com.game.playerassets.ia.strategy.gameplay.activation.dream;


import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activation de la carte, si le joueur a bien une carte à invoquer, et qu'il n'a pas d'énergies dans son stock.
 */

public class DreamPotionActivationRetreat extends AbstractStrategyDream{

    private final static int ID = 352;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_RETREAT;
    private final static int DREAM_NB = 24;


    public DreamPotionActivationRetreat(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(DREAM_NB, canActivateCards);
        if (card!= null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            List<Card> cardInHands = facadeIA.getCardsF();
            if (!cardInHands.isEmpty()) {
                if (Arrays.stream(facadeIA.getAmountOfEnergiesArrayF()).sum() == 0) {
                    facadeIA.setCardToUseF(card);
                    return GameplayChoice.ACTIVATE;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
