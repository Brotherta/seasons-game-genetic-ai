package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * Si vous êtes le joueur ayant en jeu strictement le plus de cartes
 * pouvoir  à  la  fin  de  la  partie,  avancez  votre  pion  du  sorcier  de  20
 * cases sur la piste des cristaux.
 */
public class RagfieldEffect extends AbstractEffect {
    public static final int GAIN = 20;

    public RagfieldEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        int cardsPlayerHasInvoked = pb.getCardManager().getInvokeDeck().getCards().size();
        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!(personalBoard.equals(pb))) {
                int cardsInvoked = personalBoard.getCardManager().getInvokeDeck().getCards().size();
                if (cardsInvoked > cardsPlayerHasInvoked) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        UtilEffect.gainCrystals(gameEngine, player, GAIN);
    }
}
