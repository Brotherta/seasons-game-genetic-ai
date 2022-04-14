package com.game.playerassets.ia.strategy.gameplay.bonus.draw;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyBonusDraw extends AbstractStrategy {
    private final static int CONFLICT = 12;

    public AbstractStrategyBonusDraw(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}