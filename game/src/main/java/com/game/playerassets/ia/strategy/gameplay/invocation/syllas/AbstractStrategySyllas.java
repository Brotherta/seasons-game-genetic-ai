package com.game.playerassets.ia.strategy.gameplay.invocation.syllas;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategySyllas extends AbstractStrategy {
    private final static int CONFLICT = 8;

    public AbstractStrategySyllas(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}
