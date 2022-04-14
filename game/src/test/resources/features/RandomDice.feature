#language: en
Feature: Dices

  Background:
    Given the game engine launched the game
    And there is some players playing the game
    And the dices has been loaded

  Scenario: The dices must be chosen randomly
    Given there is less than 4 players
    Then the dices should be chosen randomly

  Scenario: Dice choice phase
    When the engine ask the players to choose their dices
    Then the engine get the last dice remaining

  Scenario: Game do a time-forward with his dice
    When everyone has chosen their dice
    And the engine got the last remaining dice
    Then the engine do a time-forward with the value of the distance in the actual face of the dice

