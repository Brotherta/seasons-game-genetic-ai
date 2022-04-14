package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * Pour  activer  la  Potion  de  savoir,  sacrifiez-la  afin  de  recevoir
 * 5  énergies  de  votre  choix  en  provenance  du  stock.  Placez  ces
 * énergies dans votre réserve
 */
public class KnowledgeEffect extends AbstractEffect {
    public static final int GAIN = 5;

    public KnowledgeEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        description.append(String.format("Sacrifice %s :", getName()));
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        pb.getCardManager().sacrificeCard(getCard()); // gain 5 energies of his choice
        UtilEffect.gainEnergies(gameEngine, player, GAIN);
    }
}
