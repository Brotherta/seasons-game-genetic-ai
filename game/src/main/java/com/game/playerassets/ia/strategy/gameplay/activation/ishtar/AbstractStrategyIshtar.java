package com.game.playerassets.ia.strategy.gameplay.activation.ishtar;

import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyIshtar extends AbstractStrategy {
    private final static int CONFLICT = 0;

    public AbstractStrategyIshtar(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }

    protected static EnergyType ishtarActivationUtils(FacadeIA facadeIA) {
        int[] energiesBy3 = facadeIA.getEnergiesRepeatedF(3);
        facadeIA.setChoosableEnergiesF(energiesBy3, 1);
        EnergyType type = EnergyType.FIRE;
        int[] energyChosen = facadeIA.getMeF().chooseEnergiesToSacrifice();
        for (int i = 0; i < energyChosen.length; i++) {
            if (energyChosen[i] > 0) {
                type = EnergyType.values()[i];
                break;
            }
        }
        return type;
    }
}
