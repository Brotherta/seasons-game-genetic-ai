package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Choisis la carte avec le plus faible coût parmi les cartes proposées.
 */
public class ChooseSmallestCostStrategy extends AbstractCardStrategy{
    private final static int ID = 460;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChooseSmallestCostStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        if (!facadeIA.isPreludeF()) {
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
