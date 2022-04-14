package com.game.playerassets.ia.strategy.gameplay.invocation.greatness;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyGreatness extends AbstractStrategy {
    private final static int CONFLICT = 7;

    public AbstractStrategyGreatness(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}
