package com.game.playerassets.ia.strategy;


import com.game.playerassets.PersonalBoard;
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
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.*;
import com.game.playerassets.ia.strategy.gameplay.invocation.naria.*;
import com.game.playerassets.ia.strategy.gameplay.invocation.priority.PriorityPermanentInvocation;
import com.game.playerassets.ia.strategy.gameplay.invocation.syllas.*;
import com.game.playerassets.ia.strategy.givecards.*;
import com.game.playerassets.ia.strategy.prelude.*;
import com.game.playerassets.ia.strategy.reroll.*;
import com.game.playerassets.ia.strategy.reroll.RandomRerollStrategy;
import com.game.playerassets.ia.strategy.returnInHand.*;
import com.game.playerassets.ia.strategy.sacrificecard.*;
import com.game.playerassets.ia.strategy.sacrificeenergy.*;

import java.util.List;

public class StrategyFactory {

    private static final StrategyFactory INSTANCE = new StrategyFactory();

    private StrategyFactory() {
    }

    public static StrategyFactory getInstance() {
        return INSTANCE;
    }

    public Strategy buildStrategy(int id, Strategy nextStrategy) {
        Strategy returned = null;
        switch (id) {
            case 1 -> returned =  new RandomCardStrategy(nextStrategy);
            case 2 -> returned =  new SimpleCardStrategy(nextStrategy);
            case 3 -> returned =  new RandomChooseTime(nextStrategy);
            case 4 -> returned = new RandomCopyStrategy(nextStrategy);
            case 5 -> returned = new RandomDiceStrategy(nextStrategy);
            case 6 -> returned = new SimpleDiceStrategy(nextStrategy);
            case 7 -> returned = new RandomEnergyStrategy(nextStrategy);
            case 8 -> returned = new RandomInvocationFreeStrategy(nextStrategy);
            case 10 -> returned = new RandomGiveCardsStrategy(nextStrategy);
            case 11 -> returned = new RandomPreludeStrategy(nextStrategy);
            case 12 -> returned = new SimplePreludeStrategy(nextStrategy);
            case 13 -> returned = new RandomRerollStrategy(nextStrategy);
            case 14 -> returned = new RandomSacrificeCardStrategy(nextStrategy);
            case 15 -> returned = new RandomSacrificeEnergyStrategy(nextStrategy);
            case 328 -> returned = new MostValueCopyStrategy(nextStrategy);
            case 329 -> returned = new NextValueCopyStrategy(nextStrategy);
            case 331 -> returned = new IshtarActivationModerate(nextStrategy);
            case 332 -> returned = new MostValuableFromDateToStep(nextStrategy);
            case 335 -> returned = new MostValuableEnergyStrategy(nextStrategy);
            case 336 -> returned = new GaugeBonusStrategy(nextStrategy);
            case 340 -> returned = new KairnActivationAggressive(nextStrategy);
            case 341 -> returned = new KairnActivationModerate(nextStrategy);
            case 343 -> returned = new MaliceStrategyAggressive(nextStrategy);
            case 344 -> returned = new KairnActivationRetreat(nextStrategy);
            case 345 -> returned = new PowerPotionActivationAggressive(nextStrategy);
            case 346 -> returned = new PowerPotionActivationModerate(nextStrategy);
            case 347 -> returned = new PowerPotionActivationRetreat(nextStrategy);
            case 348 -> returned = new KnowledgeActivationModerate(nextStrategy);
            case 349 -> returned = new KnowledgeActivationRetreat(nextStrategy);
            case 350 -> returned = new DreamPotionActivationAggressive(nextStrategy);
            case 351 -> returned = new DreamPotionActivationModerate(nextStrategy);
            case 352 -> returned = new DreamPotionActivationRetreat(nextStrategy);
            case 354 -> returned = new LifePotionActivationAggressive(nextStrategy);
            case 355 -> returned = new LifePotionActivationModerate(nextStrategy);
            case 356 -> returned = new LifePotionActivationRetreat(nextStrategy);
            case 357 -> returned = new IshtarActivationAggressive(nextStrategy);
            case 358 -> returned = new IshtarActivationRetreat(nextStrategy);
            case 360 -> returned = new CrystallizeBonusMax(nextStrategy);
            case 363 -> returned = new DrawBonusNoCard(nextStrategy);
            case 364 -> returned = new ChangeTimeRetreat(nextStrategy);
            case 365 -> returned = new ChangeTimeModerate(nextStrategy);
            case 366 -> returned = new ChangeTimeAggressiveForward(nextStrategy);
            case 367 -> returned = new ChangeTimeAggressiveBackward(nextStrategy);
            case 368 -> returned = new ExchangeBonusUseless(nextStrategy);
            case 373 -> returned = new MaliceStrategyModerateEnergy(nextStrategy);
            case 374 -> returned = new MaliceStrategyModerateInvoke(nextStrategy);
            case 384 -> returned = new GrismineLifeMegaCombo(nextStrategy);
            case 389 -> returned = new LeastValuableEnergyStrategy(nextStrategy);
            case 390 -> returned = new UselessEnergyStrategy(nextStrategy);
            case 392 -> returned = new DiceStrategyInvokeGauge(nextStrategy);
            case 393 -> returned = new DiceStrategyEnergyValuable(nextStrategy);
            case 394 -> returned = new DiceStrategyEnergyToInvoke(nextStrategy);
            case 395 -> returned = new DiceStrategyStealInvokeCrystal(nextStrategy);
            case 397 -> returned = new DiceStrategyInvokeAndEnergy(nextStrategy);
            case 398 -> returned = new DiceStrategyCrystallize(nextStrategy);
            case 399 -> returned = new DiceStrategyDraw(nextStrategy);
            case 400 -> returned = new SyllasInvocationAggressive(nextStrategy);
            case 401 -> returned = new NariaInvocationModerateThirdYear(nextStrategy);
            case 402 -> returned = new SyllasInvocationModerate(nextStrategy);
            case 403 -> returned = new NariaInvocationAggressive(nextStrategy);
            case 405 -> returned = new PermanentWaitInvocation(nextStrategy);
            case 407 -> returned = new MostInStockEnergyStrategy(nextStrategy);
            case 408 -> returned = new PriorityPermanentInvocation(nextStrategy);
            case 409 -> returned = new CurrentAndNextSeasonsEnergyStrategy(nextStrategy);
            case 415 -> returned = new EnergyToInvoke(nextStrategy);
            case 416 -> returned = new EnergyPriceSellCurrentSeason(nextStrategy);
            case 417 -> returned = new EnergyPriceSellNextSeason(nextStrategy);
            case 421 -> returned = new GeneralInvokeStrategy(nextStrategy);
            case 422 -> returned = new SacrificeLessPoint(nextStrategy);
            case 423 -> returned = new SacrificeSingleEffect(nextStrategy);
            case 424 -> returned = new ReturnInHandSingleEffect(nextStrategy);
            case 425 -> returned = new ReturnInHandLessExpensive(nextStrategy);
            case 426 -> returned = new FreeMostExpensive(nextStrategy);
            case 427 -> returned = new FreeMostPointCard(nextStrategy);
            case 428 -> returned = new GiveCardCantInvokeStrategy(nextStrategy);
            case 429 -> returned = new DiceStrategyDIcesOfMaliceInvoke(nextStrategy);
            case 430 -> returned = new DiceStrategyDiceOfMaliceCrystalize(nextStrategy);
            case 432 -> returned = new RandomReturnInHandStrategy(nextStrategy);
            case 433 -> returned = new DiceStrategySiceOfMaliceMaximiseCrystal(nextStrategy);
            case 436 -> returned = new GiveCardLesValuableToFirst(nextStrategy);
            case 437 -> returned = new SacrificeUselessForInvoke(nextStrategy);
            case 438 -> returned = new SacrificeWorthLessToSell(nextStrategy);
            case 440 -> returned = new PreludeStrategyPermanentEffectLastYear(nextStrategy);
            case 441 -> returned = new PreludeStrategyPermanentEffect(nextStrategy);
            case 443 -> returned = new PreludeStrategyPermanentEffectSecondYear(nextStrategy);
            case 444 -> returned = new FreeEnergyAndCrystals(nextStrategy);
            case 445 -> returned = new FreeMostExpensiveCrystals(nextStrategy);
            case 446 -> returned = new PreludeStrategySingleEffectFirstYear(nextStrategy);
            case 447 -> returned = new PreludeStrategySEFYAndPLY(nextStrategy);
            case 448 -> returned = new ChooseMaximumPointForPrelude(nextStrategy);
            case 449 -> returned = new ChooseMaxPointStrategy(nextStrategy);
            case 450 -> returned = new ChoseInvokableCard(nextStrategy);
            case 451 -> returned = new EndGameplayStrategy(nextStrategy);
            case 454 -> returned = new ChoosePermaEffectPreludeStrategy(nextStrategy);
            case 455 -> returned = new ChooseSingleEffectPreludeStrategy(nextStrategy);
            case 456 -> returned = new ChooseSmallCostPreludeStrategy(nextStrategy);
            case 458 -> returned = new AmsugMaliceCombo(nextStrategy);
            case 460 -> returned = new ChooseSmallestCostStrategy(nextStrategy);
            case 461 -> returned = new ChoseDiceOfMalicePreludeStrategy(nextStrategy);
            default -> throw new IllegalArgumentException(String.format("id strategy invalide %d", id));
        }
        return returned;
    }

    public Strategy getStrategyFromIntList(List<Integer> idList) {
        Strategy strategy = null;
        for (int i = idList.size()-1; i >= 0 ; i--) {
            int id = idList.get(i);
            strategy = getInstance().buildStrategy(id, strategy);
        }
        return strategy;
    }
}
