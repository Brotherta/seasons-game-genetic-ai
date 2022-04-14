package com.game.playerassets.ia.strategy.gameplay.activation.life;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activation de la carte par sacrifice de celle-ci, si il a au moins 1 énergie dans son stock.
 */

public class LifePotionActivationAggressive extends AbstractStrategyLife {

    private final static int ID = 354;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;
    private final static int LIFE_NB = 26;

    public LifePotionActivationAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            List<Card> canActivateCards = facadeIA.getActivableCardsF();

            Card card = null;

            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();

            if (Card.contains(LIFE_NB, canActivateCards)
                    && Arrays.stream(playerEnergies).sum() > 0) {

                card = Card.getCardInList(LIFE_NB, canActivateCards);
                facadeIA.setCardToUseF(card);
                return GameplayChoice.ACTIVATE;
            } else {
                return getNextStrategy().canAct(facadeIA);
            }
        } else {
            return getNextStrategy().canAct(facadeIA);
        }

    }

}
