package com.game.playerassets.ia.strategy.gameplay.crystallization;


import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stratégie privilégiant la cristallisation des énergies ayant le plus fort taux de cristallisation de la
 * saison courante ainsi que celle de la saison suivante.
 */

public class CurrentAndNextSeasonsEnergyStrategy extends AbstractCrystallizationStrategy{

    private final static int ID = 409;
    private final static TypeStrategy TYPE = TypeStrategy.CRYSTALLIZE_AGGRESSIVE;

    public CurrentAndNextSeasonsEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE)) {
            int[] energiesToCrystallize = new int[EnergyType.values().length];
            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();

            if (Arrays.stream(playerEnergies).sum() > 0) {
                for (int i = 0; i < playerEnergies.length; i++) {
                    EnergyType type = EnergyType.values()[i];
                    if (StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                            false, false, true, true)) {
                        energiesToCrystallize[type.ordinal()] = playerEnergies[type.ordinal()];
                    }
                }
                if (Arrays.stream(energiesToCrystallize).sum() > 0) {
                    facadeIA.setEnergiesToCrystallizeF(energiesToCrystallize);
                    return GameplayChoice.CRYSTALLIZE;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
