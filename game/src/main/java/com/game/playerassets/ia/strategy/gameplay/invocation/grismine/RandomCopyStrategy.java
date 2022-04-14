package com.game.playerassets.ia.strategy.gameplay.invocation.grismine;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * Stratégie choisissant aléatoirement un joueur pour la copie du stock d'énergies.
 */

public class RandomCopyStrategy extends AbstractStrategy {

    private final static int ID = 4;
    private final static TypeStrategy TYPE = TypeStrategy.RANDOM;
    private final static int GRISMINE = 21;
    public RandomCopyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        ArrayList<Player> players = facadeIA.getPlayersF();
        if (choicesAvailable.contains(GameplayChoice.INVOKE) && Card.contains(GRISMINE, facadeIA.getCardsF())) {

            facadeIA.setPlayerToCopyF(players.get(Util.getRandomInt(players.size())));
            facadeIA.setCardToUseF(Card.getCardInList(GRISMINE, facadeIA.getCardsF()));
            return GameplayChoice.INVOKE;
        }
        facadeIA.setPlayerToCopyF(players.get(Util.getRandomInt(players.size())));
        return GameplayChoice.STOP_TURN;
    }
}
