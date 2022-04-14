package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie le choix d'un dé augmentant sa jauge d'invocation s'il en a besoin.
 */

public class DiceStrategyInvokeGauge extends AbstractDiceStrategy{
    private final static int ID = 392;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT; // TODO peut être faire un type
    public DiceStrategyInvokeGauge(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }


    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        if(facadeIA.hasEnoughGaugeF()){
            for(Dice d : dices){
                if(d.getActualFace().isInvocation()){
                    return d;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
