package com.game.engine.energy;

import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.IA;
import com.game.playerassets.ia.Player;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnergyManagerGaugeStepDefs {
    PersonalBoard personalBoard;
    Player player;
    int amountBefore;

    @Mock
    Dice d = mock(Dice.class);

    @Mock
    GameEngine engine;

    @Mock
    FacadeIA fdb;

    @Given("a player with {int} energy in stock")
    public void init_player_with_energy(int initialEnergyAmount) {
        personalBoard = new PersonalBoard(engine);
        player = new IA("Test IA", fdb, 1);
        personalBoard.getEnergyManager().addEnergy(EnergyType.FIRE, initialEnergyAmount);
        amountBefore = personalBoard.getEnergyManager().getAmountofEnergies();
    }

    @Given("the player pick a dice")
    public void player_pick_dice() {
        assertNotNull(d);
        DiceFace df = new DiceFace(1, false, false, false, 1, 0, new int[]{0, 0, 2, 0});
        when(d.rollFace()).thenReturn(df);
        when(d.getActualFace()).thenReturn(df);
    }

    @Then("the player gains energies")
    public void gain_energy() {
        personalBoard.getEnergyManager().addEnergy(d.getActualFace().getEnergiesAmount());
        int amountAfter = personalBoard.getEnergyManager().getAmountofEnergies();
        assertTrue(amountAfter > amountBefore);
    }

    @But("the player gains only {int} energy")
    public void but_no_chance_haha(int remaining) {
        int amountAfter = personalBoard.getEnergyManager().getAmountofEnergies();
        assertEquals(amountAfter - amountBefore, remaining);
    }

}
