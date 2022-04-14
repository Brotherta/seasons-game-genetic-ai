package com.utils;

import com.game.engine.Board;
import com.game.engine.dice.Cup;
import com.game.engine.dice.Dice;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RandomDiceStepdefs {

    private GameEngine engine;
    private Board board;
    private ArrayList<Player> players;
    private Cup[] cups;

    @Given("the game engine launched the game")
    public void the_game_engine_launched_the_game() {
        engine = new GameEngine(Util.getRandomInt(3) + 2);
        board = engine.getBoard();
    }

    @And("there is some players playing the game")
    public void there_is_some_players_playing_the_game() {
        players = engine.getPlayersCentralManager().getPlayerList();
        assertTrue(engine.getPlayersCentralManager().getAmountOfPlayers() >= 2);
    }

    @And("the dices has been loaded")
    public void the_dices_has_been_loaded() {
        cups = engine.getCups();
    }

    @Given("there is less than {int} players")
    public void there_is_less_than_players(Integer playersAmount) {
        engine = new GameEngine(Util.getRandomInt(2) + 2);
        board = engine.getBoard();
        assertTrue(engine.getPlayersCentralManager().getAmountOfPlayers() < 4);
    }

    @Then("the dices should be chosen randomly")
    public void the_dices_should_be_chosen_randomly() {
        if(engine.getPlayersCentralManager().getAmountOfPlayers() < 4) {
            Cup[][] cups = new Cup[2][];
            for (int i = 0; i < cups.length; i++) {
                engine.loadDices();
                cups[i] = engine.getCups();
            }

            Cup[] base = cups[0];
            for (int i = 1; i < cups.length; i++) {
                for (Cup cI : cups[i]) {
                    for (Cup cBase : base) {
                        for (Dice dI : cI.getDices()) {
                            for (Dice dBase : cBase.getDices()) {
                                if (dI.getDiceId() != dBase.getDiceId()) {
                                    assertNotEquals(dI.getDiceId(), dBase.getDiceId());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            fail("All dices are equals");
        }
    }

    private Dice lastDice;

    @When("the engine ask the players to choose their dices")
    public void the_engine_ask_the_players_to_choose_their_dices() {
        engine.doTurn(board.getYear());
        lastDice = engine.getBoard().getBoardDice();
    }

    @Then("the engine get the last dice remaining")
    public void the_engine_get_the_last_dice_remaining() {
        for(int i = 0; i < engine.getPlayersCentralManager().getPlayerList().size(); i++) {
            assertNotEquals(engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i).getActualDice().getDiceId(), lastDice.getDiceId());
        }
    }

    private int oldTime;
    @When("everyone has chosen their dice")
    public void everyone_has_chosen_their_dice() {
        oldTime = board.getMonth();
        engine.doTurn(board.getYear());
    }

    @When("the engine got the last remaining dice")
    public void the_engine_got_the_last_remaining_dice() {
        assertNotNull(board.getBoardDice());
    }

    @Then("the engine do a time-forward with the value of the distance in the actual face of the dice")
    public void the_engine_do_a_time_forward_with_the_value_of_the_distance_in_the_actual_face_of_the_dice() {
        assertEquals((board.getMonth() - board.getBoardDice().getActualFace().getDistance()) % 12, oldTime);
    }
}
