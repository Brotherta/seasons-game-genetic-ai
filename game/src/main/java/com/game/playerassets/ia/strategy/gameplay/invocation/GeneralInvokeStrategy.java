package com.game.playerassets.ia.strategy.gameplay.invocation;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Stratégie d'invocation générale invoquant une carte,
 * si elle est disponible et non mise en attente
 * par une autre stratégie.
 */

public class GeneralInvokeStrategy extends AbstractStrategy {

    private final static int ID = 421;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public GeneralInvokeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }


    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        List<Card> cards  = facadeIA.getInvokableCardsF();
        if (choicesAvailable.contains(GameplayChoice.INVOKE)) {
            for(Card c : cards){
                if( !facadeIA.mustWaitF(c.getNumber())){
                    facadeIA.setCardToUseF(c);
                    return GameplayChoice.INVOKE;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
