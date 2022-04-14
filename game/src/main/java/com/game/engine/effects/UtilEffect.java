package com.game.engine.effects;

import com.game.engine.card.Card;
import com.game.engine.card.CardManager;
import com.game.engine.energy.EnergyManager;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.observer.GameObserver;
import com.utils.Event;

import java.util.Arrays;
import java.util.List;

public class UtilEffect {

    public static void gainCrystals(GameEngine engine, Player player, int nbGain) {
        StringBuilder description = player.getDescription();
        engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer()).addCrystal(nbGain);

        PersonalBoard pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        GameObserver go = pb.getGameObserver();
        engine.notifyAllObserver(go, Event.GAIN_CRYSTAL, player);
        description.append((String.format(" gains %d crystals.", nbGain)));
    }

    public static void gainEnergies(GameEngine gameEngine, Player player, int nbGain) {
        PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        StringBuilder description = player.getDescription();

        player.getFacadeIA().setChoosableEnergiesF(new int[]{nbGain, nbGain, nbGain, nbGain}, Math.min(nbGain, player.getFacadeIA().getAmountOfEnergiesLeftF()));
        int[] energies = player.chooseEnergies();

        if (Arrays.stream(energies).sum() <= player.getFacadeIA().getAmountOfEnergiesLeftF()) {
            personalBoard.getEnergyManager().addEnergy(energies);
        }
        else if (player.getFacadeIA().getAmountOfEnergiesLeftF() > 0) {
            gainEnergies(gameEngine, player, player.getFacadeIA().getAmountOfEnergiesLeftF());
        }

        GameObserver go = personalBoard.getGameObserver();
        gameEngine.notifyAllObserver(go, Event.GAIN_ENERGY, player);

        description.append(String.format(" gains %s %n", EnergyManager.displayEnergiesFromAnArray(energies)));
    }

    public static void drawCards(GameEngine gameEngine, Player player, int nbCard) {
        StringBuilder description = player.getDescription();
        PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        CardManager cm = personalBoard.getCardManager();
        List<Card> cards = personalBoard.getCardManager().drawCards(gameEngine, nbCard);

        player.getFacadeIA().setChoosableCardsF(cards);
        Card cardChosen = player.chooseBetweenCards();

        cm.addCard(cardChosen);
        cards.remove(cardChosen);

        if (!cards.isEmpty()) {
            for (Card card : cards) {
                gameEngine.getDeck().addCardToDispileCards(card);
            }
        }

        GameObserver observer = personalBoard.getGameObserver();
        gameEngine.notifyAllObserver(observer, Event.DRAW_CARD, 1);

        description.append(String.format(" draw %d cards and keep one%n", nbCard));
    }

    public static void increaseInvokeCapacity(GameEngine gameEngine, Player player, int amount) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        pb.getCardManager().getInvokeDeck().upInvocationGauge(amount);
        description.append(String.format(" up his invoke gauge by %d", amount));
        GameObserver go = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer()).getGameObserver();
        gameEngine.notifyAllObserver(go, Event.INCREASE_INVOKE_CAPACITY, amount);
    }
}
