package com.game.playerassets.ia.strategy.gameplay.activation.kairn;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.energy.Energy;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Essaye d'activer Kairn, si une énergie n'est pas nécessaire à l'invocation ou qu'elle n'a pas de valeur particulière, on active.
 */

public class KairnActivationRetreat extends AbstractStrategyKairn{
    private final static int ID = 344;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;
    private final static int KAIRN_NB = 16;




    public KairnActivationRetreat(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }


    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(KAIRN_NB, canActivateCards);
        if (card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            int[] energies = facadeIA.getAmountOfEnergiesArrayF();

            SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
            EnergyType leastValuable = new Energy(currentSeason).getEnergyType();
            EnergyType leastValuable2 = new Energy(Util.getPreviousSeasons(currentSeason)).getEnergyType(); // the energy type the most current the previous seasons is always one of the least valuable the next seasons
            int[] energyChosen = new int[EnergyType.values().length];
            if (energies[leastValuable.ordinal()] >= 1 && !StrategyUtils.isMyEnergiesUseful(EnergyType.values()[leastValuable.ordinal()], facadeIA, false, true, false, false)) { //choose the least valuable on the current seasons
                energyChosen[leastValuable.ordinal()] = 1;
            } else if (energies[leastValuable2.ordinal()] >= 1 && !StrategyUtils.isMyEnergiesUseful(EnergyType.values()[leastValuable2.ordinal()], facadeIA, false, true, false, false)) {
                energyChosen[leastValuable2.ordinal()] = 1;
            } else {
                return getNextStrategy().canAct(facadeIA);
            }
            facadeIA.setChoosableEnergiesF(energyChosen, 1);
            facadeIA.setCardToUseF(card);
            return GameplayChoice.ACTIVATE;

        }
        return getNextStrategy().canAct(facadeIA);
    }
}
