package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * choisis un pas l'avantageant pour la cristallisation
 */
public class ChangeTimeModerate extends AbstractStrategyChangeTime {
    private final static int ID = 365;
    private final static int CARD_NUMBER = 7;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_MODERATE;

    public ChangeTimeModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF());
        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE)
                && facadeIA.canInvokeThisCardF(card)) {
            int step = 0;
            int[] energies = facadeIA.getAmountOfEnergiesArrayF();
            EnergyType maxType = Util.maxTypeOwned(energies);

            SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
            SeasonType nextSeason = Util.getNextSeasons(currentSeason);
            SeasonType previousSeason = Util.getPreviousSeasons(currentSeason);

            EnergyType mostValuableCurrent = facadeIA.getMostValuableEnergyTypeF(currentSeason);
            EnergyType mostValuablePrevious = facadeIA.getMostValuableEnergyTypeF(previousSeason);
            EnergyType mostValuableNext = facadeIA.getMostValuableEnergyTypeF(nextSeason);

            if (maxType == mostValuablePrevious) {
                step = -3;
            } else if (maxType == mostValuableNext) {
                step = 3;
            } else if (maxType == mostValuableCurrent) {
                return getNextStrategy().canAct(facadeIA);
            }
            return timeStrategy(this, step, facadeIA, CARD_NUMBER);
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
