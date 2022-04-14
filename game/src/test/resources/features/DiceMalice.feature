Feature: MaliceEffect

  Background:
    Given a game with 4 players for Malice Effect

  Scenario: a player use the MaliceEffect
    Given the player invoke the card with the MaliceEffect
    Then the player choose to activate his card to redraw the dice