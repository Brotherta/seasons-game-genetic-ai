package com.game.playerassets.ia.strategy.reroll;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

/**
 * ne relance pas si l'IA a besoin d'un point d'invocation et qu'il est sur la face du dé
 * /
 * relance si l'IA a besoin d'un point et qu'il n'est pas sur la face*
 */
public class MaliceStrategyModerateInvoke extends AbstractMaliceStrategy {
    private final static int ID = 374;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;


    public MaliceStrategyModerateInvoke(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        boolean choice = true ;
        int Gauge = facadeIA.getInvocationGaugeF();
        if(!facadeIA.hasEnoughGaugeF() && facadeIA.getDiceFaceF().isInvocation()){
            choice = false ;
        }
        return choice;
    }
}
