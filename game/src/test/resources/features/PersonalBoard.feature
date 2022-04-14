#language: en
Feature: PersonalBoard

  Scenario:
    Given the player has a Dice which authorize to sell
    And decide to sell 2 Energy FIRE
    Then he gets 4 Crystals
