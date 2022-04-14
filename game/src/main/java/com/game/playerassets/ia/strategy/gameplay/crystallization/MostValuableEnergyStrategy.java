package com.game.playerassets.ia.strategy.gameplay.crystallization;

import com.game.engine.SeasonType;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stratégie privilégiant la cristallisation des énergies
 * ayant le taux de cristallisation le plus important
 */

public class MostValuableEnergyStrategy extends AbstractCrystallizationStrategy {

    private final static int ID = 335;
    private final static TypeStrategy TYPE = TypeStrategy.CRYSTALLIZE_AGGRESSIVE;

    public MostValuableEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        int[] energiesToCrystallize = new int[EnergyType.values().length];
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        EnergyType mostValuableEnergyType = facadeIA.getMostValuableEnergyTypeF(currentSeason);
        int[] energies = facadeIA.getAmountOfEnergiesArrayF();
        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE) && Arrays.stream(energies).sum() > 0) {
            if (energies[mostValuableEnergyType.ordinal()] == 0) {
                return getNextStrategy().canAct(facadeIA);
            } else {
                int nbToCrystallize = energies[mostValuableEnergyType.ordinal()];
                energiesToCrystallize[mostValuableEnergyType.ordinal()] = nbToCrystallize;
                facadeIA.setEnergiesToCrystallizeF(energiesToCrystallize);
                return GameplayChoice.CRYSTALLIZE;
            }
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }

}
