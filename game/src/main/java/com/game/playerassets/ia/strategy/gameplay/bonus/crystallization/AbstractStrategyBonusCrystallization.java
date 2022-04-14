package com.game.playerassets.ia.strategy.gameplay.bonus.crystallization;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyBonusCrystallization extends AbstractStrategy {
    private final static int CONFLICT = 11;

    public AbstractStrategyBonusCrystallization(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}