package com.game.playerassets.ia.strategy.gameplay.activation.ishtar;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Activation de la carte si nos trois énergies ne sont pas nécessaires à l'invocation d'une carte.
 */
public class IshtarActivationModerate extends AbstractStrategyIshtar {

    private final static int ID = 331;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int ISHTAR_NB = 5;

    public IshtarActivationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(ISHTAR_NB, canActivateCards);

        if (card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            card = Card.getCardInList(ISHTAR_NB, canActivateCards);
            EnergyType type = ishtarActivationUtils(facadeIA);

            if (!StrategyUtils.isMyEnergiesUseful(type, facadeIA, false, true, false, false)) {
                facadeIA.setCardToUseF(card);
                int[] energies = new int[4];
                energies[type.ordinal()] = 1;
                facadeIA.setChoosableEnergiesF(energies, 1);
                return GameplayChoice.ACTIVATE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
