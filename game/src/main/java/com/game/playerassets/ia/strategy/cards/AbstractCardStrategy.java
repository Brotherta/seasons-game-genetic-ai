package com.game.playerassets.ia.strategy.cards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

public class AbstractCardStrategy extends AbstractStrategy {
    public AbstractCardStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        super(nextStrategy, id, type);
    }
    Card chooseMaxCard(List<Card> cards){
        Card MaxValueCard = cards.get(0);
        for (Card c: cards){
            if(c.getPoints() > MaxValueCard.getPoints()){
                MaxValueCard = c;
            }
        }
        return MaxValueCard;
    }

    Card chooseMinimalCostStrategy(List<Card> cards){
        Card minimal = null;
        int minimalCost = 16;
        for(Card c : cards){
            int curentPrice = Arrays.stream(c.getEnergyCost()).sum();
            if( curentPrice< minimalCost){
                minimal = c;
                minimalCost = curentPrice;
            }
        }
        return minimal;
    }
}
