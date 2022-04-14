package com.game.playerassets.ia.strategy.reroll;

import com.game.engine.SeasonType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;

/**
 * ne relance pas si le dé a la face avec des énergies de rang 2
 */
public class MaliceStrategyModerateEnergy extends AbstractMaliceStrategy{
    private final static int ID = 373;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_MODERATE;
    private final static int MINIMALAMOUNT = 2 ;
    public MaliceStrategyModerateEnergy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }


    @Override
    public Object act(FacadeIA facadeIA) {
        boolean choice = false;
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        int[] energiesOnDice = facadeIA.getDiceFaceF().getEnergiesAmount();

        int nbEnergy = Arrays.stream(energiesOnDice).sum();
        if( nbEnergy < MINIMALAMOUNT ){
            choice = true;
        }
        for(int i = 0 ; i < EnergyType.values().length; i++){
            if(energiesOnDice[i] == 2){
              int p = EnergyManager.getEnergyPrice(EnergyType.values()[i],currentSeason);
              if(p == 2){
                  choice = false;
              }
            }
        }
        return choice;

    }

    
}
