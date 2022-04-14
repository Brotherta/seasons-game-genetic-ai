package com.game.playerassets.ia.strategy.gameplay.invocation.naria;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyNaria extends AbstractStrategy {
    private final static int CONFLICT = 9;

    public AbstractStrategyNaria(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}