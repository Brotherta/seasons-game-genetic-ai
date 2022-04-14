package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.observer.GameObserver;
import com.utils.Event;

/**
 * Suivant que vous jouez à 2, 3 ou 4 joueurs, le coût d’invocation
 * de Figrim l’avaricieux n’est pas le même.
 * À chaque fois que l’on change de saison, tous vos adversaires
 * doivent vous donner 1 cristal. Ils reculent d’une case leur pion
 * du sorcier sur la piste des cristaux. Avancez votre pion du
 * sorcier sur la même piste d’autant de cases que de cristaux ainsi gagnés.
 */
public class FigrimEffect extends AbstractEffect {
    public static final int NB_GAIN = 1;

    public FigrimEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setPermanentEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = gameEngine.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());

        int gainedCrystal = 0;
        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!personalBoard.equals(pb)) {
                if (personalBoard.getCrystal() >= NB_GAIN) {
                    gainedCrystal += NB_GAIN;
                    personalBoard.loseCrystal(NB_GAIN);
                } else {
                    gainedCrystal += personalBoard.getCrystal();
                    personalBoard.loseCrystal(personalBoard.getCrystal());
                }
                GameObserver go = personalBoard.getGameObserver();
                gameEngine.notifyAllObserver(go, Event.GAIN_CRYSTAL, personalBoard.getPlayer());
            }
        }

        pb.addCrystal(gainedCrystal);
        GameObserver go = pb.getGameObserver();
        gameEngine.notifyAllObserver(go, Event.GAIN_CRYSTAL, player);
        int nbOfPlayers = gameEngine.getPlayersCentralManager().getAmountOfPlayers();

        description.append(String.format(" %s has taken %d crystals from the %d players", player.getName(), gainedCrystal, nbOfPlayers));
    }
}
