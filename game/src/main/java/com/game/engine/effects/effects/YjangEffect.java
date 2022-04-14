package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

/**
 * À chaque fois que vous invoquez une carte pouvoir, recevez une
 * énergie  de  votre  choix  en  provenance  du  stock.  Placez  cette
 * énergie dans votre réserve.
 */
public class YjangEffect extends AbstractEffect {
    public static final int GAIN = 1;

    public YjangEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainEnergies(gameEngine, player, GAIN);
    }
}
