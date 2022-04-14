package com.game.playerassets.ia.strategy.gameplay.bonus.crystallization;

import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.ArrayList;

/**
 * Utilise le bonus si et seulement si, l'IA possède une majorité (par rapport au nombre d'énergies max) d'énergies
 * de valeur max (3), qu'elles ne servent pas à l'activation/invocation, et qu'elle ne possède pas de dé
 * de cristallisation, et qu'elle est dernière en gains de cristaux, et qu'on n'est pas à l'année 1.
 */
public class CrystallizeBonusMax extends AbstractStrategyBonusCrystallization {

    private final static int ID = 360;
    private final static TypeStrategy TYPE = TypeStrategy.BONUS_RETREAT;
    private final static int NB_ENERGY = 4;

    public CrystallizeBonusMax(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {

        ArrayList<GameplayChoice> choicesAvailable = facadeIA.getGameplayChoiceF();
        if (facadeIA.getYearF() > 1
                && !facadeIA.getDiceFaceF().isSell()
                && facadeIA.isLastF()
                && choicesAvailable.contains(GameplayChoice.CRYSTALLIZE_BONUS)
                && facadeIA.getBonusAmountF() > 2) {

            int[] myEnergies = facadeIA.getAmountOfEnergiesArrayF();
            EnergyType type = facadeIA.getMostValuableEnergyTypeF(facadeIA.getCurrentSeasonsF());
            if (myEnergies[type.ordinal()] >= NB_ENERGY
                    && !StrategyUtils.isMyEnergiesUseful(type, facadeIA, true, true, false, false)) {

                int[] toCrystallize = new int[4];
                toCrystallize[type.ordinal()] = myEnergies[type.ordinal()];
                facadeIA.setEnergiesToCrystallizeF(toCrystallize);
                return GameplayChoice.CRYSTALLIZE_BONUS;
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
