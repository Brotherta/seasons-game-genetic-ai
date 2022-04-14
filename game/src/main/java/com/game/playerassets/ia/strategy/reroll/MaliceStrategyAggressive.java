package com.game.playerassets.ia.strategy.reroll;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
/**
 * chosisi tout le temps d'utiliser son dé
 */
public class MaliceStrategyAggressive extends AbstractMaliceStrategy{
    private final static int ID = 343;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;
    private final static boolean CHOICE = true;
    public MaliceStrategyAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return CHOICE;
    }
}
