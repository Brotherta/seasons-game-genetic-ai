package com.game.playerassets.ia.strategy.gameplay.bonus.gauge;

import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Lors d'un choix de gameplay, si le joueur n'a jamais utilisé de bonus,
 * qu'il peut invoquer une carte, mais que sa jauge n'est pas assez grande, il utilise le bonus.
 */

public class GaugeBonusStrategy extends AbstractStrategyBonusGauge {

    private final static int ID = 336;
    private final static TypeStrategy TYPE = TypeStrategy.BONUS_RETREAT;

    public GaugeBonusStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.INVOKE_BONUS)
                && facadeIA.getBonusAmountF() == 3
                && !facadeIA.hasEnoughGaugeF()
                && facadeIA.canInvokeAnyCardWithoutGaugeF()
                && facadeIA.isLastF()) {
            int nbCardsInvoked = facadeIA.getInvokedCardsF().size();
            for (Player player : facadeIA.getPlayersF()) {
                if (player.getFacadeIA().getInvokedCardsF().size() < nbCardsInvoked) {
                    return getNextStrategy().canAct(facadeIA);
                }
            }
            return GameplayChoice.INVOKE_BONUS;
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
