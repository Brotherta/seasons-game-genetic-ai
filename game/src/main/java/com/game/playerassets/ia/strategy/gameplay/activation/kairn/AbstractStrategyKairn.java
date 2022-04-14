package com.game.playerassets.ia.strategy.gameplay.activation.kairn;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyKairn extends AbstractStrategy {
    private final static int CONFLICT = 2;

    public AbstractStrategyKairn(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}
