package com.game.playerassets.ia;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.playerassets.ia.strategy.Strategy;
import ia.IAType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Basic concept of IA
 */
public class IA extends Player {

    public IA(IAType type, String name, FacadeIA fpb, int num) {
        super(name, fpb, num, type);
    }

    public IA(String name, FacadeIA fpb, int num) {
        super(name, fpb, num, IAType.RANDOM_IA);

    }

    @Override
    public String getName() {
        return super.toString();
    }


    /**
     * From a list of available choices, the player will choose one.
     *
     * @return The chosen GameplayChoice
     */
    @Override
    public GameplayChoice makeGameplayChoice() {
        return (GameplayChoice) getGameplayStrategy().canAct(getFacadeIA());
    }

    /**
     * Choose which dice do you prefer from a given list
     *
     * @return The chosen dice
     */
    @Override
    public Dice chooseDiceFace() {
        return (Dice) getDiceStrategy().canAct(getFacadeIA());
    }

    /**
     * Chooses how to manage his card for the different year.
     *
     * @return the stack sorted by the year.
     */
    @Override
    public Stack<Card> manageCard() {
        return (Stack<Card>) getPreludeStrategy().canAct(getFacadeIA());
    }

    /**
     * If the engine ask which card do you prefer in a list
     *
     * @return The chosen card
     */
    @Override
    public Card chooseBetweenCards() {
        return (Card) getCardStrategy().canAct(getFacadeIA());
    }

    /**
     * Choices an energy stock to copy.
     * @return a player
     */
    @Override
    public void choosePlayerToCopy() {
        getCopyStrategy().canAct(getFacadeIA());
    }

    /**
     * Chooses energies to keep in stock.
     * @return an array of energies.
     */
    @Override
    public int[] chooseEnergies() {
        return (int[]) getEnergyStrategy().canAct(getFacadeIA());
    }

    /**
     * Chooses energies to sacrifice.
     * @return an array of energies.
     */
    @Override
    public int[] chooseEnergiesToSacrifice() {
        return (int[]) getSacrificeEnergyStrategy().canAct(getFacadeIA());
    }

    /**
     * Chooses a card that he want to invoke for free.
     * @return a card.
     */
    @Override
    public Card chooseCardToInvokeForFree() {
        getFacadeIA().setInvokeForFree(true);
        return (Card) getInvocationFreeStrategy().canAct(getFacadeIA());
    }

    /**
     * Chooses a card in his InvokeDeck to sacrifice.
     * @return a card.
     */
    @Override
    public Card chooseCardPlayedToSacrifice() {
        return (Card) getSacrificeCardStrategy().canAct(getFacadeIA());
    }

    @Override
    public Card chooseCardToReturnInHand() {
        return (Card) getReturnInHandStrategy().canAct(getFacadeIA());
    }

    @Override
    public void chooseTimeToChange(){
        getChangeTimeStrategy().canAct(getFacadeIA());
    }

    @Override
    public Map<Player, Card> choosePlayerToGiveACard() {
        return (Map<Player, Card>) getGiveCards().canAct(getFacadeIA());
    }

    @Override
    public boolean chooseIfReRollDice() {
        return (boolean) getRerollStrategy().canAct(getFacadeIA());
    }
}