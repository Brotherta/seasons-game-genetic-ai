package com.game.playerassets.ia.strategy.givecards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Privilégie la sélection d'une carte pour un jour ne pouvant pas invoquer cette carte.
 */
public class GiveCardCantInvokeStrategy extends AbstractGiveCardStrategy {
    private final static int ID = 428;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;


    public GiveCardCantInvokeStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Player> players = facadeIA.getPlayersF();
        ArrayList<Card> cardscopy = facadeIA.getChoosableCardsF();
        ArrayList<Card> cards = new ArrayList<>(cardscopy);
        HashMap<Player, Card> map = new HashMap<>();
        for (Player p : players) {
            for (Card c : cards) {
                if (!p.getFacadeIA().canInvokeThisCardF(c)) {
                    map.put(p, c);
                    cards.remove(c);
                    break;
                }
            }
        }
        if(cards.size() ==0){
         return map;
        }
        facadeIA.setChoosableCardsF(cardscopy);
        return getNextStrategy().canAct(facadeIA);
    }
}
