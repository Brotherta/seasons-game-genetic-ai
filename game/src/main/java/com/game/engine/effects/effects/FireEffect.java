package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.card.Deck;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.ArrayList;

/**
 * Au moment où vous invoquez l’Amulette de feu, piochez 4 cartes
 * pouvoir. Consultez-les et gardez-en une que vous placez dans votre
 * main. Placez les 3 cartes restantes dans la défausse.
 */
public class FireEffect extends AbstractEffect {
    public static final int DRAW = 4;

    public FireEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        Deck deck = gameEngine.getDeck();

        description.append(String.format(" draw %d cards ", DRAW));
        ArrayList<Card> drawnCards = new ArrayList<>();
        int maxDraw = DRAW;
        if (deck.getCards().size() + deck.dispileCardsSize() < DRAW) {
            maxDraw = 1;
        }

        for (int i = 0; i < maxDraw; i++) {
            drawnCards.add(deck.drawCard());
        }

        player.getFacadeIA().setChoosableCardsF(drawnCards);
        Card chosenCard = player.chooseBetweenCards();
        description.append(String.format("and choose %s ", chosenCard.getName()));
        pb.getCardManager().addCard(chosenCard);
        drawnCards.remove(chosenCard);

        for (Card c : drawnCards) {
            deck.addCardToDispileCards(c);
        }

        description.append(" then put 3 cards into the discards pile");
    }
}
