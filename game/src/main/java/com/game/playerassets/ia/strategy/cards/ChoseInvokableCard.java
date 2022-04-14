package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Choisis la carte qu'il peut invoquer en priorité, sinon passe a la stratégie suivante.
 */
public class ChoseInvokableCard extends AbstractCardStrategy{
    private final static int ID = 450;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public ChoseInvokableCard(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        if(!facadeIA.isPreludeF()){
            List<Card> cards = facadeIA.getChoosableCardsF();
            Card selected = null;
            for(Card c : cards ){
                if(facadeIA.canInvokeThisCardF(c)){
                    selected = c ;
                    break;
                }
            }
            if(selected == null){
                facadeIA.setChoosableCardsF(cards);
                return getNextStrategy().canAct(facadeIA);
            }
            return selected;
        }
        else{
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
