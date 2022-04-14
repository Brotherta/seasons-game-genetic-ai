package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * Stratégie de choix aléatoire du dé.
 */

public class RandomDiceStrategy extends AbstractStrategy {

    private final static int ID = 5;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomDiceStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Dice> dices = facadeIA.getRolledDicesF();
        return dices.get(Util.getRandomInt(dices.size()));
    }
}
