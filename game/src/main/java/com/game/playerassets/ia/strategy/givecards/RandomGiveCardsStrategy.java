package com.game.playerassets.ia.strategy.givecards;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Choix aléatoire de distribution des cartes.
 */
public class RandomGiveCardsStrategy extends AbstractStrategy {

    private final static int ID = 10;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;

    public RandomGiveCardsStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<Player> players = facadeIA.getPlayersF();
        ArrayList<Card> cards = facadeIA.getChoosableCardsF();
        Collections.shuffle(players, Util.getR());
        Collections.shuffle(cards, Util.getR());
        HashMap<Player, Card> map = new HashMap<>();

        for (int i = 0; i < players.size(); i++) {
            map.put(players.get(i), cards.get(i));
        }

        return map;
    }
}
