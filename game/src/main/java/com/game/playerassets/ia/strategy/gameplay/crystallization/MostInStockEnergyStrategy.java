package com.game.playerassets.ia.strategy.gameplay.crystallization;


import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Srtatégie privilégiant la cristallisation du type d'énergie le plus présent dans le stock.
 */

public class MostInStockEnergyStrategy extends AbstractCrystallizationStrategy{

    private final static int ID = 407;
    private final static TypeStrategy TYPE = TypeStrategy.CRYSTALLIZE_AGGRESSIVE;

    public MostInStockEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE)) {
            int[] energiesToCrystallize = new int[EnergyType.values().length];
            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();
            if (Arrays.stream(playerEnergies).sum() > 0) {
                EnergyType typeToCrystallize = Util.maxTypeOwned(playerEnergies);
                energiesToCrystallize[typeToCrystallize.ordinal()] = playerEnergies[typeToCrystallize.ordinal()];
                facadeIA.setEnergiesToCrystallizeF(energiesToCrystallize);
                return GameplayChoice.CRYSTALLIZE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
