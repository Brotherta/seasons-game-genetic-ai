package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Choisis la carte avec le plus faible coût parmi les cartes proposées durant le prélude. */
public class ChooseSmallCostPreludeStrategy extends AbstractCardStrategy{
    private final static int ID = 456;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChooseSmallCostPreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        if(facadeIA.isPreludeF()){
        List<Card> cards = facadeIA.getChoosableCardsF();
        return chooseMinimalCostStrategy(cards);}
        return getNextStrategy().canAct(facadeIA);
    }
}
