package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

/**
 * Au moment où vous invoquez l’Amulette d’air, avancez votre pion
 * du sorcier de 2 cases sur votre jauge d’invocation.
 */
public class AirEffect extends AbstractEffect {
    public static final int GAIN = 2;

    public AirEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.increaseInvokeCapacity(gameEngine, player, GAIN);
    }
}
