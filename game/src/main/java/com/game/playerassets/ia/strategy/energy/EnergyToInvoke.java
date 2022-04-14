package com.game.playerassets.ia.strategy.energy;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.AbstractStrategy;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Choisi les énergies servant à invoquer des cartes.
 */

public class EnergyToInvoke extends AbstractStrategy {
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    private final static int ID = 415;


    public EnergyToInvoke(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        SeasonType currentSeason = facadeIA.getCurrentSeasonsF();
        int[] myEnergies = facadeIA.getAmountOfEnergiesArrayF();

        // On récupère toutes les cartes de la main actuelles et des cartes de l'année prochaine.
        List<Card> cards = facadeIA.getAllCardF();
        int[] maxEnergies = new int[4];
        int nbMax = 0;
        for (Card card : cards) {
            int[] energiesMissing = StrategyUtils.whatDoINeed(myEnergies, card.getEnergyCost());
            int nbMissing = Arrays.stream(energiesMissing).sum();

            // Si le nombre d'énergies manquantes de la carte sont supérieurs au max précédent, on met à jour.
            if (nbMissing > nbMax) {
                maxEnergies = energiesMissing;
                nbMax = nbMissing;
            }
        }

        // Si on a trouvé une carte où il nous manque des énergies, alors on va les récupérer.
        // Sinon on passe à la stratégie suivante.
        if (nbMax != 0) {
            facadeIA.getChoosableEnergiesF(); // pas nécessaire, mais je les fetch pour qu'il n'en reste pas dedans.
            int quantity = facadeIA.getNbChoosableEnergiesF();
            int[] energyChosen = new int[4];

            // Si la quantité d'énergies manquantes est inférieure à la quantité d'énergies souhaitée,
            // Alors ont choisi des énergies dans les prochaines stratégies,
            // et on les ajoute aux énergies manquantes de la carte.
            if (nbMax < quantity) {
                facadeIA.setChoosableEnergiesF(new int[4], quantity - nbMax);
                int[] energiesRemaining = (int[]) getNextStrategy().canAct(facadeIA);

                for (int i = 0; i < EnergyType.values().length; i++) {
                    energyChosen[i] += energiesRemaining[i];
                }
                quantity -= quantity - nbMax;
            }

            // On parcourt les énergies manquantes de la carte, et tant qu'on a besoin d'énergies,
            // on récupère celles de la carte.
            for (int i = 0; i < EnergyType.values().length; i++) {
                // Si on a encore de la place, je récupère des énergies, sinon je sors de la boucle.
                if (quantity > 0) {
                    // Si le slot de la carte contient plus d'énergies que la place que j'ai,
                    // alors je récupère uniquement la quantité voulue,
                    // sinon, je récupère toutes les énergies du slot.
                    if (quantity < maxEnergies[i]) {
                        energyChosen[i] += quantity;
                        quantity = 0;
                    } else {
                        energyChosen[i] += maxEnergies[i];
                        quantity -= maxEnergies[i];
                    }
                } else {
                    break;
                }
            }
            return energyChosen;
        } else {
            return getNextStrategy().canAct(facadeIA);
        }
    }
}
