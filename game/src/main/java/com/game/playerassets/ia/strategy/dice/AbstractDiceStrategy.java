package com.game.playerassets.ia.strategy.dice;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

public class AbstractDiceStrategy extends AbstractStrategy {
    public AbstractDiceStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }


public int[] energyNeeds(FacadeIA facadeIA){
    int[] needs = new int[EnergyType.values().length];
    for(Card c : facadeIA.getCardsF()){
        int[] tempNeed = StrategyUtils.whatDoINeed(facadeIA.getAmountOfEnergiesArrayF(),c.getEnergyCost());
        for(int i = 0 ; i < EnergyType.values().length; i++){
            needs[i] += tempNeed[i];
        }
    }
    return needs;
}
}