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
 *Stratégie privilégiant la cristallisation des énergies ne servant ni à l'invocation ni à l'activation.
 */

public class UselessEnergyStrategy extends AbstractCrystallizationStrategy{

    private final static int ID = 390;
    private final static TypeStrategy TYPE = TypeStrategy.CRYSTALLIZE_MODERATE;

    public UselessEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
//        System.out.printf(".");
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE)) {
            int[] playerEnergies = facadeIA.getAmountOfEnergiesArrayF();
            int[] energiesToCrystallize = new int[EnergyType.values().length];
            for (int i = 0; i < playerEnergies.length; i++) {
                EnergyType type = EnergyType.values()[i];
                if (StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                        true, false, false, false)) {
                    return getNextStrategy().canAct(facadeIA);
                } else if (StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                        false, true, false, false)) {
                    continue;
                }
                energiesToCrystallize[type.ordinal()] = playerEnergies[type.ordinal()];
            }
            if (Arrays.stream(energiesToCrystallize).sum() == 0) {
                return getNextStrategy().canAct(facadeIA);
            }
            facadeIA.setEnergiesToCrystallizeF(energiesToCrystallize);
            return GameplayChoice.CRYSTALLIZE;
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
