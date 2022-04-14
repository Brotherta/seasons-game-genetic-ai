package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Choisis la première carte Single Effect trouvé parmi les cartes proposées pendant le prélude.
 */
public class ChooseSingleEffectPreludeStrategy extends AbstractCardStrategy{
    private final static int ID = 455;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChooseSingleEffectPreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        if (facadeIA.isPreludeF()) {
            ArrayList<Card> cards = facadeIA.getChoosableCardsF();
            for(Card c : cards){
                if(c.getEffect().getIsSingleEffect()){
                    return c;
                }
            }
            facadeIA.setChoosableCardsF(cards);
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
