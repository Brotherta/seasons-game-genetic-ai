package com.game.playerassets.ia;

import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.strategy.cards.*;
import com.game.playerassets.ia.strategy.dice.*;
import com.game.playerassets.ia.strategy.energy.EnergyPriceSellCurrentSeason;
import com.game.playerassets.ia.strategy.energy.EnergyPriceSellNextSeason;
import com.game.playerassets.ia.strategy.energy.EnergyToInvoke;
import com.game.playerassets.ia.strategy.energy.RandomEnergyStrategy;
import com.game.playerassets.ia.strategy.freeinvocation.FreeEnergyAndCrystals;
import com.game.playerassets.ia.strategy.freeinvocation.FreeMostExpensive;
import com.game.playerassets.ia.strategy.freeinvocation.FreeMostExpensiveCrystals;
import com.game.playerassets.ia.strategy.freeinvocation.RandomInvocationFreeStrategy;
import com.game.playerassets.ia.strategy.gameplay.EndGameplayStrategy;
import com.game.playerassets.ia.strategy.gameplay.RandomGameplayStrategy;
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
import ia.IAType;

public class IAFactory {
    private static final IAFactory INSTANCE = new IAFactory();

    private IAFactory() {
    }

    public static IAFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Generates by default IA with random strategy.
     */

