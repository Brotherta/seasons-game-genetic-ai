package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 *Privilégie un dé ayant un cristal d'invocation, ainsi que des énergies nécessaires à l'invocation/activation de cartes.
 */
public class DiceStrategyInvokeAndEnergy extends AbstractDiceStrategy {
    private final static int ID = 397;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public DiceStrategyInvokeAndEnergy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        int[] needs = energyNeeds(facadeIA);
        Dice selection = null;
        int max = 0;
        for (Dice d : dices) {
            if (d.getActualFace().isInvocation()) {
                int[] energiesOnDice = d.getActualFace().getEnergiesAmount();
                int accUseful = 0;
                for (int i = 0; i < EnergyType.values().length; i++) {
                    if (needs[i] > 0 && energiesOnDice[i] > 0) {
                        accUseful += energiesOnDice[i];
                    }
                }
                if (accUseful > max) {
                    selection = d;
                    max = accUseful;
                }
            }
        }
        if(selection != null){
            return selection;
        }


        return getNextStrategy().canAct(facadeIA);
    }
}
