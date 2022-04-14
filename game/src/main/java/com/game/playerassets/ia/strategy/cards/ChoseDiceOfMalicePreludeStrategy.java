package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Choisis le dé de la Malice parmi les cartes proposées pendant le prélude.
 */
public class ChoseDiceOfMalicePreludeStrategy extends AbstractCardStrategy{
    private final static int ID = 461;
    private final static int DICE_OF_MALICE = 15;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    public ChoseDiceOfMalicePreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        if(facadeIA.isPreludeF()){
        List<Card> cards = facadeIA.getChoosableCardsF();
        Card card = Card.getCardInList(DICE_OF_MALICE, cards);
        if(card != null){
            return card;
        }
        else{
            facadeIA.setChoosableCardsF(cards);
            return getNextStrategy().canAct(facadeIA);
        }}
        return getNextStrategy().canAct(facadeIA);
    }
}
