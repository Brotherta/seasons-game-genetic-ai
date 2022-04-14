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
 * Stratégie privilégiant la copy du joueur ayant le plus d'énergie rapportant le plus pour les saisons suivantes.
 **/

public class NextValueCopyStrategy extends AbstractStrategyGrismine {

    private final static int ID = 329;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_MODERATE;
    private final static int CARD_NUMBER = 21;

    public NextValueCopyStrategy(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        if (choicesAvailable.contains(GameplayChoice.INVOKE) && Card.contains(CARD_NUMBER, facadeIA.getCardsF())
                && facadeIA.canInvokeThisCardF(Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF()))
                && !facadeIA.mustWaitF(CARD_NUMBER)) {

            SeasonType nextSeason = Util.getNextSeasons(facadeIA.getCurrentSeasonsF());
            EnergyType nextMostValuableType = facadeIA.getMostValuableEnergyTypeF(nextSeason);
            ArrayList<Player> players = facadeIA.getPlayersF();
            Player richest = Util.mostEnergyOfAType(nextMostValuableType, players);

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
