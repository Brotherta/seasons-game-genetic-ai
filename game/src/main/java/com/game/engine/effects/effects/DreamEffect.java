package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.card.CardManager;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.List;

/**
 * Pour activer la Potion de rêves, sacrifiez-la et défaussez toutes
 * vos  énergies  afin  d’invoquer  gratuitement  une  carte  pouvoir
 * de votre main.
 * Si  un  joueur  ne  possède  aucune  énergie, il  peut  utiliser  la
 * Potion de rêves.
 */
public class DreamEffect extends AbstractEffect {
    public static final int INVOKE = 1;

    public DreamEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setActivationEffect(true);
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        List<Card> cards = player.getFacadeIA().getCardsF();
        if (cards.size() >= 1) {
            for (Card card : cards) {
                if ((card.getName().compareTo(getName()) != 0) && card.getEffect().canActivate(gameEngine, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        CardManager cm = pb.getCardManager();
        EnergyManager em = pb.getEnergyManager();

        em.consumeEnergy(em.getAmountOfEnergiesArray()); // defausse des energies
        cm.sacrificeCard(getCard()); //sacrifice card

        description.append(String.format(" Discards all the·y·m energy and  sacrifice the card %s ", getName()));

        //choose a card to invoke for free
        if (!pb.getCardManager().getCards().isEmpty() && pb.getCardManager().getInvokeDeck().hasEnoughGauge()) {

            Card card = player.getFacadeIA().getCardToUseFreeF();
            if (card == null) {
                player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());
                card = player.chooseCardToInvokeForFree();
            }
            description.append(String.format(" %s invoked %d card for free a card.", player.getName(), INVOKE));
            cm.invoke(card, true);
        } else {
            description.append(" try to invoked for free, but couldn't invoke any card.");
        }
    }
}
