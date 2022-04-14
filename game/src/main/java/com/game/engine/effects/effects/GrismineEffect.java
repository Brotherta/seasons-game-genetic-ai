package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.observer.GameObserver;
import com.utils.Event;

import java.util.Arrays;

/**
 * Au moment où vous invoquez Lewis Grisemine, recevez en
 * provenance du stock exactement le même nombre et le même
 * type d’énergies que l’adversaire de votre choix possède dans
 * sa réserve. Placez les énergies ainsi reçues dans votre réserve.
 * Les énergies copiées chez un adversaire ne sont pas volées, ce dernier les conserve.
 */
public class GrismineEffect extends AbstractEffect {

    public GrismineEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        for (PersonalBoard pb : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!pb.getPlayer().equals(player) && pb.getEnergyManager().getAmountofEnergies() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());

        Player playerChosen = player.getFacadeIA().getPlayerToCopyF();
        if (playerChosen == null) {
            pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
            player.choosePlayerToCopy();
            playerChosen = player.getFacadeIA().getPlayerToCopyF();

        }

        EnergyManager emChosen = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(playerChosen.getNumPlayer()).getEnergyManager();
        description.append(String.format(" %s copied %s energy to add to his energy stock", player.getName(), playerChosen.getName()));

        int[] energiesToCopy = Arrays.copyOf(emChosen.getAmountOfEnergiesArray(), EnergyType.values().length);
        player.getFacadeIA().setChoosableEnergiesF(energiesToCopy, player.getFacadeIA().getAmountOfEnergiesLeftF());
        int[] energiesChosen = player.chooseEnergies();
        pb.getEnergyManager().addEnergy(energiesChosen);
        GameObserver observer = pb.getGameObserver();
        gameEngine.notifyAllObserver(observer, Event.GAIN_ENERGY, pb.getEnergyManager().getAmountOfEnergiesArray());
    }
}
