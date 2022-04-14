package com.game.engine.card;

import java.util.ArrayList;
import java.util.List;

public class Invocation {
    private final List<Card> cards;
    private final List<Card> activatedCard;
    private int gauge;
    private static final int MAX_GAUGE = 15;

    public Invocation() {
        this.cards = new ArrayList<>();
        this.activatedCard = new ArrayList<>();
        this.gauge = 0;
    }

    public void addCard(Card c) {
        this.cards.add(c);
    }

    public void addActivatedCard(Card card) {
        activatedCard.add(card);
    }

    public boolean isAlreadyActivated(Card card) {
        return activatedCard.contains(card);
    }

    public void resetActivatedCard() {
        activatedCard.clear();
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getGaugeSize() {
        return gauge;
    }

    public boolean hasEnoughGauge() {
        return cards.size() < gauge;
    }

    public void upInvocationGauge(int amount) {
        if (gauge + amount > MAX_GAUGE) {
            gauge = MAX_GAUGE;
        } else {
            gauge += amount;
        }
    }

    public void reset() {
        cards.clear();
        activatedCard.clear();
        gauge = 0;
    }
}
