package com.game.playerassets.ia.strategy.returnInHand;

import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractReturnInHandStrategy extends AbstractStrategy {

    public AbstractReturnInHandStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
}
