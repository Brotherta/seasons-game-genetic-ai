package com.game.playerassets.ia.strategy.energy;

import com.game.engine.SeasonType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

/**
 * Choisi des énergies qui se vendent le plus cher pour la saison suivante.
 */

public class EnergyPriceSellNextSeason extends AbstractStrategy {
    private final static int ID = 417;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public EnergyPriceSellNextSeason(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        SeasonType nextSeason = Util.getNextSeasons(currentSeason);
        return EnergyStrategyUtil(facadeIA, nextSeason);
    }

    public static Object EnergyStrategyUtil(FacadeIA facadeIA, SeasonType nextSeason) {
        facadeIA.getChoosableEnergiesF();
        int quantity = facadeIA.getNbChoosableEnergiesF();

        EnergyType chosen = null;
        for (EnergyType e : EnergyType.values()) {
            if (EnergyManager.getEnergyPrice(e, nextSeason) == 3) {
                chosen = e;
            }
        }

        int[] energyReturned = new int[4];
        energyReturned[chosen.ordinal()] = quantity;
        return energyReturned;
    }

}
