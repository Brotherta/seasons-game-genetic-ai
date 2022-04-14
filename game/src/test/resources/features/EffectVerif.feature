#language: en

Feature: Effect

  Background:
    Given a game with 4 players

  Scenario: a player use the AirEffect
    Given the player draw the card with the AirEffect
    Then the player invoke the card and increase his invoke capacity

  Scenario: a player use the EarthEffect
    Given the player draw the card the EarthEffect
    Then the player invoke the card and gain crystals by the EarthEffect

  Scenario: a player use the FigrimEffect
    Given the player draw the card with the FigrimEffect
    Then the player invoke the card and gets crystals from other players

  Scenario: a player use the GrismineEffect
    Given the player draw the card with the GrismineEffect
    Then the player invoke the card and gets the energies of the player he choosed

  Scenario: a player use the IoEffect
    Given the player draw the card with the IoEffect
    Then the player invoke the card and gain crystals by the IoEffect

  Scenario: a player use the OlafEffect
    Given the player draw the card with the OlafEffect
    Then the player invoke the card and gain crystals by the OlafEffect

  Scenario: a player use the SpringEffect
    Given the player draw the card with the SpringEffect
    Then the player invoke the card and gain crystals by the SpringEffect

  Scenario: a player use the SyllasEffect
    Given the player draw the card with the SyllasEffect
    Then the player invoke the card and make each opponent sacrifice a card

  Scenario: a player use the TimeEffect
    Given the player draw the card with the TimeEffect
    Then the player invoke the card and gains energies

  Scenario: a player use the RagfieldEffect
    Given the player draw the card with the RagfieldEffect
    Then the player invoke the card and gains crystals if he can activate the card

  Scenario: a player use the GreatnessEffect
    Given the player draw the card with the GreatnessEffect
    Then the player invoke the card and gain crystals for each magical object type card invoked

  Scenario: a player use the NariaEffect
    Given the player draw the card with the NariaEffect
    Then the player invoke the card, draw some, keep one and give the others

  Scenario: a player use the ChestEffect
    Given the player draw the card with the ChestEffect
    Then the player invoke the card, and if he can activate, gains crystals

  Scenario: a player use the FireEffect
    Given the player draw a card with the FireEffect
    Then the player invoke the card and draws cards and keep 1

  Scenario: a player use the DivinEffect
    Given the player draw the card with the DivinEffect
    Then the player invoke the card, draw some, keep one and invoke it for free

  Scenario: a player use the YjangEffecy
    Given the player draw the card with the YjangEffect
    Then the player invoke a card, YjangEffect is applied

  Scenario: a player use the TemporalEffect at Month 1 Year 1
    Given the player draw the card with the TemporalEffect
    Then the player invoke the card, and change the month by 3

  Scenario: a player use the TemporalEffect at Month 11 Year 1
    Given the player draw the card with the TemporalEffect a second time
    Then the player invoke the card, choose 3 step forward, and change the month and the year

  Scenario: a player use the AmsugEffect
    Given the player draw the card with the AmsugEffect
    Then the player invoke the card, and all players have one card magic object returned in their hand if they got one

  Scenario: a player use the DreamEffect
    Given the player draw the card with the DreamEffect
    Then the player invoke the card, activate his effect, sacrifices all his energies and invokes one card for free

  Scenario: a player use the FortuneEffect
    Given the player draw the card with the FortuneEffect
    Then the player invoke the card, and on following invoke the energyCost will be reduced

  Scenario: a player use the GrimoirEffect
    Given the player draw the card with the GrimoirEffect
    Then the player invoke the card, gains energies and increase his energies capacity while the card is invoked

  Scenario: a player use the IshtarEffect
    Given the player draw a card with the IshtarEffect
    Then the player invoke the card and trade his energies for crystals

  Scenario: a player use the KairnEffect
    Given the player draw the card with the KairnEffect
    Then the player invoke the card, when he activates the effect, sacrifice an energy and make players lose crystals

  Scenario: a player use the WaterEffect
    Given the player draw the card with the WaterEffect
    Then the player invoke the card, gain 4 energies of his choice, and put them on the WaterAmulet

  Scenario: a player use the PowerEffect
    Given the player draw the card with the PowerEffect
    Then the player invoke the card, sacrifice it, draw 1 card and gain 2 invocation gauge point

  Scenario: a player use the KnowLedgeEffect
    Given the player draw the card Potion of Knowledge
    Then the player invoke his card and then activates it, ha hasn't the card in his invocation cards anymore

  Scenario: a player use the LifeEffect
    Given the player draw the card with the LifeEffect
    Then the player invoke the card and when he activate the effect, sacrifice the card and crystallize all his energies

  Scenario: a player use the MendiantEffect
    Given the player draw the card with MendiantEffect, he has 0 energy in stock
    Then the player invoke the card, at the end of the round he gain 1 energy.