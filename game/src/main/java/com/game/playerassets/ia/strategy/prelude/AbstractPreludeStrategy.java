package com.game.playerassets.ia.strategy.prelude;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractPreludeStrategy extends AbstractStrategy {
    public AbstractPreludeStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
}
