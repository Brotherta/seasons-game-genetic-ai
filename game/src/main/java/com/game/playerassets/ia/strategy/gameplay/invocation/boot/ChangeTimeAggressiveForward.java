package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

/**
 * Choisis le pas le plus loin dans le futur
 */
public class ChangeTimeAggressiveForward extends AbstractStrategyChangeTime {
    private final static int ID = 366;

    private final static int CARD_NUMBER = 7;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_AGGRESSIVE;
    private static final int STEPMAX = 3;

    public ChangeTimeAggressiveForward(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return timeStrategy(this, STEPMAX, facadeIA, CARD_NUMBER);
    }

}
