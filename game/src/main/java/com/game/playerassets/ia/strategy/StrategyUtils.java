package com.game.playerassets.ia.strategy;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.cards.*;
import com.game.playerassets.ia.strategy.dice.*;
import com.game.playerassets.ia.strategy.energy.EnergyPriceSellCurrentSeason;
import com.game.playerassets.ia.strategy.energy.EnergyPriceSellNextSeason;
import com.game.playerassets.ia.strategy.energy.EnergyToInvoke;
import com.game.playerassets.ia.strategy.energy.RandomEnergyStrategy;
import com.game.playerassets.ia.strategy.freeinvocation.*;
import com.game.playerassets.ia.strategy.gameplay.EndGameplayStrategy;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.dream.DreamPotionActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.ishtar.IshtarActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.ishtar.IshtarActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.ishtar.IshtarActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.kairn.KairnActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.knowledge.KnowledgeActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.knowledge.KnowledgeActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.life.LifePotionActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationAggressive;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationModerate;
import com.game.playerassets.ia.strategy.gameplay.activation.power.PowerPotionActivationRetreat;
import com.game.playerassets.ia.strategy.gameplay.bonus.crystallization.CrystallizeBonusMax;
import com.game.playerassets.ia.strategy.gameplay.bonus.draw.DrawBonusNoCard;
import com.game.playerassets.ia.strategy.gameplay.bonus.exchange.ExchangeBonusUseless;
import com.game.playerassets.ia.strategy.gameplay.bonus.gauge.GaugeBonusStrategy;
import com.game.playerassets.ia.strategy.gameplay.combo.AmsugMaliceCombo;
import com.game.playerassets.ia.strategy.gameplay.combo.GrismineLifeMegaCombo;
import com.game.playerassets.ia.strategy.gameplay.crystallization.*;
import com.game.playerassets.ia.strategy.gameplay.invocation.GeneralInvokeStrategy;
import com.game.playerassets.ia.strategy.gameplay.invocation.PermanentWaitInvocation;
import com.game.playerassets.ia.strategy.gameplay.invocation.boot.*;
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.MostValuableFromDateToStep;
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.MostValueCopyStrategy;
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.NextValueCopyStrategy;
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.RandomCopyStrategy;
import com.game.playerassets.ia.strategy.gameplay.invocation.naria.NariaInvocationAggressive;
import com.game.playerassets.ia.strategy.gameplay.invocation.naria.NariaInvocationModerateThirdYear;
import com.game.playerassets.ia.strategy.gameplay.invocation.priority.PriorityPermanentInvocation;
import com.game.playerassets.ia.strategy.gameplay.invocation.syllas.SyllasInvocationAggressive;
import com.game.playerassets.ia.strategy.gameplay.invocation.syllas.SyllasInvocationModerate;
import com.game.playerassets.ia.strategy.givecards.GiveCardCantInvokeStrategy;
import com.game.playerassets.ia.strategy.givecards.GiveCardLesValuableToFirst;
import com.game.playerassets.ia.strategy.givecards.RandomGiveCardsStrategy;
import com.game.playerassets.ia.strategy.prelude.*;
import com.game.playerassets.ia.strategy.reroll.MaliceStrategyAggressive;
import com.game.playerassets.ia.strategy.reroll.MaliceStrategyModerateEnergy;
import com.game.playerassets.ia.strategy.reroll.MaliceStrategyModerateInvoke;
import com.game.playerassets.ia.strategy.reroll.RandomRerollStrategy;
import com.game.playerassets.ia.strategy.returnInHand.RandomReturnInHandStrategy;
import com.game.playerassets.ia.strategy.returnInHand.ReturnInHandLessExpensive;
import com.game.playerassets.ia.strategy.returnInHand.ReturnInHandSingleEffect;
import com.game.playerassets.ia.strategy.sacrificecard.RandomSacrificeCardStrategy;
import com.game.playerassets.ia.strategy.sacrificecard.SacrificeLessPoint;
import com.game.playerassets.ia.strategy.sacrificecard.SacrificeSingleEffect;
import com.game.playerassets.ia.strategy.sacrificeenergy.RandomSacrificeEnergyStrategy;
import com.game.playerassets.ia.strategy.sacrificeenergy.SacrificeUselessForInvoke;
import com.game.playerassets.ia.strategy.sacrificeenergy.SacrificeWorthLessToSell;
import com.utils.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StrategyUtils {

    private final static List<Integer> cardWithEnergyCost;

    static {
        cardWithEnergyCost = List.of(5, 16, 26);
    }

    /**
     * Référence les conflits entre les stratégies similaires.
     * À ajouter dans le bon type lors de la création.
     * Clé : type de stratégie (conflits)
     * entrées : liste des identifiants des cartes concernées.
     */
    private static Map<Integer, int[]> conflictMap;

    static {
        conflictMap = Map.ofEntries(
                Map.entry(1, new int[]{3, 364, 365, 366, 367}), // Temporal Boots:
                Map.entry(2, new int[]{}), // Kairn activation
                Map.entry(3, new int[]{347, 346, 345}), // Power sacrifice
                Map.entry(4, new int[]{}), // Knowledge sacrifice
                Map.entry(5, new int[]{354, 355, 356}), // Life sacrifice
                Map.entry(6, new int[]{}), // Dream sacrifice
                Map.entry(7, new int[]{}),// Greatness invocation
                Map.entry(8, new int[]{}), // Syllas invocation
                Map.entry(9, new int[]{}), // Naria invocation
                Map.entry(10, new int[]{}), // Amsung invocation
                Map.entry(11, new int[]{360}), // Crystallization bonus
                Map.entry(12, new int[]{363}), // Draw bonus
                Map.entry(13, new int[]{336}), // Gauge bonus
                Map.entry(14, new int[]{368}), // Exchange bonus
                Map.entry(15, new int[]{331, 357, 358})); // Ishtar activation
    }

    public static Card getRandomCard(List<Card> cards) {
        Collections.shuffle(cards);
        return cards.get(0);
    }

    public static Card getRandomInvokableCard(FacadeIA facadeIA) {
        List<Card> cards = facadeIA.getCardsF();
        for (Card c : cards) {
            if (facadeIA.canInvokeThisCardF(c)) {
                return c;
            }
        }
        return null;
    }

    public static int[] getRandomCrystallize(FacadeIA facadeIA) {
        int[] crystallise = facadeIA.getAmountOfEnergiesArrayF();
        int[] res = new int[EnergyType.values().length];
        int quantity = Arrays.stream(crystallise).sum() / 2;

        if (quantity == 0) {
            quantity = Arrays.stream(crystallise).sum();
        }

        for (int i = 0; i < quantity; i++) {
            int energy = Util.getRandomInt(EnergyType.values().length);
            if (crystallise[energy] > 0) {
                crystallise[energy]--;
                res[energy]++;
            }
        }

        return res;
    }

    public static int[] whatDoINeed(int[] myEnergies, int[] cost) {
        int[] missing = new int[EnergyType.values().length];
        for (int i = 0; i < myEnergies.length; i++) {
            if (cost[i] > myEnergies[i]) {
                missing[i] = cost[i] - myEnergies[i];
            }
        }
        return missing;
    }

    public static int[] getRandomExchange(FacadeIA facadeIA) {
        int[] fromRandoms = facadeIA.chooseRandomsEnergiesF(2);
        int[] toRandoms = generateRandomArray(2);
        if (Arrays.stream(facadeIA.getAmountOfEnergiesArrayF()).sum() < 2)
            return new int[]{0, 0, 0, 0, 0, 0, 0, 0};

        return new int[]{fromRandoms[0], fromRandoms[1], fromRandoms[2], fromRandoms[3], toRandoms[0], toRandoms[1], toRandoms[2], toRandoms[3]};
    }

    /**
     * Checks if the energies are useful in what you wanted to check with the according boolean.
     *
     * @param type         type of energy to check.
     * @param facadeIA     the facade of the current player.
     * @param activation   if useful to the activation of any card
     * @param invocation   if useful to the invocation of any card
     * @param crystal_now  if useful to crystallize at the current season
     * @param crystal_next if useful to crystallize at the next season
     * @return a boolean if all conditions are passed.
     */
    public static boolean isMyEnergiesUseful(EnergyType type, FacadeIA facadeIA, boolean activation, boolean invocation, boolean crystal_now, boolean crystal_next) {

        if (activation) {
            List<Card> activableCards = facadeIA.getActivableCardsF();
            for (Card card : activableCards) {
                if (cardWithEnergyCost.contains(card.getNumber())) {
                    return true;
                }
            }
        }
        if (invocation) {
            List<Card> invokableCards = facadeIA.getAllCardF();
            for (Card card : invokableCards) {
                int[] cost = card.getEnergyCost();
                if (cost[type.ordinal()] > 0) {
                    return true;
                }
            }
        }
        if (crystal_now) {
            SeasonType seasonType = facadeIA.getCurrentSeasonsF();
            if (facadeIA.getMostValuableEnergyTypeF(seasonType).equals(type)) {
                return true;
            }
        }
        if (crystal_next) {
            SeasonType seasonType = facadeIA.getCurrentSeasonsF();
            seasonType = Util.getNextSeasons(seasonType);
            return facadeIA.getMostValuableEnergyTypeF(seasonType).equals(type);
        }
        return false;
    }

    private static int[] generateRandomArray(int amount) {
        int[] randomArray = new int[EnergyType.values().length];

        for (int i = 0; i < amount; i++) {
            randomArray[Util.getRandomInt(4)]++;
        }

        return randomArray;
    }
}
