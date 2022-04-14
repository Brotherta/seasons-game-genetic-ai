package com.game;

import com.game.engine.dice.Dice;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.IA;
import com.game.playerassets.ia.Player;
import com.utils.stats.StatsManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PersonalBoardStepdefs {
    private PersonalBoard personalBoard;
    int initialCrystal;
    Player player;

    @Mock
    Dice d ;

    @Mock
    FacadeIA fd;

    @Mock
    GameEngine gameEngine = mock(GameEngine.class);

    @Mock
    StatsManager statsManager = mock(StatsManager.class);

    @Given("the player has a Dice which authorize to sell")
    public void the_Player_has_a_Dice_which_authorize_to_sell(){
        Player player = new IA("test ia", fd, 1);
        personalBoard = new PersonalBoard(gameEngine);
        personalBoard.setStatsManager(statsManager);
        personalBoard.setPlayer(player);
        personalBoard.setActualDice(d);
        initialCrystal = personalBoard.getCrystal();

    }

    @And("decide to sell {int} Energy FIRE")
    public void decide_to_sell_2_Energy_FIRE(int i){
        personalBoard.getEnergyManager().addEnergy(EnergyType.FIRE, i);
        personalBoard.crystallize(EnergyType.FIRE, i );
    }


    @Then("he gets {int} Crystals")
    public void he_gets_4_Crystals(int i){
        assertEquals(personalBoard.getCrystal(),initialCrystal+i);
    }
}