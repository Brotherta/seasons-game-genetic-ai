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
 * Activation de la carte par sacrifice de celle-ci, si le joueur a au moins 3 énergies et que les énergies ne servent pas à l'invocation.
 */

public class LifePotionActivationModerate extends AbstractStrategyLife {

    private final static int ID = 355;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int LIFE_NB = 26;

    public LifePotionActivationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            List<Card> canActivateCards = facadeIA.getActivableCardsF();

            Card card = null;

            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();

            if (Card.contains(LIFE_NB, canActivateCards) && Arrays.stream(playerEnergies).sum() >= 3) {
                return lifePotionStrategy(this, facadeIA, playerEnergies, canActivateCards, LIFE_NB);
            } else {
                return getNextStrategy().canAct(facadeIA);
            }
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }

}
