package com.game.playerassets.ia.strategy.givecards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Donne la cartes, valant le moins de point à celui ayant le plus de cristaux, etc.
 */
public class GiveCardLesValuableToFirst extends AbstractGiveCardStrategy{
    private final static int ID = 436;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public GiveCardLesValuableToFirst(Strategy nextStrategy) {
        super(nextStrategy, ID , TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Player> players = facadeIA.getPlayersF();
        ArrayList<Card> cardscopy = facadeIA.getChoosableCardsF();
        ArrayList<Card> cards = new ArrayList<>(cardscopy);
        HashMap<Player, Card> map = new HashMap<>();
        Player first = players.get(0);
        for (Player p : players){
            if(p.getFacadeIA().getCrystalF() > first.getFacadeIA().getCrystalF()){
                first = p;
            }
        }
        Card lessValue = cards.get(0);
        for(Card c : cards){
            if(c.getPoints() < lessValue.getPoints()){
                lessValue = c;
            }
        }
        cards.remove(lessValue);
        map.put(first,lessValue);
        players.remove(first);
        for(Player p: players){
            map.put(p,cards.get(0));
            cards.remove(0);
        }

        return map;
    }
}
