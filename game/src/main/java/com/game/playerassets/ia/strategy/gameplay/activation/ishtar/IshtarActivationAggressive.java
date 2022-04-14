package com.game.playerassets.ia.strategy.gameplay.activation.ishtar;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Activation de la carte dès que le joueur possède 3 énergies dans son stock.
 */
public class IshtarActivationAggressive extends AbstractStrategyIshtar {

    private final static int ID = 357;
    private final static TypeStrategy TYPE = TypeStrategy.ACTIVATE_AGGRESSIVE;
    private final static int ISHTAR_NB = 5;

    public IshtarActivationAggressive(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(ISHTAR_NB, canActivateCards);


        if (card != null && choicesAvailable.contains(GameplayChoice.ACTIVATE)) {
            int[] energiesBy3 = facadeIA.getEnergiesRepeatedF(3);
            boolean check = false;
            for (int i = 0; i < energiesBy3.length; i++) {
                if (energiesBy3[i] >= 3) {
                    check = true;
                    break;
                }
            }
            if (check) {
                facadeIA.setCardToUseF(card);
                return GameplayChoice.ACTIVATE;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }

}