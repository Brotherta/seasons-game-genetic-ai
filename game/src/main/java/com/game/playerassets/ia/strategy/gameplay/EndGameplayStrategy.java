package com.game.playerassets.ia.strategy.gameplay;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class EndGameplayStrategy extends AbstractStrategy {
    private final static int ID = 451;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public EndGameplayStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return GameplayChoice.STOP_TURN;
    }
}
