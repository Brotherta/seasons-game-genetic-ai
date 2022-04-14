package com.game.playerassets.ia.strategy.givecards;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractGiveCardStrategy extends AbstractStrategy {
    public AbstractGiveCardStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
}
