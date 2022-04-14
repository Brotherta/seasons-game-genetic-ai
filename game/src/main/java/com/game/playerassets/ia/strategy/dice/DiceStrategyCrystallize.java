package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie un dé de cristallisation, si les énergies du joueur lui sont inutiles.
 */
public class DiceStrategyCrystallize extends AbstractDiceStrategy {
    private final static int ID = 398;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public DiceStrategyCrystallize(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        int[] energies = facadeIA.getAmountOfEnergiesArrayF();
        for (int i = 0; i < EnergyType.values().length; i++) {
            if (energies[i] > 0 && !StrategyUtils.isMyEnergiesUseful(EnergyType.values()[i], facadeIA, false, true, false, false)){
                if(StrategyUtils.isMyEnergiesUseful(EnergyType.values()[i], facadeIA, false,false,true,false)){
                    for(Dice d : dices){
                        if(d.getActualFace().isSell()){
                            return d;
                        }
                    }
                }
            }

        }

        return getNextStrategy().canAct(facadeIA);
    }
}
