package com.game.engine.effects.effects;

import com.game.engine.dice.DiceFace;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * Le Dé de la malice n’a pas de coût d’invocation et peut donc
 * être invoqué gratuitement.
 * À chaque fois que vous activez le Dé de la malice, relancez le dé
 * des saisons que vous avez choisi et ce, avant d’avoir effectué
 * ses  actions. Ne prenez en compte que le nouveau dé pour
 * effectuer vos actions du tour.
 * Vous recevez 2 cristaux chaque fois que vous activez l’effet de votre Dé
 * de la malice.
 * Si vous possédez 2 Dés de la malice, vous pouvez utiliser les deux à
 * la suite si les actions de vos 2 premier dés ne vous conviennent pas.
 * Recevez alors 4 cristaux.
 */
public class MaliceEffect extends AbstractEffect {
    public static final int GAIN = 2;

    public MaliceEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        return false; // l'activation de la carte se fait dans le player turn manager au moment de process les dés.
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        DiceFace df = pb.getActualDice().rollFace();
        description.append(" reroll the dice and get : ");
        description.append(String.format("[%s]", df.facePropertyToString()));
        UtilEffect.gainCrystals(gameEngine, player, GAIN);
    }
}
