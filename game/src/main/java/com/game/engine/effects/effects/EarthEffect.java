package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

/**
 * Au moment où vous invoquez l’Amulette de terre, avancez votre
 * pion du sorcier de 9 cases sur la piste des cristaux.
 */
public class EarthEffect extends AbstractEffect {
    public static final int GAIN = 9;

    public EarthEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainCrystals(gameEngine, player, GAIN);
    }
}