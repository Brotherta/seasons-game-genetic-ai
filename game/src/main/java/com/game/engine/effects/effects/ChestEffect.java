package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.Arrays;

/**
 * À chaque fois que vous possédez 4 énergies ou plus dans votre
 * réserve à la fin de la manche, recevez 3 cristaux. Avancez alors
 * de 3 cases votre pion du sorcier sur la piste des cristaux.
 */
public class ChestEffect extends AbstractEffect {
    public static final int GAIN = 3;
    public static final int REQUIRE = 4;

    public ChestEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        return Arrays.stream(player.getFacadeIA().getAmountOfEnergiesArrayF()).sum() >= REQUIRE;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainCrystals(gameEngine, player, GAIN);
    }
}

