package com.game.playerassets.ia.strategy.prelude;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Choix en fonction du nombre de points des cartes lors du prélude.
 */
public class SimplePreludeStrategy extends AbstractStrategy {

    private final static int ID = 12;
    private final static TypeStrategy TYPE = TypeStrategy.SIMPLE;

    public SimplePreludeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getTemporaryHandF();
        cards.sort(Comparator.comparingInt(c -> Arrays.stream(c.getEnergyCost()).sum()));
        Stack<Card> cardStack = new Stack<>();
        for (Card card : cards) {
            cardStack.push(card);
        }
        return cardStack;
    }
}
