package com.game.playerassets.ia.strategy.gameplay.bonus.draw;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Si le joueur n'a pas de cartes en main et qu'on n'est pas en dernière année, il utilise le bonus,
 * et s'il est le joueur avec le moins de cartes invoqué et qu'il est dernier en cristaux.
 */
public class DrawBonusNoCard extends AbstractStrategyBonusDraw {
    private final static int ID = 363;
    private final static TypeStrategy TYPE = TypeStrategy.BONUS_AGGRESSIVE;

    public DrawBonusNoCard(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.DRAW_CARD_WITH_BONUS)
                && facadeIA.isLastF()
                && facadeIA.getBonusAmountF() > 2) {
            if (facadeIA.getCardsF().size() == 0 && facadeIA.getYearF() != 3) {
                int nbCardsInvoked = facadeIA.getInvokedCardsF().size();
                for (Player player : facadeIA.getPlayersF()) {
                    if (player.getFacadeIA().getInvokedCardsF().size() < nbCardsInvoked) {
                        return getNextStrategy().canAct(facadeIA);
                    }
                }
                return GameplayChoice.DRAW_CARD_WITH_BONUS;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
