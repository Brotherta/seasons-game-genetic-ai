package com.game.engine.card;

import com.game.engine.gamemanager.GameEngine;
import com.utils.loaders.cards.CardsLoader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.mockito.Mock;

import java.io.File;
import java.util.Objects;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class DeckStepDefs {

    private CardsLoader cardsLoader;
    private Deck deck;
    private int nbPlayer;
    @Mock
    GameEngine engine = mock(GameEngine.class);

    @Given("the cards are load")
    public void the_cards_are_load() {
        File f = new File(Objects.requireNonNull(CardsLoader.class.getClassLoader().getResource("cards.json")).getPath());
        cardsLoader = CardsLoader.getCardsLoader(f.getPath());
    }

    @And("there is {int} players in the game")
    public void players(int nbPlayers) {
        this.nbPlayer = nbPlayers;
    }

    @And("the deck is initialized")
    public void the_deck_is_initialized() {
        deck = cardsLoader.loadDeck(nbPlayer, engine);
    }

    @Given("there is exactly {int} cards in the deck")
    public void less_than_cards(int nbCardsExcpected) {
        int actualSize = deck.getCards().size();
        assertEquals(nbCardsExcpected, actualSize);
    }

    @Then("the dices should be shuffled")
    public void well_shuffled() {
        Stack<Card> unsortDeck = (Stack<Card>) deck.getCards().clone();
        deck.shuffleDeck();
        assertNotEquals(unsortDeck, deck.getCards());
    }
}
