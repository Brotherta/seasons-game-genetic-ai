package com.game.playerassets.ia.strategy.gameplay.combo;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Combo;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Si le joueur possède le dé de la malice et amsug longcoup.
 * L'objectif est d'invoquer ou de déjà avoir en jeu le dé,
 * pour ensuite invoquer Amsug, activer son effet,
 * renvoyer dans la main le dé et d'invoquer le dé à nouveau par la suite.
 */

public class AmsugMaliceCombo extends AbstractStrategy {

    private final static int ID = 458;
    private final static TypeStrategy TYPE = TypeStrategy.COMBO;
    private final static int AMSUG = 17;
    private final static int MALICE = 15;

    public AmsugMaliceCombo(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();

        List<Card> canActivateCards = facadeIA.getInvokedCardsF();
        Card malice = Card.getCardInList(MALICE, canActivateCards);

        List<Card> canInvokeCards = facadeIA.getInvokableCardsF();
        Card amsug = Card.getCardInList(AMSUG, canInvokeCards);

        if (malice != null) {
            if (amsug != null
                    && choicesAvailable.contains(GameplayChoice.INVOKE)) {
                Combo combo = facadeIA.createComboF();
                combo.addCardToQueue(amsug);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);
                combo.addCardToQueue(malice);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);
                facadeIA.setComboToUse(combo);
                return GameplayChoice.COMBO;
            }
        } else {
            malice = Card.getCardInList(MALICE, canInvokeCards);
            if (malice != null && amsug != null && choicesAvailable.contains(GameplayChoice.INVOKE)) {
                Combo combo = facadeIA.createComboF();
                combo.addCardToQueue(malice);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);
                combo.addCardToQueue(amsug);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);
                combo.addCardToQueue(malice);
                combo.addChoiceToQueue(GameplayChoice.INVOKE);
                facadeIA.setComboToUse(combo);
                return GameplayChoice.COMBO;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}

