package com.game.engine.card;

import com.game.engine.effects.EffectTemplate;
import com.utils.loaders.cards.Type;

import java.util.Arrays;
import java.util.List;

public class Card {
    private final String name;
    private final int number;
    private final int points;
    private final int crystalCost;
    private final int[] energyCost;
    private EffectTemplate effect;
    private final Type type;

    public Card(String name, int number, int points, int crystalCost, int[] energyCost,Type type) {
        this.name = name;
        this.number = number;
        this.points = points;
        this.crystalCost = crystalCost;
        this.energyCost  = energyCost;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public int getPoints() {
        return points;
    }

    public int getCrystalCost() {
        return crystalCost;
    }

    public int[] getEnergyCost() {
        return Arrays.copyOf(energyCost, energyCost.length);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setEffect(EffectTemplate effect) {
        this.effect = effect;
    }

    public EffectTemplate getEffect() {
        return effect;
    }

    public static boolean contains(int cardNb, List<Card> cards) {
        for (Card card : cards) {
            if (card.getNumber() == cardNb) {
                return true;
            }
        }
        return false;
    }

    public static Card getCardInList(int cardNb, List<Card> cards) {
        for (Card card : cards) {
            if (card.getNumber() == cardNb) {
                return card;
            }
        }
        return null;
    }
}
