package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.Arrays;

/**
 * À chaque fois que vous invoquez une carte pouvoir, le coût
 * de cette dernière est réduit d’1 énergie de votre choix. Le
 * coût des prochaines cartes invoquées ne peut néanmoins être
 * inférieur à 1 énergie.
 * La Main de la fortune ne diminue en aucun cas les coûts d’activation de
 * vos cartes pouvoir (comme celui de l’Orbe de cristal par exemple)
 */
public class FortuneEffect extends AbstractEffect {
    public static final int REDUCE = 1;

    public FortuneEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        return (player.getFacadeIA().getCardInIvocationF() != null) && (Arrays.stream(player.getFacadeIA().getCardInIvocationF().getEnergyCost()).sum()) > 0;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        /*
            we are getting the card during the invocation with a clever trick
            this card is set in the card manager during the call of Invoke,
            now we can ask him which energy he wants back
        */
        Card cardInInvocation = player.getFacadeIA().getCardInIvocationF();
        int[] energiesCost = cardInInvocation.getEnergyCost();
        player.getFacadeIA().setChoosableEnergiesF(energiesCost, Math.min(REDUCE, player.getFacadeIA().getAmountOfEnergiesLeftF()));
        int[] energyChosen = player.chooseEnergies();
        description.append(String.format("%s effect, reduce the cost of %s of %s",getName(),cardInInvocation.getName(), EnergyManager.displayEnergiesFromAnArray(energyChosen) ));
        pb.getEnergyManager().addEnergy(energyChosen);
    }
}
