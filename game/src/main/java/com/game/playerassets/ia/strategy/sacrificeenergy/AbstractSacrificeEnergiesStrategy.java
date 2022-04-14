package com.game.playerassets.ia.strategy.sacrificeenergy;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractSacrificeEnergiesStrategy extends AbstractStrategy {

    public AbstractSacrificeEnergiesStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
}
