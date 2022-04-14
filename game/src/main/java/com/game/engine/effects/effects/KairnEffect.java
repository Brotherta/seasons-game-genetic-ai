package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.observer.GameObserver;
import com.utils.Event;

import java.util.Arrays;

/**
 * Pour activer Kairn le destructeur, défaussez une énergie de votre
 * choix  en  provenance  de  votre  réserve.  Chaque  adversaire  recule
 * alors de 4 cases son pion du sorcier sur la piste des cristaux
 */
public class KairnEffect extends AbstractEffect {
    public static final int SACRIFICE = 1;
    public static final int LOSS = 4;

    public KairnEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    /***
     *
     * @return true if he has at least one energy
     */
    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        return Arrays.stream(player.getFacadeIA().getAmountOfEnergiesArrayF()).sum() > 0;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());

        int[] energies = pb.getEnergyManager().getAmountOfEnergiesArray();
        int[] energyChosen = player.getFacadeIA().getChoosableEnergiesF();

        if(energyChosen[0] == -1){
            //handle the activation without telling the energies to sacifice
            player.getFacadeIA().setChoosableEnergiesF(energies, SACRIFICE);
            energyChosen = player.chooseEnergiesToSacrifice();
        }


        description.append(String.format(" sacrifices %d energies", SACRIFICE));
        pb.getEnergyManager().consumeEnergy(energyChosen);

        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!personalBoard.equals(pb)) {
                personalBoard.loseCrystal(LOSS);
                GameObserver observer =  personalBoard.getGameObserver();
                gameEngine.notifyAllObserver(observer, Event.GAIN_CRYSTAL, personalBoard.getPlayer());
            }
        }
        description.append(String.format(" each player except himself loose %d crystals", LOSS));
    }
}