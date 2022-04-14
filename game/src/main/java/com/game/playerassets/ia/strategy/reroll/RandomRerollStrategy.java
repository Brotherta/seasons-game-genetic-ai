package com.game.playerassets.ia.strategy.reroll;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

/**
 * Choix aléatoire de la relance du dé.
 */
public class RandomRerollStrategy extends AbstractStrategy {

    private final static int ID = 13;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomRerollStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return Util.getRandomBoolean();
    }
}
