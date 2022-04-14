package com.game.playerassets.ia.strategy.gameplay.crystallization;

import com.game.engine.SeasonType;
import com.game.engine.energy.Energy;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stratégie priviliégiant la cristallisation des énergies ayant le taux de cristallisation le plus faible.
 */

public class LeastValuableEnergyStrategy extends AbstractCrystallizationStrategy {

    private final static int ID = 389;
    private final static TypeStrategy TYPE = TypeStrategy.CRYSTALLIZE_RETREAT;

    public LeastValuableEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE)) {
            int[] energiesToCrystallize = new int[EnergyType.values().length];

            SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
            EnergyType leastValuableEnergyType1 = new Energy(currentSeason).getEnergyType();
            EnergyType leastValuableEnergyType2 = new Energy(Util.getPreviousSeasons(currentSeason)).getEnergyType();

            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();

            if (Arrays.stream(playerEnergies).sum() > 0) {
                if (playerEnergies[leastValuableEnergyType1.ordinal()] == 0) {
                    if (playerEnergies[leastValuableEnergyType2.ordinal()] == 0) {
                        return getNextStrategy().canAct(facadeIA);
                    }
                }
                energiesToCrystallize[leastValuableEnergyType1.ordinal()] = playerEnergies[leastValuableEnergyType1.ordinal()];
                energiesToCrystallize[leastValuableEnergyType2.ordinal()] = playerEnergies[leastValuableEnergyType2.ordinal()];
                facadeIA.setEnergiesToCrystallizeF(energiesToCrystallize);
                return GameplayChoice.CRYSTALLIZE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
