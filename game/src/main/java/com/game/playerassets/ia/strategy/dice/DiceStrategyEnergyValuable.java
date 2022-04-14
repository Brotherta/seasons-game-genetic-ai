package com.game.playerassets.ia.strategy.dice;

import com.game.engine.SeasonType;
import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie le choix du dé comportant les énergies les plus valables.
 */
public class DiceStrategyEnergyValuable extends AbstractDiceStrategy{
    private final static int ID = 393;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public DiceStrategyEnergyValuable(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();

        Dice mostValuableDice = null;
        int maxNbEnergy = 0;
        for(Dice d : dices ){
            int[] diceEnergies = d.getActualFace().getEnergiesAmount();
            for(int i = 0 ; i < EnergyType.values().length; i++){
                if(diceEnergies[i] > maxNbEnergy && EnergyManager.getEnergyPrice(EnergyType.values()[i],currentSeason) == 2){
                    mostValuableDice = d;
                }
            }
        }

        if(mostValuableDice != null){
            return mostValuableDice;
        }
        return getNextStrategy().canAct(facadeIA);

    }
}
