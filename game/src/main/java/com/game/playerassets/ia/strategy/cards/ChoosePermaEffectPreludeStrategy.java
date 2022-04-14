package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Choisis la première carte à effet Permanent trouvé parmi les cartes proposées pendant le prélude.
 */
public class ChoosePermaEffectPreludeStrategy extends AbstractCardStrategy {
    private final static int ID = 454;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChoosePermaEffectPreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        if (facadeIA.isPreludeF()) {
            ArrayList<Card> cards = facadeIA.getChoosableCardsF();
            for(Card c : cards){
                if(c.getEffect().getIsPermanentEffect()){
                    return c;
                }
            }
            facadeIA.setChoosableCardsF(cards);
        }
        return getNextStrategy().canAct(facadeIA);

    }
}
