package com.game.playerassets.ia.strategy.energy;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.EndStrategyTest;
import com.game.playerassets.ia.strategy.Strategy;
import com.utils.loaders.cards.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class EnergyStrategyTest {
    GameEngine engine;
    Player player;
    PersonalBoard pb;
    Strategy endStrat = new EndStrategyTest(null);

    @BeforeEach
    void setUp() {
        engine = new GameEngine(4);
        player = engine.getPlayersCentralManager().getPlayerByID(0);
        pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getPlayerTurnManager().addGameplayChoice(GameplayChoice.ACTIVATE);
    }

    @Test
    void EnergyPriceSellCurrentSeasonTest() {
        // Winter current season
        EnergyType energyMostExpensive = EnergyType.EARTH;
        player.setEnergyStrategy(new EnergyPriceSellCurrentSeason(endStrat));

        player.getFacadeIA().setChoosableEnergiesF(new int[4], 4);

        int[] energyChosen = player.chooseEnergies();

        assertArrayEquals(new int[]{0, 0, 0, 4}, energyChosen);
    }

    @Test
    void EnergyPriceSellNextSeasonTest() {
        // Winter current season
        EnergyType energyMostExpensive = EnergyType.FIRE;
        player.setEnergyStrategy(new EnergyPriceSellNextSeason(endStrat));

        player.getFacadeIA().setChoosableEnergiesF(new int[4], 4);

        int[] energyChosen = player.chooseEnergies();

        assertArrayEquals(new int[]{0, 0, 4, 0}, energyChosen);
    }

    @Test
    void EnergyToInvokeTest() {
        player.setEnergyStrategy(new EnergyToInvoke(new EnergyPriceSellCurrentSeason(endStrat)));
        Card cardCost4Fire = new Card("4Fire", 1, 0, 0, new int[]{0, 0, 4, 0}, Type.FAMILIAR);
        Card cardCost2Fire2Water = new Card("2Fire2Water", 1, 0, 0, new int[]{0, 2, 2, 0}, Type.FAMILIAR);
        pb.getCardManager().addCard(cardCost4Fire);
        pb.getCardManager().addCard(cardCost2Fire2Water);

        // Chooses to get energy from 4Fire.
        player.getFacadeIA().setChoosableEnergiesF(new int[4], 4);
        int[] energyChosen = player.chooseEnergies();
        assertArrayEquals(new int[]{0, 0, 4, 0}, energyChosen);

        // Chooses to get energy from 2fire2water
        player.getFacadeIA().setChoosableEnergiesF(new int[4], 4);
        pb.getCardManager().getCards().remove(cardCost4Fire);
        energyChosen = player.chooseEnergies();
        assertArrayEquals(new int[]{0, 2, 2, 0}, energyChosen);

        // Chooses to get energy from the current season, he doesn't have cards that cost energies.
        player.getFacadeIA().setChoosableEnergiesF(new int[4], 4);
        pb.getCardManager().getCards().clear();
        energyChosen = player.chooseEnergies();
        assertArrayEquals(new int[]{0, 0, 0, 4}, energyChosen); // Winter -> choose 4 EARTH

        // Chooses to get the 2 first energies.
        player.getFacadeIA().setChoosableEnergiesF(new int[4], 2);
        pb.getCardManager().getCards().clear();
        pb.getCardManager().addCard(cardCost2Fire2Water);
        energyChosen = player.chooseEnergies();
        assertArrayEquals(new int[]{0, 2, 0, 0}, energyChosen); // Winter -> choose 4 EARTH

        // Chooses to get the 4energies from the card then energy of the current season.
        player.getFacadeIA().setChoosableEnergiesF(new int[4], 6);
        energyChosen = player.chooseEnergies();
        assertArrayEquals(new int[]{0, 2, 2, 2}, energyChosen); // Winter -> choose 4 EARTH
    }
}
