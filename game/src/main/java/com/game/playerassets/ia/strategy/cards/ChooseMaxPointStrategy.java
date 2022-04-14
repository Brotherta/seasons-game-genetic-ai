package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Choisis la carte valant le plus de points parmi les cartes proposées .
 */
public class ChooseMaxPointStrategy extends AbstractCardStrategy{
    private final static int ID = 449;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChooseMaxPointStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        if(!facadeIA.isPreludeF()){
            ArrayList<Card> cards = facadeIA.getChoosableCardsF();
            return chooseMaxCard(cards);}
        else{
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
