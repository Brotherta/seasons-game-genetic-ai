package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

/**
 * Stratégie aléatoire de choix du choix du temps.
 */
public class RandomChooseTime extends AbstractStrategy {
    private final static int ID = 3;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomChooseTime(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        int step = Util.getRandomInt(-3, 4);
        if (step == 0) step--;
        return step;
    }
}
