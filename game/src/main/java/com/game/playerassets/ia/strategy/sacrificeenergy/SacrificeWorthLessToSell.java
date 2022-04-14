package com.game.playerassets.ia.strategy.sacrificeenergy;

import com.game.engine.SeasonType;
import com.game.engine.energy.Energy;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;
import com.utils.Util;


/**
 * Sacrifie les énergies qui ont le moins de valeurs à la cristallisation.
 */
public class SacrificeWorthLessToSell extends AbstractSacrificeEnergiesStrategy {

    private final static int ID = 438;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public SacrificeWorthLessToSell(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        int[] playerEnergies = facadeIA.getChoosableEnergiesF();
        int quantity = facadeIA.getNbChoosableEnergiesF();

        int[] energiesToSacrifice = new int[EnergyType.values().length];
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        EnergyType leastValuableEnergyType1 = new Energy(currentSeason).getEnergyType();
        EnergyType leastValuableEnergyType2 = new Energy(Util.getPreviousSeasons(currentSeason)).getEnergyType();

        int nbEnergyOfType1 = playerEnergies[leastValuableEnergyType1.ordinal()];
        int nbEnergyOfType2 = playerEnergies[leastValuableEnergyType2.ordinal()];

        if (nbEnergyOfType1 > 0 || nbEnergyOfType2 > 0) {

            if (nbEnergyOfType1 > 0) {
                if (nbEnergyOfType1 <= quantity) {
                    energiesToSacrifice[leastValuableEnergyType1.ordinal()] = nbEnergyOfType1;
                    playerEnergies[leastValuableEnergyType1.ordinal()] = 0;
                    quantity -= nbEnergyOfType1;
                } else {
                    energiesToSacrifice[leastValuableEnergyType1.ordinal()] = quantity;
                    playerEnergies[leastValuableEnergyType1.ordinal()] -= quantity;
                    quantity = 0;
                }
            }

            if (nbEnergyOfType2 > 0 && quantity > 0) {
                if (nbEnergyOfType2 <= quantity) {
                    energiesToSacrifice[leastValuableEnergyType2.ordinal()] = nbEnergyOfType2;
                    playerEnergies[leastValuableEnergyType2.ordinal()] = 0;
                    quantity -= nbEnergyOfType2;
                } else {
                    energiesToSacrifice[leastValuableEnergyType2.ordinal()] = quantity;
                    playerEnergies[leastValuableEnergyType2.ordinal()] -= quantity;
                    quantity = 0;
                }
            }

            if (quantity > 0) {
                facadeIA.setChoosableEnergiesF(playerEnergies, quantity);
                int[] energiesRemaining = (int[]) getNextStrategy().canAct(facadeIA);

                for (int i = 0; i < EnergyType.values().length; i++) {
                    energiesToSacrifice[i] += energiesRemaining[i];
                }
            }
            return energiesToSacrifice;

        } else {
            facadeIA.setChoosableEnergiesF(playerEnergies, quantity);
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
