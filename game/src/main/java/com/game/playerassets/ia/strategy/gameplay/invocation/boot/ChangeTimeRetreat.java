package com.game.playerassets.ia.strategy.gameplay.invocation.boot;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

/**
 * Choisis un pas de temps pour finir la partie en avance (fonctionne si nous sommes proche de la fin du jeu).
 */


public class ChangeTimeRetreat extends AbstractStrategyChangeTime {
    private final static int ID = 364;
    private final static int CARD_NUMBER = 7;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_RETREAT;
    private static final int YEAR = 3;
    private static final int MONTH = 10;
    private static final int STEPMAX = 3;

    public ChangeTimeRetreat(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        int year = facadeIA.getYearF();
        int month = facadeIA.getMonthF();
        if (year == YEAR && month >= MONTH) {
            return timeStrategy(this, STEPMAX, facadeIA, CARD_NUMBER);
        }
        return getNextStrategy().canAct(facadeIA);
    }

}
