package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * À chaque fois que vous possédez 1 énergie ou moins dans votre
 * réserve à la fin de la manche, recevez 1 énergie de votre choix
 * en provenance du stock et placez-la dans votre réserve.
 */
public class MendiantEffect extends AbstractEffect {
    public static final int GAIN = 1;
    public static final int REQUIRE = 1;

    public MendiantEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        return em.getAmountofEnergies() <= REQUIRE;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainEnergies(gameEngine, player, GAIN);
    }
}
