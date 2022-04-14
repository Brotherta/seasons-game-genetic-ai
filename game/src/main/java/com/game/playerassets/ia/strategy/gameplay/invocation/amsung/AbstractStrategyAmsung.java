package com.game.playerassets.ia.strategy.gameplay.invocation.amsung;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyAmsung extends AbstractStrategy {
    private final static int CONFLICT = 10;

    public AbstractStrategyAmsung(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}