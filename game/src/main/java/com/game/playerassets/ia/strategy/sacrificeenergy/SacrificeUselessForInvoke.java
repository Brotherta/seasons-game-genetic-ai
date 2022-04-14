package com.game.playerassets.ia.strategy.sacrificeenergy;

import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;


/**
 * Sacrifie les énergies ne servant pas à l'invocation d'une carte.
 */
public class SacrificeUselessForInvoke extends AbstractSacrificeEnergiesStrategy {

    private final static int ID = 437;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;

    public SacrificeUselessForInvoke(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        int[] playerEnergies = facadeIA.getChoosableEnergiesF();
        int quantity = facadeIA.getNbChoosableEnergiesF();

        int[] energiesToSacrifice = new int[EnergyType.values().length];
        for (int i = 0; i < playerEnergies.length; i++) {
            EnergyType type = EnergyType.values()[i];

            if (quantity > 0) {
                int nbEnergyOfType = playerEnergies[type.ordinal()];

                if (nbEnergyOfType > 0
                        && !StrategyUtils.isMyEnergiesUseful(type, facadeIA,
                        false, true, false, false)) {

                    if (nbEnergyOfType <= quantity) {
                        energiesToSacrifice[type.ordinal()] = nbEnergyOfType;
                        playerEnergies[type.ordinal()] = 0;
                        quantity -= nbEnergyOfType;
                    } else {
                        energiesToSacrifice[type.ordinal()] = quantity;
                        playerEnergies[type.ordinal()] -= quantity;
                        quantity = 0;
                        break;
                    }
                }
            } else { // on n'a plus d'énergies à sacrifier, on break la boucle.
                break;
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
    }
}
