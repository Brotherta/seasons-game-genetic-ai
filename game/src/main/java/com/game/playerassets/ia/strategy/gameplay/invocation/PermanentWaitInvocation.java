package com.game.playerassets.ia.strategy.gameplay.invocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

public class PermanentWaitInvocation extends AbstractStrategy {
    private static final TypeStrategy TYPE = TypeStrategy.DEFAULT;
    private static final int ID = 405;

    public PermanentWaitInvocation(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return getNextStrategy().act(facadeIA);
    }
}

