package com.game.playerassets.ia.strategy.returnInHand;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.List;

public class RandomReturnInHandStrategy  extends AbstractStrategy {

    private final static int ID = 432;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomReturnInHandStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();
        return cards.get(Util.getRandomInt(cards.size()));
    }
}
