package com.game.playerassets.ia.strategy.gameplay.activation.life;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Activation de la carte par sacrifice de celle-ci, si la réserve d'énergie du joueur est remplie et qu'il ne peut rien invoquer.
 */

public class LifePotionActivationRetreat extends AbstractStrategyLife {

    private final static int ID = 356;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_RETREAT;
    private final static int LIFE_NB = 26;


    public LifePotionActivationRetreat(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            List<Card> canActivateCards = facadeIA.getActivableCardsF();

            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();

            if (Card.contains(LIFE_NB, canActivateCards) && facadeIA.getAmountOfEnergiesLeftF() == 0) {
                return lifePotionStrategy(this, facadeIA, playerEnergies, canActivateCards, LIFE_NB);
            } else {
                return getNextStrategy().canAct(facadeIA);
            }
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }

}

