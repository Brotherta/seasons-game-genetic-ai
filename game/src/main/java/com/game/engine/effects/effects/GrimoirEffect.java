package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.effects.UtilEffect;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

/**
 * Au  moment  où  vous  invoquez le Grimoire  ensorcelé, recevez
 * 2  énergies  de  votre  choix en provenance  du  stock,  que  vous
 * placez dans votre réserve.
 * Tant  que  le  Grimoire  ensorcelé  est  en  jeu,  il  augmente  la
 * capacité  de  votre  réserve,  lui  permettant  de  stocker  jusqu’à
 * 10 énergies au lieu de 7.
 */
public class GrimoirEffect extends AbstractEffect {
    public static final int GAIN = 2;
    public static final int UP_GAUGE = 3;

    public GrimoirEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        description.append(String.format("Activate %s", getName()));

        if (em.getGauge() < EnergyManager.getMaxGauge()) {
            em.setGauge(EnergyManager.getMaxGauge());
            description.append(String.format(" rises his Energy gauge to %d", EnergyManager.getMaxGauge()));
        }
        else {
            description.append(String.format(" can't rises his Energy because he aleready has invoked %s", getName()));
        }
        UtilEffect.gainEnergies(gameEngine, player, GAIN);
    }

    @Override
    public void unapplyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();

        boolean isAnOtherGrimInvoked = false;

        for (Card c : pb.getCardManager().getInvokeDeck().getCards()) {
            if (c.getName().compareTo(getName()) == 0) {
                isAnOtherGrimInvoked = true;
                description.append(String.format("there is still a %s invoked the effect is not lost", getName()));
                break;
            }
        }

        if (!isAnOtherGrimInvoked) {
            description.append(String.format("The effect of %S is lost ", getName()));
            if (em.getGauge() == EnergyManager.getMaxGauge()) {
                em.setGauge(EnergyManager.getInitialGauge());
            }

            int amountToSacrifice = em.getAmountofEnergies() - EnergyManager.getInitialGauge();
            if (amountToSacrifice > 0) {
                description.append(String.format("lose %d energies because no room left", amountToSacrifice));
                player.getFacadeIA().setChoosableEnergiesF(em.getAmountOfEnergiesArray(), amountToSacrifice);
                int[] energyChosen = player.chooseEnergiesToSacrifice(); //est normal

                em.consumeEnergy(energyChosen);
            }
        }
    }
}

