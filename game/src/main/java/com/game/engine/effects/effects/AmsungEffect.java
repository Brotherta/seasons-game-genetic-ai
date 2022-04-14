package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.ArrayList;

/**
 * Au moment où vous invoquez Amsug Longcoup, chaque joueur
 * (vous y compris) renvoie dans sa main l’une de ses cartes objet
 * magique déjà en jeu.
 * <p>
 * Un joueur ne possédant pas d’objet magique invoqué n’est pas
 * concerné par l’effet d’Amsug Longcoup.
 */
public class AmsungEffect extends AbstractEffect {

    public AmsungEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();

        for (Player p : gameEngine.getPlayersCentralManager().getPlayerList()) {
            PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(p.getNumPlayer());
            ArrayList<Card> magicObject = p.getFacadeIA().MagicObjectInvokedF();

            if (!magicObject.isEmpty()) {
                p.getFacadeIA().setChoosableCardsF(magicObject);
                Card c = p.chooseCardToReturnInHand();
                description.append(String.format(" The card %s return in %s's hand|", c.getName(), p.getName()));
                pb.getCardManager().getInvokeDeck().getCards().remove(c);
                pb.getCardManager().getCards().add(c);
            } else {
                description.append(String.format(" %s has no card to take back|", p.getName()));
            }
        }
    }
}