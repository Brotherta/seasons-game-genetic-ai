package com.game.engine.effects.effects;

import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.Amulet;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

public class WaterEffect extends AbstractEffect {
    public static final int GAIN = 4;

    public WaterEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();

        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        int[] energyToChoose;

        player.getFacadeIA().setChoosableEnergiesF(new int[]{GAIN, GAIN, GAIN, GAIN}, Math.min(GAIN, player.getFacadeIA().getAmountOfEnergiesLeftF()));
        energyToChoose = player.chooseEnergies();
        description.append(String.format(" put %s on his Water Amulet", EnergyManager.displayEnergiesFromAnArray(energyToChoose)));
        em.setWaterAmulet(new Amulet(energyToChoose));
    }

    @Override
    public void unapplyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        description.append(String.format(" loses his Amulet and the energies on it%n"));
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        em.setWaterAmulet(null);
    }
}
