package com.game.playerassets.ia.strategy.gameplay.invocation.syllas;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoque Syllas dès que tout les joueurs ont au moins 1 carte pouvoir invoquée.
 */
public class SyllasInvocationModerate extends AbstractStrategySyllas {
    private final static int ID = 402;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_AGGRESSIVE;
    private final static int CARD_NUMBER = 10;

    public SyllasInvocationModerate(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF());

        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE) && facadeIA.canInvokeThisCardF(card)
                && !facadeIA.mustWaitF(CARD_NUMBER)) {

            List<Player> players = facadeIA.getPlayersF();
            for (Player p : players) {
                if (p.getFacadeIA().getInvokedCardsF().size() == 0) {
                    facadeIA.setMustWaitF(CARD_NUMBER);
                    return getNextStrategy().canAct(facadeIA);
                }
            }

            facadeIA.setCardToUseF(card);
            return GameplayChoice.INVOKE;
        }
        facadeIA.setMustWaitF(CARD_NUMBER);
        return getNextStrategy().canAct(facadeIA);
    }
}
