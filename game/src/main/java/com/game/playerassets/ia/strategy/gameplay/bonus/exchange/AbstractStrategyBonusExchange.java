package com.game.playerassets.ia.strategy.gameplay.bonus.exchange;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractStrategyBonusExchange extends AbstractStrategy {
    private final static int CONFLICT = 14;

    public AbstractStrategyBonusExchange(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }
}