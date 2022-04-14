package com.game.playerassets.ia.strategy.gameplay.activation.power;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Activation de la carte par sacrifice de celle-ci, si le joueur n'a pas assez de jauge pour invoquer.
 */
public class PowerPotionActivationModerate extends AbstractStrategyPower {
    private final static int ID = 346;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int CARD_NUMBER = 23;


    public PowerPotionActivationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getActivableCardsF());
        if(card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE) && !facadeIA.hasEnoughGaugeF()) {
            facadeIA.setCardToUseF(card);
            return GameplayChoice.ACTIVATE;
        }
        else {
            return getNextStrategy().canAct(facadeIA);
        }
    }

}
