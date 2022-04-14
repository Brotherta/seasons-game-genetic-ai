package com.game.playerassets.ia.strategy.gameplay.bonus.gauge;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyBonusGauge extends AbstractStrategy {
    private final static int CONFLICT = 13;

    public AbstractStrategyBonusGauge(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}