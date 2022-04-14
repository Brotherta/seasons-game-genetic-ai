package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * Stratégie simple en se basant sur le tier d'une carte pour la choisir.
 */
public class SimpleCardStrategy extends AbstractStrategy {
    private final static int ID = 2;
    private final static TypeStrategy TYPE = TypeStrategy.SIMPLE;

    public SimpleCardStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Card> cards = facadeIA.getChoosableCardsF();
        if (facadeIA.isPreludeF()) {
            return sortCard(cards).get(0);
        } else {
            return cards.get(Util.getRandomInt(cards.size()));
        }
    }

    public ArrayList<Card> sortCard(ArrayList<Card> cardToSort) {
        ArrayList<Integer> tier1 = new ArrayList<>();
        tier1.add(9);
        tier1.add(20);
        tier1.add(27);
        tier1.add(30);

        ArrayList<Card> copy = new ArrayList<>(cardToSort);
        ArrayList<Card> sortingCard = new ArrayList<>();
        for (Card c :
                cardToSort) {
            if (tier1.contains(c.getNumber())) {
                sortingCard.add(c);
                copy.remove(c);
            }
        }
        sortingCard.addAll(copy);
        return sortingCard;
    }
}
