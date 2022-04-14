package com.game.playerassets.ia.strategy.gameplay.activation.dream;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activation de la carte si le joueur a bien une carte à invoquer, et si il n'a pas d'énergies qui peuvent lui servir à invoquer/activer une carte.
 */

public class DreamPotionActivationModerate extends AbstractStrategyDream {

    private final static int ID = 351;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int DREAM_NB = 24;


    public DreamPotionActivationModerate(Strategy nextStrategy) {
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
                int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();
                if (Arrays.stream(playerEnergies).sum() > 0) {
                    for (int i = 0; i < playerEnergies.length; i++) {
                        EnergyType type = EnergyType.values()[i];
                        if (StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                                true, false, false, false)) {
                            return getNextStrategy().canAct(facadeIA);
                        }
                    }
                }
                facadeIA.setCardToUseF(card);
                return GameplayChoice.ACTIVATE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
