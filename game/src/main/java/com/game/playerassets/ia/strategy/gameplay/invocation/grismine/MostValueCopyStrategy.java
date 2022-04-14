package com.game.playerassets.ia.strategy.gameplay.invocation.grismine;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;

/**
 * Stratégie privilégiant la copie du joueur ayant le plus d'énergies rapportant le plus pour la saison courante.
 **/


public class MostValueCopyStrategy extends AbstractStrategyGrismine {

    private final static int ID = 328;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_MODERATE;
    private final static int CARD_NUMBER = 21;

    public MostValueCopyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (choicesAvailable.contains(GameplayChoice.INVOKE) && Card.contains(CARD_NUMBER, facadeIA.getCardsF())
                && !facadeIA.mustWaitF(CARD_NUMBER)) {

            SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
            EnergyType mostValuableType = facadeIA.getMostValuableEnergyTypeF(currentSeason);
            ArrayList<Player> players = facadeIA.getPlayersF();
            Player richest = Util.mostEnergyOfAType(mostValuableType, players);
            if (richest == null) {
                facadeIA.setMustWaitF(CARD_NUMBER);
                return getNextStrategy().canAct(facadeIA);
            } else {
                facadeIA.setCardToUseF(Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF()));
                facadeIA.setPlayerToCopyF(richest);
                return GameplayChoice.INVOKE;
            }
        }
        facadeIA.setMustWaitF(CARD_NUMBER);
        return getNextStrategy().canAct(facadeIA);
    }


}
