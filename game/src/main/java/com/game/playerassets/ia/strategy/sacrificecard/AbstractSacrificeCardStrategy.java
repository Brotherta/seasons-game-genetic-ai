package com.game.playerassets.ia.strategy.sacrificecard;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractSacrificeCardStrategy extends AbstractStrategy {

    public AbstractSacrificeCardStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
}
