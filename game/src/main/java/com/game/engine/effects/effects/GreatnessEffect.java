package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.utils.loaders.cards.Type;

import java.util.List;

/**
 * Au moment où vous invoquez le Sceptre de grandeur, avancez
 *
 * de 3 cases votre pion du sorcier sur la piste des cristaux pour
 * chaque autre objet magique en jeu que vous possédez.
 * Si vous invoquez un deuxième Sceptre de grandeur, le premier
 * Sceptre  de  grandeur  (que  vous  possédez)  compte  dans  le
 * décompte des objets magiques en jeu.
 */
public class GreatnessEffect extends AbstractEffect {
    public static final int GAIN = 3;

    public GreatnessEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        List<Card> invokedCards = pb.getCardManager().getInvokeDeck().getCards();
        int nbObjectTypeCards = 0;

        for (Card card : invokedCards) {
            Type cardType = card.getType();
            if (cardType.equals(Type.OBJECT) && (card.getName().compareTo(getName()) != 0)) {
                nbObjectTypeCards++;
            }
        }

        UtilEffect.gainCrystals(gameEngine, player, nbObjectTypeCards*GAIN);
    }
}
