package com.game.playerassets.ia.strategy.gameplay;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;
import ia.IAType;

import java.util.ArrayList;
import java.util.List;

public class RandomGameplayStrategy extends AbstractStrategy {

    private final static int ID = 9;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomGameplayStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        // If he can invoke
        if (choicesAvailable.contains(GameplayChoice.INVOKE)) {
            Card card = StrategyUtils.getRandomInvokableCard(facadeIA);
            facadeIA.setCardToUseF(card);
            return GameplayChoice.INVOKE; // will invoc everytime)
        }

        // If he can crystallize
        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE)) {
            facadeIA.setEnergiesToCrystallizeF(StrategyUtils.getRandomCrystallize(facadeIA));
            return GameplayChoice.CRYSTALLIZE; // (will crystallize everytime)
        }

        // if he can draw card but at the same time the bonus is available
        if (choicesAvailable.contains(GameplayChoice.DRAW_CARD) && choicesAvailable.contains(GameplayChoice.DRAW_CARD_WITH_BONUS)) {
            int rdm = Util.getRandomInt(10); // 1/10 probability to draw with bonus
            if (rdm == 1) {
                return GameplayChoice.DRAW_CARD_WITH_BONUS;
            } else {
                return GameplayChoice.DRAW_CARD;
            }
        }

        // else if there is juste the draw card available but no bonus
        else if (choicesAvailable.contains(GameplayChoice.DRAW_CARD)) {
            return GameplayChoice.DRAW_CARD;
        }

        if (facadeIA.getMeF().getType() != IAType.COMPOSE_IA && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {

            List<Card> cardToActivate = facadeIA.getActivableCardsF();

            Card cardReturn = StrategyUtils.getRandomCard(cardToActivate);
            facadeIA.setCardToUseF(cardReturn);
            return GameplayChoice.ACTIVATE;
        }


        if (choicesAvailable.contains(GameplayChoice.CRYSTALLIZE_BONUS)) {

            int rdm = Util.getRandomInt(12); // But this time with 1/12 probability
            if (rdm == 1) {
                int r = Util.getRandomInt(3);
                switch (r) {
                    case 0:
                        return GameplayChoice.INVOKE_BONUS;
                    case 1: {
                        facadeIA.setEnergiesToCrystallizeF(StrategyUtils.getRandomCrystallize(facadeIA));
                        return GameplayChoice.CRYSTALLIZE_BONUS;
                    }
                    case 2: {
                        facadeIA.setChoosableEnergiesF(StrategyUtils.getRandomExchange(facadeIA), 2);
                        return GameplayChoice.EXCHANGE_BONUS;
                    }
                }
            }
        }

        /* If tThere is only SOP_TURN and all USE_BONUS (ex : "[STOP_TURN; USE_BONUS; USE_BONUS; USE_BONUS;]")
        then the IA will just stop the turn */
        if (choicesAvailable.contains(GameplayChoice.STOP_TURN) && choicesAvailable.size() == facadeIA.getBonusAmountF() + 1) {
            return GameplayChoice.STOP_TURN;
        }

        // Global else
        return GameplayChoice.STOP_TURN;
    }
}
