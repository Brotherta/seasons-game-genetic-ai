package com.game.playerassets.ia.strategy.gameplay.invocation.grismine;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyGrismine extends AbstractStrategy {
    private final static int CONFLICT = 15;

    public AbstractStrategyGrismine(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }

}
