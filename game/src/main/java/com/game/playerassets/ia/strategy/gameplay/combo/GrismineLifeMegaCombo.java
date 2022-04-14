package com.game.playerassets.ia.strategy.gameplay.combo;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Combo;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * S'il peut invoquer Grismine et sacrifier la potion de vie,
 * il invoque puis sacrifie la potion de vie pour tout cristalliser.
 * Vérifie s'il n'a pas déjà toutes ses énergies.
 */
public class GrismineLifeMegaCombo extends AbstractStrategy {

    private final static int ID = 384;
    private final static TypeStrategy TYPE = TypeStrategy.COMBO;
    private final static int GRISMINE = 21;
    private final static int LIFE = 26;

    public GrismineLifeMegaCombo(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card life = Card.getCardInList(LIFE, canActivateCards);

        List<Card> canInvokeCards = facadeIA.getCardsF();
        Card grismine = Card.getCardInList(GRISMINE, canInvokeCards);

        if (life != null && grismine != null
                && choicesAvailable.contains(GameplayChoice.ACTIVATE)
                && facadeIA.canInvokeThisCardF(grismine)
                && facadeIA.getAmountOfEnergiesLeftF() > 2) {

            Player richest = Util.getRichest(facadeIA);
            if (Arrays.stream(richest.getFacadeIA().getAmountOfEnergiesArrayF()).sum() > 1) {
                Combo combo = facadeIA.createComboF();
                combo.addCardToQueue(grismine);
                facadeIA.setPlayerToCopyF(richest);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);

                combo.addCardToQueue(life);
                combo.addChoiceToQueue(GameplayChoice.ACTIVATE);
                facadeIA.setComboToUse(combo);
                return GameplayChoice.COMBO;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
