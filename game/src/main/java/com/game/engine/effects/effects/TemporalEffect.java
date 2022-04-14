package com.game.engine.effects.effects;

import com.game.engine.SeasonType;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.utils.Util;
import ia.IAType;

/***
 *Les Bottes temporelles n’ont pas de coût d’invocation et
 * peuvent donc être invoquées gratuitement.
 * Au moment où vous invoquez les Bottes temporelles, avancez
 * ou reculez d’une à trois cases le marqueur saison sur la roue
 * des saisons.
 * Si les Bottes temporelles font reculer le marqueur saison de l’hiver à
 * l’automne :
 * - reculez le marqueur année d’une case.
 * - conservez toutes les cartes pouvoir que vous avez en main.
 * Si les Bottes temporelles vous amènent à changer de saison (en avançant
 * ou en reculant le marqueur saison), les cartes affectées par le changement de saison comme Figrim l’avaricieux ou le Sablier du temps font
 * immédiatement effet.
 * Si les Bottes temporelles amènent à dépasser la fin de la troisième année de jeu en cours de manche, terminez la manche en cours avant de
 * mettre un terme à la partie.
 */

public class TemporalEffect extends AbstractEffect {

    public TemporalEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        int numberToChoose = player.getFacadeIA().getTimeStepF();

        if (numberToChoose < 0) {
            player.getDescription().append(String.format("%s we roll back from %d months ", getName(), numberToChoose));
        } else {
            player.getDescription().append(String.format("%s we step forward for %d months ", getName(), numberToChoose));
        }
        SeasonType seasonType = gameEngine.getBoard().getSeason();
        gameEngine.getBoard().timeForward(numberToChoose);
        if (!gameEngine.getBoard().getSeason().equals(seasonType)) {
            for (PersonalBoard pb : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
                if (pb.getPlayer().getType() == IAType.SUBJECT_IA) {
                    Subject s = pb.getSubject();
                    s.linkDNA(gameEngine.getBoard().getYear(), gameEngine.getBoard().getSeason());
                }
            }
        }
        Util.checkPermanentEffect(gameEngine.getPlayersCentralManager().getPersonalBoardList(), gameEngine, EffectType.SEASON);
    }


}