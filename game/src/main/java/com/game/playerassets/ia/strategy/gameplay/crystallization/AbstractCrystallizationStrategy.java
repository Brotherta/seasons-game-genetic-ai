package com.game.playerassets.ia.strategy.gameplay.crystallization;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractCrystallizationStrategy extends AbstractStrategy {

    private final static int CONFLICT = 16;

    public AbstractCrystallizationStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}
