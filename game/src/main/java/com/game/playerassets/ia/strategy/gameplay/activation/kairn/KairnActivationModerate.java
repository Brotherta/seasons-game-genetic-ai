package com.game.playerassets.ia.strategy.gameplay.activation.kairn;

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
 * Essaye d'activer Kairn en dépensant le moins d'énergie nécessaire à l'invocation.
 */
public class KairnActivationModerate extends AbstractStrategyKairn {
    private final static int ID = 341;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int KAIRN_NB = 16;


    public KairnActivationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(KAIRN_NB, canActivateCards);
        if (card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            int[] energies = facadeIA.getAmountOfEnergiesArrayF();
            for (int i = 0; i < EnergyType.values().length; i++) {
                if (energies[i] > 0 && !StrategyUtils.isMyEnergiesUseful(EnergyType.values()[i], facadeIA, false, true, false, false)) {
                    int[] energyChosen = new int[EnergyType.values().length];
                    energyChosen[i] = 1;
                    facadeIA.setChoosableEnergiesF(energyChosen, 1);
                    facadeIA.setCardToUseF(card);
                    return GameplayChoice.ACTIVATE;

                }
            }


        }
        return getNextStrategy().canAct(facadeIA);
    }
}
