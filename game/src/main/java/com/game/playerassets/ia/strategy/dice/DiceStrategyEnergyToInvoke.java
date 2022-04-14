package com.game.playerassets.ia.strategy.dice;

import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie le choix du dé ayant des énergies pouvant lui permettre d'invoquer.
 */
public class DiceStrategyEnergyToInvoke extends AbstractDiceStrategy{
    private final static int ID = 394;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public DiceStrategyEnergyToInvoke(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Dice> dices = facadeIA.getRolledDicesF();
        // check what you need to invoke your cards and look if there are some dices that has some energies youn need
        int[] needs = energyNeeds(facadeIA);
        // once ours needs are expressed let's check the dices that have some energies for us
        Dice maxDice = null;
        int max = 0;
        for(Dice d : dices){
            int[] energiesOnDice = d.getActualFace().getEnergiesAmount();
            int accUseful = 0;
            for(int i = 0 ; i < EnergyType.values().length; i++){
                if(needs[i] > 0 && energiesOnDice[i] > 0){
                    accUseful += energiesOnDice[i];
                }
            }
            if(accUseful > max){
                maxDice = d;
                max = accUseful;
            }
        }


        if(maxDice != null){
            return maxDice;
        }
        else{
        return getNextStrategy().canAct(facadeIA);}
    }
}
