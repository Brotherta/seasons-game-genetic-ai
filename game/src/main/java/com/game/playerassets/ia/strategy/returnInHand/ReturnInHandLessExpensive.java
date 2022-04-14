package com.game.playerassets.ia.strategy.returnInHand;


import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Ne concerne que les cartes de type objet magique retour dans la main de la carte ayant le coup le plus faible.
 */

public class ReturnInHandLessExpensive extends AbstractReturnInHandStrategy {

    private final static int ID = 425;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public ReturnInHandLessExpensive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getChoosableCardsF();

        if (!cards.isEmpty()) {

            int nbCard = 0;
            int[] cost = new int[EnergyType.values().length];

            for (Card card : cards) {
                if (Arrays.stream(card.getEnergyCost()).sum() <= Arrays.stream(cost).sum()) {
                    cost = card.getEnergyCost();
                    nbCard = card.getNumber();
                }
            }

            if (nbCard != 0) {
                return Card.getCardInList(nbCard, cards);
            }
        }
        facadeIA.setChoosableCardsF(facadeIA.MagicObjectInvokedF());
        return getNextStrategy().canAct(facadeIA);
    }

}
