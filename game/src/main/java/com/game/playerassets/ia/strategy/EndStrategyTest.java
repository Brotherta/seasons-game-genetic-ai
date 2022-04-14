package com.game.playerassets.ia.strategy;

import com.game.playerassets.ia.FacadeIA;

public class EndStrategyTest extends AbstractStrategy {
    private final static int ID = -1;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public EndStrategyTest(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return null;
    }
}
