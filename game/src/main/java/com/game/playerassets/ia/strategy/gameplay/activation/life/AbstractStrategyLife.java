package com.game.playerassets.ia.strategy.gameplay.activation.life;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

public class AbstractStrategyLife extends AbstractStrategy {
    private final static int CONFLICT = 5;

    public AbstractStrategyLife(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }

    @Override
    public int getConflicts() {
        return CONFLICT;
    }


    public static Object lifePotionStrategy(AbstractStrategy strategy,
                                            FacadeIA facadeIA,
                                            int[] playerEnergies,
                                            List<Card> canActivateCards,
                                            int LIFE_NB) {
        for (int i = 0; i < playerEnergies.length; i++) {
            EnergyType type = EnergyType.values()[i];
            if (StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                    false, true, false, false)) {
                return strategy.getNextStrategy().canAct(facadeIA);
            }
        }

        Card card = Card.getCardInList(LIFE_NB, canActivateCards);
        facadeIA.setCardToUseF(card);
        return GameplayChoice.ACTIVATE;
    }
}
