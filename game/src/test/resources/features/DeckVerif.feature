#language: en
Feature: Deck

  Background:
    Given the cards are load
    And there is 3 players in the game
    And the deck is initialized

  Scenario: The deck is welled shuffled
    Given there is exactly 60 cards in the deck
    Then the dices should be shuffled