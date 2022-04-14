package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Stratégie de choix de dé en privilégiant les dés de crystallisation.
 */
public class SimpleDiceStrategy extends AbstractStrategy {

    private final static int ID = 6;
    private final static TypeStrategy TYPE = TypeStrategy.SIMPLE;

    public SimpleDiceStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        for (Dice d : dices) {
            if (d.getActualFace().isSell()) {
                return d;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
