package com.game.playerassets.ia.strategy.gameplay.bonus.exchange;

import com.game.engine.card.Card;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Si le joueur a 2 énergies inutiles à l'invocation d'une carte et si on est en dernière année,
 * alors il utilise le bonus pour les remplacer par 2 énergies permettant de compléter le coût d'invocation d'une carte.
 */
public class ExchangeBonusUseless extends AbstractStrategyBonusExchange {
    private final static int ID = 368;
    private final static TypeStrategy TYPE = TypeStrategy.BONUS_MODERATE;

    public ExchangeBonusUseless(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        int[] energies = facadeIA.getAmountOfEnergiesArrayF();

        if (facadeIA.isLastF()
                && facadeIA.getYearF() > 2
                && choicesAvailable.contains(GameplayChoice.EXCHANGE_BONUS)
                && !choicesAvailable.contains(GameplayChoice.INVOKE)
                && Arrays.stream(energies).sum() >= 2
                && facadeIA.getBonusAmountF() > 2) {

            List<Card> cards = facadeIA.getCardsF();
            for (Card card : cards) {

                int[] cost = card.getEnergyCost();
                int[] missing = StrategyUtils.whatDoINeed(energies, cost);
                int missingTotal = Arrays.stream(missing).sum();

                if (missingTotal >= 1 && missingTotal <= 2) {
                    facadeIA.setChoosableEnergiesF(energies, 2);
                    int[] sacrifice = facadeIA.getMeF().chooseEnergiesToSacrifice();
                    int[] wanted;
                    int[] choosableEnergies;

                    // there is only one energy missing, so the player must choose an other one.
                    if (missingTotal == 1) {
                        int[] choosableNewEnergy = new int[]{1, 1, 1, 1};
                        facadeIA.setChoosableEnergiesF(choosableNewEnergy, 1);
                        int[] energyChosen = facadeIA.getMeF().chooseEnergies();
                        wanted = new int[4];
                        for (int i = 0; i < missing.length; i++) {
                            wanted[i] = missing[i] + energyChosen[i];
                        }
                    } else {
                        wanted = missing;
                    }

                    choosableEnergies = new int[]{
                            sacrifice[0], sacrifice[1], sacrifice[2], sacrifice[3],
                            wanted[0], wanted[1], wanted[2], wanted[3]
                    };
                    facadeIA.setChoosableEnergiesF(choosableEnergies, 2);
                    return GameplayChoice.EXCHANGE_BONUS;
                }
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
