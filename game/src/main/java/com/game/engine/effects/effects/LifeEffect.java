package com.game.engine.effects.effects;

import com.game.engine.card.CardManager;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.utils.Event;

/**
 * Pour  activer  la  Potion  de  vie,  sacrifiez-la  afin  de  cristalliser
 * chacune  de  vos  énergies  en  4  cristaux.  Avancez  votre  pion
 * du  sorcier  d’autant  de  cases  que  nécessaire  sur  la  piste  des
 * cristaux.
 * On peut activer la Potion de vie sans posséder l’action de cristallisation.
 */

public class LifeEffect extends AbstractEffect {
    public static final int VALUE = 4;

    public LifeEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        EnergyManager em = pb.getEnergyManager();
        CardManager cm = pb.getCardManager();
        description.append(String.format(" uses the %s and crystalize all his energies into %d crystal each", getName(), VALUE));
        cm.sacrificeCard(getCard());
        gameEngine.notifyAllObserver(pb.getGameObserver(), Event.SACRIFICE,player);
        int amountCrystallized = em.getAmountofEnergies() * VALUE;
        em.consumeEnergy(em.getAmountOfEnergiesArray());
        pb.addCrystal(amountCrystallized);
    }
}

