package com.game.engine.effects.effects;

import com.game.engine.card.CardManager;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * Pour activer la Potion de puissance, sacrifiez-la afin de piocher
 * une carte pouvoir et d’avancer de 2 cases votre pion du sorcier
 * sur votre jauge d’invocation. Vous êtes obligé de conserver en main la carte pouvoir
 * piochée. En aucun cas vous ne pouvez la défausser.
 */
public class PowerEffect extends AbstractEffect {
    public static final int UP_GAUGE = 2;
    public static final int DRAW = 1;

    public PowerEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        description.append(String.format(" sacrifices %s to :", getName()));
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        CardManager cm = pb.getCardManager();
        cm.sacrificeCard(getCard());
        UtilEffect.drawCards(gameEngine, player, DRAW);
        UtilEffect.increaseInvokeCapacity(gameEngine, player, UP_GAUGE);
    }
}
