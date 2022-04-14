package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

public class IoEffect extends AbstractEffect {
    public static final int GAIN = 1;

    public IoEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainCrystals(gameEngine, player, GAIN);
    }
}
