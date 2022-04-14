package com.game.playerassets.ia.strategy.energy;

import com.game.engine.SeasonType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import static com.game.playerassets.ia.strategy.energy.EnergyPriceSellNextSeason.EnergyStrategyUtil;


/**
 * Choisi des énergies qui se vendent le plus cher pour la saison courante.
 */

public class EnergyPriceSellCurrentSeason extends AbstractStrategy {
    private final static int ID = 416;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public EnergyPriceSellCurrentSeason(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        return EnergyStrategyUtil(facadeIA, currentSeason);
    }
}

