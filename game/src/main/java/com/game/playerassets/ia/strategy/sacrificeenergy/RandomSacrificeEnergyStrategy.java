package com.game.playerassets.ia.strategy.sacrificeenergy;

import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

/**
 * Choix aléatoire des énergies à sacrifier.
 */
public class RandomSacrificeEnergyStrategy extends AbstractStrategy {

    private final static int ID = 15;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomSacrificeEnergyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        int[] energyToSacrifice = facadeIA.getChoosableEnergiesF();
        int quantity = facadeIA.getNbChoosableEnergiesF();

        int[] energiesChosen = new int[EnergyType.values().length];

        for (int i = 0; i < quantity; i++) {
            int energy = Util.getRandomInt(EnergyType.values().length);
            if (energyToSacrifice[energy] > 0) {
                energyToSacrifice[energy]--;
                energiesChosen[energy]++;
            } else {
                i--;
            }
        }
        return energiesChosen;
    }
}