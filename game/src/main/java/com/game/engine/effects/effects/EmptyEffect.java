package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;

public class EmptyEffect extends AbstractEffect {
    public EmptyEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
    }
}
