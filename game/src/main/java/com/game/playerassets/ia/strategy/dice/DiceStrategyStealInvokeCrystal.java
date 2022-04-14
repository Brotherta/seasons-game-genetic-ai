package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.game.playerassets.observer.Container;

import java.util.List;

/**
 * Privilégie le choix d'un dé ayant un crystal d'invocation, si un autre joueur en a besoin.
 */
public class DiceStrategyStealInvokeCrystal extends AbstractDiceStrategy {
    private final static int ID = 395;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public DiceStrategyStealInvokeCrystal(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        List<Player> others = facadeIA.getObserverF().getContainer().getPlayers();
        Container container = facadeIA.getObserverF().getContainer();
        Dice selectedDice = null;
        for (Dice d : dices) {
            if (d.getActualFace().isInvocation()) {
                selectedDice = d;
                break;
            }
        }
        if (selectedDice != null) {
            for (Player p : others) {
                if (!p.getFacadeIA().hasEnoughGaugeF()) {
                    return selectedDice;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
