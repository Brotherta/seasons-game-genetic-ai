package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

/**
 * À chaque fois que l’on change de saison, recevez une énergie
 * de  votre  choix  en  provenance  du  stock.  Placez  l’énergie  dans
 * votre réserve.
 * Si un changement de saison a lieu, recevez votre énergie à la fin
 * de la manche lors de laquelle le changement de saison a lieu.
 */
public class TimeEffect extends AbstractEffect {
    public static final int GAIN = 1;

    public TimeEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        description.append(String.format("[CHANGING SEASONS] %s :", getName()));
        UtilEffect.gainEnergies(gameEngine, player, GAIN);
    }
}

