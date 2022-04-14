package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie un dé "piocher une carte" si le joueur n'a plus de cartes en main.
 */
public class DiceStrategyDraw extends AbstractDiceStrategy{
    private final static int ID = 399;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public DiceStrategyDraw(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        if(facadeIA.getCardsF().size() == 0){
            List<Dice> dices = facadeIA.getRolledDicesF();
            for (Dice d : dices){
                if(d.getActualFace().isDrawCard()){
                    return d;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
