package com.game.playerassets.ia;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import ia.IAType;

import java.util.Map;
import java.util.Stack;

public interface TurnActions {
    GameplayChoice makeGameplayChoice();
    Dice chooseDiceFace();
    boolean chooseIfReRollDice();
    void choosePlayerToCopy();
    int[] chooseEnergies();
    int[] chooseEnergiesToSacrifice();
    Stack<Card> manageCard();
    Card chooseBetweenCards();
    Card chooseCardToInvokeForFree();

    Card chooseCardPlayedToSacrifice();
    Card chooseCardToReturnInHand();

    IAType getType();

    Map<Player, Card> choosePlayerToGiveACard();
    void chooseTimeToChange();
}