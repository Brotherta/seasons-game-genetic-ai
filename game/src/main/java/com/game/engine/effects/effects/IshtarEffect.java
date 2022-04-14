package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.card.CardManager;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;


/**
 * Pour activer la Balance d’Ishtar, défaussez 4 énergies identiques en provenance de votre réserve, afin de les cristalliser
 * en 12 cristaux. Avancez alors votre pion du sorcier de 12 cases
 * sur la piste des cristaux.
 * La Balance d’Ishtar peut être activée même si vous ne disposez pas de
 * l’action de cristallisation.
 * Si vous possédez une Bourse d’Io, recevez 16 cristaux au lieu de 12 à
 * chaque fois que vous activez la Balance d’Ishtar.
 */
public class IshtarEffect extends AbstractEffect {
    private static final int COST = 3;
    private int crystals = 3;
    private static final String SPECIAL_CONDITION = "Purse of Io";
    private static final int IO_NB = 8;

    public IshtarEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        int[] energiesRepeated = player.getFacadeIA().getEnergiesRepeatedF(COST);
        boolean cond = false;
        for (int i = 0; i < energiesRepeated.length; i++) {
            if (energiesRepeated[i] > 0) {
                cond = true;
                break;
            }
        }
        return cond;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        StringBuilder description = player.getDescription();
        CardManager cm = pb.getCardManager();
        boolean hasPurseOfIo = Card.contains(IO_NB, cm.getInvokeDeck().getCards());

        if (hasPurseOfIo) {
            crystals = 4;
            description.append(String.format(" [Special effect he has %s ge gain now %d crystal for each energy] ", SPECIAL_CONDITION, crystals));
        }

        int[] energiesChosen = player.getFacadeIA().getChoosableEnergiesF();
        if (energiesChosen[0] == -1) {
            player.getFacadeIA().setChoosableEnergiesF(player.getFacadeIA().getEnergiesRepeatedF(3), 1);
            energiesChosen = player.chooseEnergiesToSacrifice();
        }

        EnergyType typeChosen = array2Type(energiesChosen);
        assert typeChosen != null : "typechosen is null";
        em.consumeEnergy(typeChosen, Math.min(em.getAmountOfEnergiesArray()[typeChosen.ordinal()], COST));

        if (player.getFacadeIA().hasAmuletF()) {
            em.getWaterAmulet().consumeEnergyInAmulet(typeChosen, em.getWaterAmulet().getEnergyList()[typeChosen.ordinal()]);
        }
        description.append(String.format("crystallize %d %s and gains %d crystals", COST, typeChosen.name(), COST * crystals));

        pb.addCrystal(COST * crystals);
        crystals = 3;
    }

    private EnergyType array2Type(int[] energiesArray) {
        for (int i = 0; i < energiesArray.length; i++) {
            if (energiesArray[i] > 0) {
                return EnergyType.values()[i];
            }
        }
        return null;
    }

}