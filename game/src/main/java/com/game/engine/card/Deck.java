package com.game.engine.card;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private final List<Card> totalCards;
    private final Stack<Card> cards;
    private final Stack<Card> discardPile;

    public Deck(List<Card> cards) {
        this.totalCards = cards;
        this.cards = new Stack<>();
        this.discardPile = new Stack<>();
        initDeck();
    }

    public void initDeck() {
        cards.clear();
        discardPile.clear();
        for (Card card : totalCards) {
            Card newCard = new Card(
                    card.getName(),
                    card.getNumber(),
                    card.getPoints(),
                    card.getCrystalCost(),
                    card.getEnergyCost(),
                    card.getType()
            );
            newCard.setEffect(card.getEffect());
            cards.push(newCard);
        }
        shuffleDeck();
    }

    public void addCardToDispileCards(Card c) {
        discardPile.push(c);
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        Card cardToDraw;
        if (!cards.isEmpty()) {
            cardToDraw = cards.pop();
        } else {
            if (discardPile.isEmpty()) {
                throw new DeckEmptyException("deck vide");
            } else {
                int discardPileSize = discardPile.size();
                for (int i = 0; i < discardPileSize; i++) {
                    cards.push(discardPile.pop());
                }
                shuffleDeck();
                cardToDraw = cards.pop();
            }
        }
        return cardToDraw;
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public Card getCard(Integer cardNumber) {
        for (Card c :
                cards) {
            if (c.getNumber() == cardNumber) {
                return c;
            }
        }
        throw new CardNotFoundException("the card you're looking for seems to not be here");
    }

    public int dispileCardsSize() {
        return discardPile.size();
    }
}
