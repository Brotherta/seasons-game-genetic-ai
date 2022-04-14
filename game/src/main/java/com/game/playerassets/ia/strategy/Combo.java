package com.game.playerassets.ia.strategy;

import com.game.engine.card.Card;
import com.game.engine.gamemanager.players.PlayerTurnManager;
import com.game.playerassets.ia.GameplayChoice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class of Combo used to stock the combos of the player and the moves chosen by the player.
 */
public class Combo {

    private final Queue<Card> cardQueue;
    private final Queue<GameplayChoice> gameplayChoices;
    private final Queue<int[]> energiesToChooseQueue;


    public Combo(PlayerTurnManager ptm) {
        this.gameplayChoices = new LinkedList<>();
        this.cardQueue = new LinkedList<>();
        this.energiesToChooseQueue = new LinkedList<>();
    }

    public void addCardToQueue(Card card) {
        cardQueue.add(card);
    }

    public void addChoiceToQueue(GameplayChoice gameplayChoice) {
        gameplayChoices.add(gameplayChoice);
    }

    public void addEnergiesToChooseToQueue(int[] energies) {
        this.energiesToChooseQueue.add(energies);
    }

    public Queue<Card> getCardQueue() {
        return cardQueue;
    }

    public Queue<GameplayChoice> getGameplayChoices() {
        return gameplayChoices;
    }

    public Queue<int[]> getEnergiesToChooseQueue() {
        return energiesToChooseQueue;
    }
}
