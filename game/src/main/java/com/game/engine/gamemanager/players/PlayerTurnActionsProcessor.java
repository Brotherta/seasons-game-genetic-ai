package com.game.engine.gamemanager.players;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Combo;
import com.utils.Event;

import java.util.ArrayList;
import java.util.Queue;

public class PlayerTurnActionsProcessor {

    private final PlayerTurnManager playerTurnManager;
    private final Player player;
    private final PersonalBoard personalBoard;
    private final GameEngine engine;

    public PlayerTurnActionsProcessor(PlayerTurnManager playerTurnManager) {
        this.playerTurnManager = playerTurnManager;
        this.player = playerTurnManager.getPlayer();
        this.personalBoard = playerTurnManager.getPersonalBoard();
        this.engine = playerTurnManager.getEngine();
    }

    void processActivation() {
        Card c = player.getFacadeIA().getCardToUseF();
        playerTurnManager.getDescription().append(String.format("%n\t-> chooses to activate %s : ", c.getName()));
        c.getEffect().applyEffect(engine, playerTurnManager.getPlayer());
        personalBoard.getCardManager().getInvokeDeck().addActivatedCard(c);
        engine.getStatsManager().addActivatedCards(player, 1);
    }

    /**
     * Crystallize 2 energy for each energy type if possible
     */
    public void processCrystallize() {
        int[] c = player.getFacadeIA().getEnergiesToCrystallizeF();
        crystallize(c);
    }

    /**
     * Process the invoke action of the player
     */
    public void processInvoke() {
        personalBoard.getCardManager().invoke(player.getFacadeIA().getCardToUseF());
    }

    /**
     * Process the draw card action of the player
     */
    public void processDrawCard() {
        personalBoard.drawCardFromDice();
    }

    /**
     * Process the double draw card (bonus) action of the player
     */
    public void processDoubleDrawCard() {
        personalBoard.getBonusManager().doubleDrawCard();
    }

    /**
     * Put and end to the turn
     *
     * @param choicesAvailable The list containing the choices
     */
    public void processStopTurn(ArrayList<GameplayChoice> choicesAvailable) {
        choicesAvailable.clear();
        playerTurnManager.getDescription().append("\n\t-> choses to stop his turn.\n");
    }

    /**
     * Our function which call personal board to crystallize etc
     *
     * @param energiesToCrystallize Energies going to be crystallized
     */
    private void crystallize(int[] energiesToCrystallize) {
        for (int i = 0; i < EnergyType.values().length; i++) {
            if (energiesToCrystallize[i] > personalBoard.getEnergyManager().getAmountOfEnergiesArray()[i])
                return;
        }
        personalBoard.crystallize(energiesToCrystallize);
    }

    /**
     * This function call the bonus manager and his crystallize method
     */
    public void processCrystallizeBonus() {
        int[] energiesToCrystallize = player.getFacadeIA().getEnergiesToCrystallizeF();
        personalBoard.getBonusManager().crystallizeEnergy(energiesToCrystallize);
    }

    /**
     * Our function which call the bonus manager to exchange
     *
     */
    public void processExchangeBonus() {
        int[] energiesExchange = player.getFacadeIA().getChoosableEnergiesF();
        int[] fromExchange = new int[]{energiesExchange[0], energiesExchange[1], energiesExchange[2], energiesExchange[3]};
        int[] toExchange = new int[]{energiesExchange[4], energiesExchange[5], energiesExchange[6], energiesExchange[7]};

        personalBoard.getBonusManager().exchangeEnergy(fromExchange, toExchange);
    }

    /**
     * Our function which call the CardManager to up the invocation gauge
     */
    public void processInvocationBonus() {
        personalBoard.getBonusManager().upInvocation();
    }

    public void processCombo() {
        personalBoard.getDescription().append("\n\t-> makes a combo ! Let's win... with this smart combo...");
        Combo combo = player.getFacadeIA().getComboToUse();
        Queue<GameplayChoice> choices = combo.getGameplayChoices();
        GameplayChoice choice = choices.poll();
        while (choice != null) {
            switch (choice) {
                case CRYSTALLIZE -> {
                    int[] energiesToCrystallize = combo.getEnergiesToChooseQueue().poll();
                    assert energiesToCrystallize != null : "FAILED(PlayerTurnActionsProcessor): energies to crystallize in Combo is not initialized properly !";
                    player.getFacadeIA().setEnergiesToCrystallizeF(energiesToCrystallize);
                    processCrystallize();
                }
                case CRYSTALLIZE_BONUS -> {
                    int[] energiesToCrystallize = combo.getEnergiesToChooseQueue().poll();
                    assert energiesToCrystallize != null : "FAILED(PlayerTurnActionsProcessor): energies to crystallize with bonus in Combo is not initialized properly !";
                    player.getFacadeIA().setEnergiesToCrystallizeF(energiesToCrystallize);
                    processCrystallizeBonus();
                }
                case EXCHANGE_BONUS -> {
                    int[] energiesToCrystallize = combo.getEnergiesToChooseQueue().poll();
                    assert energiesToCrystallize != null : "FAILED(PlayerTurnActionsProcessor): energies to exchange with bonus in Combo is not initialized properly !";
                    player.getFacadeIA().setEnergiesToCrystallizeF(energiesToCrystallize);
                    processExchangeBonus();
                }
                case INVOKE -> {
                    Card card = combo.getCardQueue().poll();
                    assert card != null : "FAILED(PlayerTurnActionsProcessor): card to use in Combo is not initialized properly !";
                    player.getFacadeIA().setCardToUseF(card);
                    processInvoke();
                }
                case ACTIVATE -> {
                    Card card = combo.getCardQueue().poll();
                    assert card != null : "FAILED(PlayerTurnActionsProcessor): card to use in Combo is not initialized properly !";
                    player.getFacadeIA().setCardToUseF(card);
                    processActivation();
                }
                case DRAW_CARD_WITH_BONUS -> {
                    processDoubleDrawCard();
                    engine.notifyAllObserver(personalBoard.getGameObserver(), Event.DRAW_CARD_WITH_BONUS, 1);
                }
                case DRAW_CARD -> {
                    processDrawCard();
                    engine.notifyAllObserver(personalBoard.getGameObserver(), Event.DRAW_CARD, 1);
                }
                case INVOKE_BONUS -> processInvocationBonus();
            }
            choice = choices.poll();
        }
    }
}
