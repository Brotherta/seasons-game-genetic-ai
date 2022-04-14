#language: en
Feature: EnergyManager

  Background:
    Given a player with 6 energy in stock

  Scenario: The player can't have more than 7 energies
    Given the player pick a dice
    Then the player gains energies
    But the player gains only 1 energy