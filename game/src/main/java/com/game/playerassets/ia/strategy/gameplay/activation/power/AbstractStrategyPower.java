package com.game.playerassets.ia.strategy.gameplay.activation.power;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyPower extends AbstractStrategy {
    private final static int CONFLICT = 3;

    public AbstractStrategyPower(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}