    public Player generate(IAType iaType, int playerNb, PersonalBoard pb, FacadeIA f) {
        IA ia = new IA(iaType, iaType.name() + String.format("%d", playerNb), f, playerNb);

        switch (iaType) {
            case COMPOSE_IA -> {
                ia.setCardStrategy(
                        new ChoseDiceOfMalicePreludeStrategy(
                                new ChooseSmallCostPreludeStrategy(
                                        new ChooseSingleEffectPreludeStrategy(
                                                new ChoosePermaEffectPreludeStrategy(
                                                        new ChoseInvokableCard(
                                                                new ChooseSmallestCostStrategy(
                                                                        new ChooseMaximumPointForPrelude(
                                                                                new ChooseMaxPointStrategy(
                                                                                        new RandomCardStrategy(null))))))))));
                ia.setDiceStrategy(
                        new DiceStrategySiceOfMaliceMaximiseCrystal(
                                new DiceStrategyDIcesOfMaliceInvoke(
                                        new DiceStrategyDiceOfMaliceCrystalize(
                                                new DiceStrategyDraw(
                                                        new DiceStrategyEnergyToInvoke(
                                                                new DiceStrategyInvokeGauge(
                                                                        new DiceStrategyEnergyValuable(
                                                                                new DiceStrategyStealInvokeCrystal(
                                                                                        new DiceStrategyInvokeAndEnergy(
                                                                                                new DiceStrategyCrystallize(
                                                                                                        new RandomDiceStrategy(null))))))))))));

                ia.setGameplayStrategy(
                        new DreamPotionActivationModerate(
                                new DreamPotionActivationAggressive(
                                        new DreamPotionActivationRetreat(
                                                new IshtarActivationModerate(
                                                        new IshtarActivationRetreat(
                                                                new IshtarActivationAggressive(
                                                                        new KairnActivationModerate(
                                                                                new KairnActivationRetreat(
                                                                                        new KnowledgeActivationModerate(
                                                                                                new KnowledgeActivationRetreat(
                                                                                                        new LifePotionActivationModerate(
                                                                                                                new LifePotionActivationAggressive(
                                                                                                                        new KairnActivationAggressive(
                                                                                                                                new LifePotionActivationRetreat(
                                                                                                                                        new PowerPotionActivationModerate(
                                                                                                                                                new PowerPotionActivationRetreat(
                                                                                                                                                        new PowerPotionActivationAggressive(
                                                                                                                                                                new AmsugMaliceCombo(
                                                                                                                                                                        new GrismineLifeMegaCombo(
                                                                                                                                                                                new CurrentAndNextSeasonsEnergyStrategy(
                                                                                                                                                                                        new LeastValuableEnergyStrategy(
                                                                                                                                                                                                new MostInStockEnergyStrategy(
                                                                                                                                                                                                        new MostValuableEnergyStrategy(
                                                                                                                                                                                                                new UselessEnergyStrategy(
                                                                                                                                                                                                                        new ChangeTimeAggressiveBackward(
                                                                                                                                                                                                                                new ChangeTimeAggressiveForward(
                                                                                                                                                                                                                                        new ChangeTimeModerate(
                                                                                                                                                                                                                                                new ChangeTimeRetreat(
                                                                                                                                                                                                                                                        new MostValuableFromDateToStep(
                                                                                                                                                                                                                                                                new MostValueCopyStrategy(
                                                                                                                                                                                                                                                                        new NextValueCopyStrategy(
                                                                                                                                                                                                                                                                                new NariaInvocationAggressive(
                                                                                                                                                                                                                                                                                        new NariaInvocationModerateThirdYear(
                                                                                                                                                                                                                                                                                                new PriorityPermanentInvocation(
                                                                                                                                                                                                                                                                                                        new DrawBonusNoCard(
                                                                                                                                                                                                                                                                                                                new ExchangeBonusUseless(
                                                                                                                                                                                                                                                                                                                        new GaugeBonusStrategy(
                                                                                                                                                                                                                                                                                                                                new CrystallizeBonusMax(
                                                                                                                                                                                                                                                                                                                                        new SyllasInvocationAggressive(
                                                                                                                                                                                                                                                                                                                                                new SyllasInvocationModerate(
                                                                                                                                                                                                                                                                                                                                                        new GeneralInvokeStrategy(
                                                                                                                                                                                                                                                                                                                                                                new PermanentWaitInvocation(
                                                                                                                                                                                                                                                                                                                                                                        new EndGameplayStrategy(null)
                                                                                                                                                                                                                                                                                                                                                                )))))))))))))))))))))))))))))))))))))))))));

                ia.setSacrificeEnergyStrategy(
                        new SacrificeUselessForInvoke(
                                new SacrificeWorthLessToSell(
                                        new RandomSacrificeEnergyStrategy(null))));
                ia.setPreludeStrategy(
                        new PreludeStrategyPermanentEffectLastYear(
                                new PreludeStrategyPermanentEffect(
                                        new PreludeStrategyPermanentEffectSecondYear(
                                                new PreludeStrategySEFYAndPLY(
                                                        new PreludeStrategySingleEffectFirstYear(
                                                                new RandomPreludeStrategy(null)))))));
                ia.setSacrificeCardStrategy(
                        new SacrificeLessPoint(
                                new SacrificeSingleEffect(
                                        new RandomSacrificeCardStrategy(null))));
                ia.setReturnInHandStrategy(
                        new ReturnInHandLessExpensive(
                                new ReturnInHandSingleEffect(
                                        new RandomReturnInHandStrategy(null))));
                ia.setInvocationFreeStrategy(
                        new FreeEnergyAndCrystals(
                                new FreeMostExpensive(
                                        new FreeMostExpensiveCrystals(
                                                new FreeMostExpensive(
                                                        new RandomCardStrategy(null))))));
                ia.setEnergyStrategy(
                        new EnergyToInvoke(
                                new EnergyPriceSellCurrentSeason(
                                        new EnergyPriceSellNextSeason(
                                                new RandomEnergyStrategy(null)))));
                ia.setCopyStrategy(
                        new MostValuableFromDateToStep(
                                new MostValueCopyStrategy(
                                        new NextValueCopyStrategy(
                                                new RandomCopyStrategy(null)))));
                ia.setGiveCards(
                        new GiveCardCantInvokeStrategy(
                                new GiveCardLesValuableToFirst(
                                        new RandomGiveCardsStrategy(null))));
                ia.setRerollStrategy(
                        new MaliceStrategyModerateInvoke(
                                new MaliceStrategyAggressive(
                                        new MaliceStrategyModerateEnergy(
                                                new RandomRerollStrategy(null)))));
                ia.setChangeTimeStrategy(
                        new ChangeTimeAggressiveBackward(
                                new ChangeTimeAggressiveForward(
                                        new ChangeTimeModerate(
                                                new ChangeTimeRetreat(
                                                        new RandomChooseTime(null))))));
            }

            case SIMPLE_IA -> {
                ia.setCardStrategy(new SimpleCardStrategy(new RandomCardStrategy(null)));
                ia.setDiceStrategy(new RandomDiceStrategy(null));
                ia.setGameplayStrategy(new RandomGameplayStrategy(null));
                ia.setPreludeStrategy(new SimplePreludeStrategy(new RandomPreludeStrategy(null)));
                ia.setSacrificeEnergyStrategy(new RandomSacrificeEnergyStrategy(null));
                ia.setSacrificeCardStrategy(new RandomSacrificeCardStrategy(null));
                ia.setReturnInHandStrategy(new RandomReturnInHandStrategy(null));
                ia.setInvocationFreeStrategy(new RandomInvocationFreeStrategy(null));
                ia.setEnergyStrategy(new RandomEnergyStrategy(null));
                ia.setCopyStrategy(new RandomCopyStrategy(null));
                ia.setGiveCards(new RandomGiveCardsStrategy(null));
                ia.setRerollStrategy(new RandomRerollStrategy(null));
                ia.setChangeTimeStrategy(new RandomChooseTime(null));

            }

            case RANDOM_IA -> {
                ia.setCardStrategy(new RandomCardStrategy(null));
                ia.setDiceStrategy(new RandomDiceStrategy(null));
                ia.setGameplayStrategy(new RandomGameplayStrategy(null));
                ia.setPreludeStrategy(new RandomPreludeStrategy(null));
                ia.setSacrificeEnergyStrategy(new RandomSacrificeEnergyStrategy(null));
                ia.setSacrificeCardStrategy(new RandomSacrificeCardStrategy(null));
                ia.setReturnInHandStrategy(new RandomReturnInHandStrategy(null));
                ia.setInvocationFreeStrategy(new RandomInvocationFreeStrategy(null));
                ia.setEnergyStrategy(new RandomEnergyStrategy(null));
                ia.setCopyStrategy(new RandomCopyStrategy(null));
                ia.setRerollStrategy(new RandomRerollStrategy(null));
                ia.setGiveCards(new RandomGiveCardsStrategy(null));
                ia.setChangeTimeStrategy(new RandomChooseTime(null));
            }
        }

        return ia;
    }
}