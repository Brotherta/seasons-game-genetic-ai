package com.game.playerassets.ia.strategy.energy;

import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.Arrays;

/**
 * Stratégie aléatoire de choix d'énergies.
 */
public class RandomEnergyStrategy extends AbstractStrategy {

    private final static int ID = 7;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        int[] energyToChoose = facadeIA.getChoosableEnergiesF();
        int quantity = facadeIA.getNbChoosableEnergiesF();

        int energiesLeft = facadeIA.getAmountOfEnergiesLeftF();

        int[] energiesChosen = new int[EnergyType.values().length];

        if (Arrays.stream(energyToChoose).sum() < quantity) {
            return new int[4];
        }
        for (int i = 0; i < quantity; i++) {
            int energy = Util.getRandomInt(EnergyType.values().length);
            if (energyToChoose[energy] > 0) {
                energyToChoose[energy]--;
                energiesChosen[energy]++;
            } else {
                i--;
            }
        }

        return energiesChosen;
    }
}
