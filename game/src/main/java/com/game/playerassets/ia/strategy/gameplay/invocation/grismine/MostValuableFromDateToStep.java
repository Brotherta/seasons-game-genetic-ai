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
 * Retourne le joueur ayant le plus d'énergies dans le futur pour une date donné et un déplacement du dé donné.
 */

public class MostValuableFromDateToStep extends AbstractStrategyGrismine {

    private final static int ID = 332;
    private final static TypeStrategy TYPE = TypeStrategy.INVOKE_MODERATE;
    private final static int CARD_NUMBER = 21;

    public MostValuableFromDateToStep(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        Card card = Card.getCardInList(CARD_NUMBER, facadeIA.getCardsF());
        if (card != null && choicesAvailable.contains(GameplayChoice.INVOKE)
                && facadeIA.canInvokeThisCardF(card)
                && !facadeIA.mustWaitF(CARD_NUMBER)) {

            int month = facadeIA.getMonthF();
            int step = facadeIA.getBoardDiceFaceF().getDistance();
            SeasonType newSeason = Util.getSeasonsFromDateInNmonth(month, step);
            EnergyType mostValuableType = facadeIA.getMostValuableEnergyTypeF(newSeason);
            ArrayList<Player> players = facadeIA.getPlayersF();
            Player richest = Util.mostEnergyOfAType(mostValuableType, players);
            if (richest == null) {
                facadeIA.setMustWaitF(CARD_NUMBER);
                return getNextStrategy().canAct(facadeIA);
            } else {
                facadeIA.setCardToUseF(card);
                facadeIA.setPlayerToCopyF(richest);
                return GameplayChoice.INVOKE;
            }
        }
        return getNextStrategy().canAct(facadeIA);

    }

}
