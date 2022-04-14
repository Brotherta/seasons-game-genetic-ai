package com.game.engine.effects;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.effects.effects.*;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyUtils;
import com.game.playerassets.ia.strategy.freeinvocation.RandomInvocationFreeStrategy;
import com.game.playerassets.ia.strategy.gameplay.invocation.grismine.RandomCopyStrategy;
import com.game.playerassets.ia.strategy.sacrificeenergy.RandomSacrificeEnergyStrategy;
import com.utils.Util;
import com.utils.loaders.cards.Type;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EffectVerifStepdefs {

    GameEngine gameEngine;
    PersonalBoard pb;
    Player player;


    @Given("a game with {int} players")
    public void init_game(int nbPlayers) {
        gameEngine = new GameEngine(nbPlayers);
        player = gameEngine.getPlayersCentralManager().getPlayerByID(0);
        pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getCardManager().reset();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(10);

    }

    /**
     * TEST OF AIR EFFECT
     */
    @Given("the player draw the card with the AirEffect")
    public void draw_card_increase_invoke_capacity() {
        Card card = new Card("testAirEffect", 1, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
        card.setEffect(new AirEffect("airEffect", EffectType.DEFAULT, gameEngine));
        int nbCardInPbBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardInPbAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardInPbAfter > nbCardInPbBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("airEffect"));
    }

    @Then("the player invoke the card and increase his invoke capacity")
    public void increase_capacity() {
        int invokeCapacityBefore = pb.getCardManager().getInvokeDeck().getGaugeSize();
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int invokeCapacityAfter = pb.getCardManager().getInvokeDeck().getGaugeSize();
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter < nbCardBefore);
        assertTrue(invokeCapacityAfter > invokeCapacityBefore);
    }

    /**
     * TEST OF EARTH EFFECT
     */
    @Given("the player draw the card the EarthEffect")
    public void draw_card_gain_crystals() {
        Card card = new Card("testEarthEffect", 2, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
        card.setEffect(new EarthEffect("earthEffect", EffectType.DEFAULT, gameEngine));
        int nbCardsInPbBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardsInPbAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardsInPbAfter > nbCardsInPbBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("earthEffect"));
    }

    @Then("the player invoke the card and gain crystals by the EarthEffect")
    public void gain_crystals() {
        int nbCrystalsBefore = pb.getCrystal();
        int nbCardBefore = pb.getCardManager().getCards().size();

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        int nbCrystalsAfter = pb.getCrystal();
        int nbCardAfter = pb.getCardManager().getCards().size();

        assertTrue(nbCardAfter < nbCardBefore);
        assertTrue(nbCrystalsAfter > nbCrystalsBefore);
    }

    /**
     * TEST OF FIGRIM EFFECT
     */
    @Given("the player draw the card with the FigrimEffect")
    public void draw_card_receive_crystals() {
        Card card = new Card("testFigrimEffect", 3, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new FigrimEffect("figrimEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsActivationEffect() || newEffect.getIsPermanentEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("figrimEffect"));
    }

    @Then("the player invoke the card and gets crystals from other players")
    public void receive_crystals() {
        gameEngine.setDescription(new StringBuilder());
        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            personalBoard.addCrystal(5);
        }
        int nbOfCrystalsBefore = pb.getCrystal();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbOfCrystalsAfter = pb.getCrystal();
        int nbOfPlayers = gameEngine.getPlayersCentralManager().getAmountOfPlayers() - 1;
        assertEquals(nbOfCrystalsBefore + nbOfPlayers, nbOfCrystalsAfter);
    }

    /**
     * TEST OF GRISMINE EFFECT
     */
    @Given("the player draw the card with the GrismineEffect")
    public void the_player_draw_a_card_with_the_CloneStockEffect() {
        Card card = new Card("testGrismineEffect", 4, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        card.setEffect(new GrismineEffect("grismineEffect", EffectType.DEFAULT, gameEngine));
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("grismineEffect"));
    }

    @Then("the player invoke the card and gets the energies of the player he choosed")
    public void the_player_invoke_the_card_and_gets_the_energy_of_a_player_he_choosed() {
        int[] energyOfTheOthers = new int[]{1, 2, 2, 2};
        for (int i = 1; i < gameEngine.getPlayersCentralManager().getAmountOfPlayers(); i++) {
            PersonalBoard pbtemp = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i);
            pbtemp.getEnergyManager().addEnergy(energyOfTheOthers);
        }
        //on vérifie que le player a bien 0 energies pour commencer.
        pb.getEnergyManager().addEnergy(new int[]{0, 4, 0, 0});
        int nbOfEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        assertArrayEquals(new int[]{0, 4, 0, 0}, pb.getEnergyManager().getAmountOfEnergiesArray());
        player.getFacadeIA().setPlayerToCopyF(gameEngine.getPlayersCentralManager().getPlayerByID(1));
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbOfEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();
        // on verifie après invocation qu'il a bien ses energies puis copie les energies de son ennemie sans dépasser sa jauge d'energie
        // le cas ou il ne dispose d'aucunes energie et copie l'entiereté des energies du joueur a été vérifié
        assertTrue(nbOfEnergiesAfter > nbOfEnergiesBefore);
        assertNotEquals(new int[]{0, 4, 0, 0}, pb.getEnergyManager().getAmountOfEnergiesArray());
    }

    /**
     * TEST OF IO EFFECT
     */
    @Given("the player draw the card with the IoEffect")
    public void draw_io_effect() {
        Card card = new Card("testIoEffect", 5, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new IoEffect("ioEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsPermanentEffect() || newEffect.getIsActivationEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("ioEffect"));
    }

    @Then("the player invoke the card and gain crystals by the IoEffect")
    public void io_effect() {
        int nbCrystalsBefore = pb.getCrystal();
        int nbCardBefore = pb.getCardManager().getCards().size();

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        int nbCrystalsAfter = pb.getCrystal();
        int nbCardAfter = pb.getCardManager().getCards().size();

        assertTrue(nbCardAfter < nbCardBefore);
        assertTrue(nbCrystalsAfter > nbCrystalsBefore);
    }

    /**
     * TEST OF OLAF EFFECT
     */
    @Given("the player draw the card with the OlafEffect")
    public void draw_card_olaf_effect() {
        Card card = new Card("testOlafEffect", 6, 0,0, new int[] {0,0,0,0}, Type.OBJECT);
        card.setEffect(new OlafEffect("olafEffect", EffectType.DEFAULT, gameEngine));
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("olafEffect"));
    }

    @Then("the player invoke the card and gain crystals by the OlafEffect")
    public void olaf_effect() {
        int nbCrystalsBefore = pb.getCrystal();
        int nbCardBefore = pb.getCardManager().getCards().size();

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        int nbCrystalsAfter = pb.getCrystal();
        int nbCardAfter = pb.getCardManager().getCards().size();

        assertTrue(nbCardAfter < nbCardBefore);
        assertTrue(nbCrystalsAfter > nbCrystalsBefore);
    }

    /**
     * TEST OD SPRING EFFECT
     */
    @Given("the player draw the card with the SpringEffect")
    public void draw_card_spring_effect() {
        Card card = new Card("testSpringEffect", 7, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new SpringEffect("springEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsPermanentEffect() || newEffect.getIsActivationEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("springEffect"));
    }

    @Then("the player invoke the card and gain crystals by the SpringEffect")
    public void spring_effect() {
        int nbCrystalsBefore = pb.getCrystal();
        int nbCardBefore = pb.getCardManager().getCards().size();

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        int nbCrystalsAfter = pb.getCrystal();
        int nbCardAfter = pb.getCardManager().getCards().size();

        assertTrue(nbCardAfter < nbCardBefore);
        assertTrue(nbCrystalsAfter > nbCrystalsBefore);
    }

    /**
     * TEST OF SYLLAS EFFECT
     */
    @Given("the player draw the card with the SyllasEffect")
    public void draw_card_syllas_effect() {
        Card card = new Card("testSyllasEffect", 8, 0,0, new int[] {0,0,0,0}, Type.OBJECT);
        card.setEffect(new SyllasEffect("syllasEffect", EffectType.DEFAULT, gameEngine));
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("syllasEffect"));
    }

    @Then("the player invoke the card and make each opponent sacrifice a card")
    public void syllas_effect() {
        ArrayList<Integer> nbCardsBefore = new ArrayList<>();
        for (int i = 1; i < gameEngine.getPlayersCentralManager().getAmountOfPlayers(); i++) {
            PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i);
            Card card = new Card("testCard", i, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
            card.setEffect(new SyllasEffect("syllasEffect", EffectType.DEFAULT, gameEngine));
            personalBoard.getCardManager().getInvokeDeck().addCard(card);
            nbCardsBefore.add(personalBoard.getCardManager().getInvokeDeck().getCards().size());
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        ArrayList<Integer> nbCardsAfter = new ArrayList<>();
        for (int j = 1; j < gameEngine.getPlayersCentralManager().getAmountOfPlayers(); j++) {
            PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(j);
            nbCardsAfter.add(personalBoard.getCardManager().getCards().size());
        }

        assertEquals(nbCardsAfter.size(), nbCardsBefore.size());
        for (int k = 0; k < nbCardsAfter.size(); k++) {
            assertTrue(nbCardsBefore.get(k) > nbCardsAfter.get(k));
        }
    }

    /**
     * TEST OF TIME EFFECT
     */
    @Given("the player draw the card with the TimeEffect")
    public void draw_card_time_effect() {
        Card card = new Card("testTimeEffect", 9, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new TimeEffect("timeEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsPermanentEffect() || newEffect.getIsActivationEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("timeEffect"));
    }

    @Then("the player invoke the card and gains energies")
    public void time_energies() {
        int nbOfEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbOfEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();
        assertTrue(nbOfEnergiesAfter > nbOfEnergiesBefore);
    }

    /**
     * TEST OF RAGFIELD EFFECT
     */
    @Given("the player draw the card with the RagfieldEffect")
    public void draw_card_ragfield_effect() {
        Card card = new Card("testRagfieldEffect", 10, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new RagfieldEffect("ragfieldEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsPermanentEffect() || newEffect.getIsActivationEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("ragfieldEffect"));
    }

    @Then("the player invoke the card and gains crystals if he can activate the card")
    public void ragfield_effect() {
        // We will manually add cards to each players invocation deck
        // so that we can simulate the activation condition
        EffectTemplate effectForTestCards = new AirEffect("fakeEffect", EffectType.DEFAULT, gameEngine);

        for (int i = 0; i < 5; i++) {
            Card testCard = new Card("testCards", 0, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
            testCard.setEffect(effectForTestCards);
            pb.getCardManager().getInvokeDeck().addCard(testCard);
        }

        assertEquals(5, pb.getCardManager().getInvokeDeck().getCards().size());

        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!(personalBoard.equals(pb))) {
                personalBoard.getCardManager().getInvokeDeck().upInvocationGauge(5);
                Card newCard = new Card("fakeCard", 0, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
                newCard.setEffect(effectForTestCards);
                personalBoard.getCardManager().getInvokeDeck().addCard(newCard);
                assertEquals(1, personalBoard.getCardManager().getInvokeDeck().getCards().size());
            }
        }

        // Now we will simulate the activation condition to see if we can apply the effect
        int cardsPlayerHasInvoked = pb.getCardManager().getInvokeDeck().getCards().size();
        boolean canActivate = true;
        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!(personalBoard.equals(pb))) {
                int cardsInvoked = personalBoard.getCardManager().getInvokeDeck().getCards().size();
                if (cardsInvoked > cardsPlayerHasInvoked) {
                    canActivate = false;
                }
            }
        }

        if(canActivate) {
            int nbOfCrystalsBefore = pb.getCrystal();
            pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
            int nbOfCrystalsAfter = pb.getCrystal();
            assertEquals(nbOfCrystalsBefore + 20, nbOfCrystalsAfter);
        }
    }

    /**
     * TEST OF GREATNESS EFFECT
     */
    @Given("the player draw the card with the GreatnessEffect")
    public void draw_card_greatness_effect() {
        Card card = new Card("Scepter of Greatness", 11, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new GreatnessEffect("greatnessEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("greatnessEffect"));
    }

    @Then("the player invoke the card and gain crystals for each magical object type card invoked")
    public void greatness_effect() {
        int nbCrystalsBefore = pb.getCrystal();
        EffectTemplate newEffect = new AirEffect("tempEffect", EffectType.DEFAULT, gameEngine);
        for (int i = 0; i < 5; i++) {
            Card objectCard = new Card("objectCard", 0, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
            objectCard.setEffect(newEffect);
            pb.getCardManager().getInvokeDeck().addCard(objectCard);
        }
        for (int j = 0; j < 3; j++) {
            Card familiarCard = new Card("familiarCard", 0, 0, 0, new int[] {0,0,0,0}, Type.FAMILIAR);
            familiarCard.setEffect(newEffect);
            pb.getCardManager().getInvokeDeck().addCard(familiarCard);
        }
        assertEquals(8, pb.getCardManager().getInvokeDeck().getCards().size());
        int nbCardsObject = 0;
        for (Card card : pb.getCardManager().getInvokeDeck().getCards()) {
            if (card.getType().equals(Type.OBJECT)) {
                nbCardsObject++;
            }
        }
        assertEquals(5, nbCardsObject);
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbCrystalsAfter = pb.getCrystal();
        assertTrue(nbCrystalsAfter > nbCrystalsBefore);
        assertEquals(nbCrystalsBefore + 3 * nbCardsObject, nbCrystalsAfter);
    }

    /**
     * TEST OF NARIA EFFECT
     */
    @Given("the player draw the card with the NariaEffect")
    public void draw_card_naria_effect() {
        Card card = new Card("testNariaEffect", 12, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new NariaEffect("nariaEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("nariaEffect"));
    }

    @Then("the player invoke the card, draw some, keep one and give the others")
    public void naria_effect() {
        int nbplayer = gameEngine.getPlayersCentralManager().getAmountOfPlayers();

        int[] nbCardsBefore = new int[nbplayer];
        int[] nbCardsAfter = new int[nbplayer];
        for (int i = 0 ; i < nbplayer ; i++) {
            nbCardsBefore[i] = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i).getCardManager().getCards().size();
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));


        for (int i = 0 ; i < nbplayer ; i++) {
            nbCardsAfter[i] = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i).getCardManager().getCards().size();
        }

        assertEquals(nbCardsAfter.length, nbCardsBefore.length);
        for (int i = 0; i < nbCardsAfter.length; i++) {
            if (i == 0) {   //the player had one card, gain one so two and finally invoke 1 so one left so no changes
                assertEquals(nbCardsBefore[i], nbCardsAfter[i]);
            } else {
                assertEquals(nbCardsBefore[i] + 1,  nbCardsAfter[i]);
            }

        }
    }

    /**
     * TEST OF CHEST EFFECT
     */
    @Given("the player draw the card with the ChestEffect")
    public void draw_card_chest_effect() {
        Card card = new Card("testChestEffect", 14, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new ChestEffect("chestEffect", EffectType.DEFAULT, gameEngine);
        if (newEffect.getIsActivationEffect() || newEffect.getIsPermanentEffect()) {
            newEffect.setActivationEffect(false);
            newEffect.setPermanentEffect(false);
            newEffect.setSingleEffect(true);
        }
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("chestEffect"));
    }

    @Then("the player invoke the card, and if he can activate, gains crystals")
    public void chest_effect() {
        // We will manually add energies to player's personalBoard
        // so that we can simulate the activation condition
        for (int i = 0; i < 5; i++) {
            pb.getEnergyManager().addEnergy(EnergyType.values()[Util.getRandomInt(4)]);
        }
        assertEquals(5, pb.getEnergyManager().getAmountofEnergies());
        // now we simulate the can activate method

        int REQUIRE = 4;
        boolean canActivate = !(pb.getEnergyManager().getAmountofEnergies() < REQUIRE);

        if (canActivate) {
            int nbCrystalsBefore = pb.getCrystal();
            pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
            int nbCrystalsAfter = pb.getCrystal();
            assertEquals(nbCrystalsBefore + 3, nbCrystalsAfter);
        }
    }

    /**
     * TEST OF FIRE EFFECT
     */
    @Given("the player draw a card with the FireEffect")
    public void draw_card_fire_effect() {
        Card card = new Card("testFireEffect", 15, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new FireEffect("fireEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("fireEffect"));
    }

    @Then("the player invoke the card and draws cards and keep 1")
    public void invoke_card() {
        int nbCardBefore = pb.getCardManager().getCards().size();
        int discardPileSizeBefore = gameEngine.getDeck().dispileCardsSize();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbCardAfter = pb.getCardManager().getCards().size();
        int discardPileSizeAfter = gameEngine.getDeck().dispileCardsSize();
        assertEquals(nbCardAfter, nbCardBefore);
        assertEquals(discardPileSizeBefore + 3, discardPileSizeAfter);
    }

    /**
     * TEST OF DIVIN EFFECT
     */
    @Given("the player draw the card with the DivinEffect")
    public void draw_card_divin_effect() {
        Card card = new Card("Divine Calice", 17, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        Card freeCard = new Card("Free Card", 17, 0, 5, new int[]{1, 1, 1, 1}, Type.OBJECT);
        EffectTemplate newEffect = new DivinEffect("DivinEffect", EffectType.DEFAULT, gameEngine);
        EffectTemplate emptyEffect = new EmptyEffect("test", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        freeCard.setEffect(emptyEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        pb.getCardManager().addCard(freeCard);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("DivinEffect"));
    }

    @Then("the player invoke the card, draw some, keep one and invoke it for free")
    public void draw_some_then_invoke_one_for_free() {
        int nbCardInvokedBefore = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbEnergyBefore = pb.getEnergyManager().getAmountofEnergies();
        int nbCrystalBefore = pb.getCrystal();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        int nbCardInvokeAfter = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbEnergyAfter = pb.getEnergyManager().getAmountofEnergies();
        int nbCrystalAfter = pb.getCrystal();

        assertEquals(nbCardInvokeAfter, nbCardInvokedBefore + 2);
        assertTrue(nbEnergyAfter >= nbEnergyBefore );
        assertTrue(nbCrystalAfter >= nbCrystalBefore );
    }

    /**
     * TEST OF YJANG EFFECT
     */
    @Given("the player draw the card with the YjangEffect")
    public void thePlayerInvokeTheCardWithTheYjangEffect() {
        int nbCardBefore = pb.getCardManager().getCards().size();

        Card card = new Card("Yjang Effect", 17, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new YjangEffect("Yjang Effect", EffectType.INVOKE, gameEngine);
        card.setEffect(newEffect);
        pb.getCardManager().addCard(card);

        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Yjang Effect"));
    }

    @Mock
    Strategy stratInvokeForFreeToMock = mock(RandomInvocationFreeStrategy.class);

    @Then("the player invoke a card, YjangEffect is applied")
    public void thePlayerInvokeACardTheEffectIsApplied() {
        player.setInvocationFreeStrategy(stratInvokeForFreeToMock);
        when(stratInvokeForFreeToMock.canAct(player.getFacadeIA())).thenReturn(pb.getCardManager().getCards().get(0));

        pb.getCardManager().invoke(player.chooseCardToInvokeForFree(), true);

        int energyBefore = pb.getEnergyManager().getAmountofEnergies();
        pb.getCardManager().invoke(player.chooseCardToInvokeForFree(), true);
        int energyAfter = pb.getEnergyManager().getAmountofEnergies();

        assertTrue(energyBefore < energyAfter);

    }

    /**
     * TEST TEMPORAL BOOTS EFFECT
     */


    @Given("the player draw the card with the TemporalEffect")
    public void the_player_draw_the_card_with_the_TemporalEffect(){

        Card card = new Card("Temporal Boots", 19, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new TemporalEffect("Temporal Boots", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        newEffect.setCard(card);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Temporal Boots"));
    }


    @Then("the player invoke the card, and change the month by {int}")
    public void the_player_invoke_the_card_and_change_the_month(int nbMonth){
        //il choisi d'invoker sa carte "Temporal Boots"
        int initialYear = gameEngine.getBoard().getYear();
        int initialMonth = gameEngine.getBoard().getMonth();
        SeasonType initialSeason = gameEngine.getBoard().getSeason();
        Card card = pb.getCardManager().getCards().get(0);
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        player.getFacadeIA().setTimeStepF(nbMonth);
        card.getEffect().applyEffect(gameEngine,player);
        int newYear = gameEngine.getBoard().getYear();
        int newMonth = gameEngine.getBoard().getMonth();
        SeasonType newSeason = gameEngine.getBoard().getSeason();
        assertEquals(initialYear,newYear);
        assertEquals(initialMonth+nbMonth,newMonth);
        assertEquals(gameEngine.getBoard().getSeasonByMonth(),newSeason);
    }

    /**
     * TEST TEMPORAL EFFECT 2
     */
    @Given("the player draw the card with the TemporalEffect a second time")
    public void the_player_draw_the_card_with_the_TemporalEffect_a_second_time(){
        //on doit etre année 1 mois 11
        while(gameEngine.getBoard().getMonth() < 11 && gameEngine.getBoard().getYear() == 1){
            gameEngine.getBoard().timeForward(1);
        }

        Card card = new Card("Temporal Boots", 19, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new TemporalEffect("Temporal Boots", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        newEffect.setCard(card);

        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Temporal Boots"));
    }

    @Then("the player invoke the card, choose {int} step forward, and change the month and the year")
    public void the_player_invoke_the_card_choose_3_step_forward_and_change_the_month_and_the_year(int nbMonth){
        //il choisi d'invoker sa carte "Temporal Boots"
        int initialYear = gameEngine.getBoard().getYear();
        int initialMonth = gameEngine.getBoard().getMonth();
        SeasonType initialSeason = gameEngine.getBoard().getSeason();
        Card card = pb.getCardManager().getCards().get(0);
        pb.getCardManager().getInvokeDeck().getCards().add(card);
        player.getFacadeIA().setTimeStepF(nbMonth);
        card.getEffect().applyEffect(gameEngine,player);
        int newYear = gameEngine.getBoard().getYear();
        int newMonth = gameEngine.getBoard().getMonth();
        SeasonType newSeason = gameEngine.getBoard().getSeason();
        assertNotEquals(initialYear,newYear);
        assertEquals(initialYear+1,newYear);
        assertEquals((initialMonth+nbMonth)%12,newMonth);
        assertEquals(gameEngine.getBoard().getSeasonByMonth(),newSeason);

    }
    @Given("the player draw the card Potion of Knowledge")
    public void draw_Potion_Knowledge(){
        Card card = new Card("Potion of Knowledge", 42, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new KnowledgeEffect("Potion of Knowledge", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        newEffect.setCard(card);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Potion of Knowledge"));
    }
    @Then("the player invoke his card and then activates it, ha hasn't the card in his invocation cards anymore")
    public void invoke_and_activate_PK(){
        int nbCardinvokBefore = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbCardInHandBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().invoke(pb.getCardManager().getCards().get(0));
        int nbCardinvokAfter = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbCardInHandAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardinvokBefore < nbCardinvokAfter);
        assertTrue(nbCardinvokBefore < nbCardInHandBefore);
        assertTrue(nbCardinvokAfter > nbCardInHandAfter);
        Card card = pb.getCardManager().getInvokeDeck().getCards().get(0);
        card.getEffect().applyEffect(gameEngine,player);
        int nbCardInvokedAfterActivation = pb.getCardManager().getInvokeDeck().getCards().size();
        assertTrue(nbCardinvokAfter > nbCardInvokedAfterActivation);
        assertEquals(5, pb.getEnergyManager().getAmountofEnergies());

    }

    @Given("the player draw the card with the AmsugEffect")
    public void draw_card_amsug_effect() {
        Card card = new Card("testAmsugEffect", 16, 0, 0, new int[]{0, 0, 0, 0}, Type.FAMILIAR);
        EffectTemplate newEffect = new AmsungEffect("amsugEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("amsugEffect"));
    }

    @Then("the player invoke the card, and all players have one card magic object returned in their hand if they got one")
    public void amsug_effect() {
        int nbPlayer = gameEngine.getPlayersCentralManager().getAmountOfPlayers();
        int[] nbCardsHandBefore = new int[nbPlayer];
        int[] nbCardsInvokedBefore = new int[nbPlayer];
        int[] nbCardsHandAfter = new int[nbPlayer];
        int[] nbCardsInvokedAfter = new int[nbPlayer];

        EffectTemplate newEffect = new AirEffect("fakeEffect", EffectType.DEFAULT, gameEngine);

        for (int i = 0; i < nbPlayer; i++) {
            PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(i);
            nbCardsHandBefore[i] = personalBoard.getCardManager().getCards().size();
            Card card = new Card("returningCard", 0, 0, 0, new int[] {0,0,0,0}, Type.OBJECT);
            card.setEffect(newEffect);
            personalBoard.getCardManager().getInvokeDeck().addCard(card);
            nbCardsInvokedBefore[i] = personalBoard.getCardManager().getInvokeDeck().getCards().size();
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));

        for (int j = 0; j < nbPlayer; j++) {
            PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(j);
            nbCardsHandAfter[j] = personalBoard.getCardManager().getCards().size();
            nbCardsInvokedAfter[j] = personalBoard.getCardManager().getInvokeDeck().getCards().size();
        }

        assertEquals(nbCardsHandAfter.length, nbCardsHandBefore.length);
        assertEquals(nbCardsInvokedAfter.length, nbCardsInvokedBefore.length);
        assertEquals(nbCardsHandBefore.length, nbCardsInvokedBefore.length);
        assertEquals(nbCardsHandAfter.length, nbCardsInvokedAfter.length);

        for (int k = 0; k < nbPlayer; k++) {
            if (k == 0) {
                assertEquals(nbCardsHandBefore[k], nbCardsHandAfter[k]);
                assertEquals(nbCardsInvokedBefore[k], nbCardsInvokedAfter[k]);
            } else {
                assertEquals(nbCardsHandBefore[k] + 1, nbCardsHandAfter[k]);
                assertEquals(nbCardsInvokedBefore[k] - 1, nbCardsInvokedAfter[k]);
            }
        }
    }

    @Given("the player draw the card with the DreamEffect")
    public void draw_card_dream_effect() {
        Card card = new Card("dreamEffect", 17, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        Card freeCard = new Card("freeCard", 17, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new DreamEffect("dreamEffect", EffectType.DEFAULT, gameEngine);
        EffectTemplate freeEffect = new AirEffect("freeEffect", EffectType.DEFAULT, gameEngine);
        freeCard.setEffect(freeEffect);
        newEffect.setCard(card);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        pb.getCardManager().addCard(freeCard);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("dreamEffect"));
    }

    @Then("the player invoke the card, activate his effect, sacrifices all his energies and invokes one card for free")
    public void dream_effect() {
        for (int i = 0; i < 5; i++) {
            pb.getEnergyManager().addEnergy(EnergyType.values()[Util.getRandomInt(4)]);
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        pb.getCardManager().getInvokeDeck().getCards().get(0).getEffect().applyEffect(gameEngine, player);
        int nbCardsHandAfter = pb.getCardManager().getCards().size();
        int nbCardsInvokeAfter = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();

        assertEquals(0, nbCardsHandAfter);
        assertEquals(1, nbCardsInvokeAfter);
        assertEquals(0, nbEnergiesAfter);
    }

    @Given("the player draw the card with the FortuneEffect")
    public void draw_card_fortune_effect() {
        Card card = new Card("FortuneEffect", 18, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        Card reducedCard = new Card("reducedCard", 18, 0, 0, new int[]{1, 1, 1, 1}, Type.OBJECT);
        EffectTemplate newEffect = new FortuneEffect("FortuneEffect", EffectType.INVOKE, gameEngine);
        EffectTemplate reducedCardEffect = new AirEffect("reducedCardEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        reducedCard.setEffect(reducedCardEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        pb.getCardManager().addCard(reducedCard);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("FortuneEffect"));
    }

    @Then("the player invoke the card, and on following invoke the energyCost will be reduced")
    public void fortune_effect() {
        pb.getEnergyManager().addEnergy(new int[] {1,1,1,1});

        assertEquals(4, pb.getEnergyManager().getAmountofEnergies());

        pb.getCardManager().invoke(pb.getCardManager().getCards().get(0));
        pb.getCardManager().invoke(pb.getCardManager().getCards().get(0));
        assertEquals(1, pb.getEnergyManager().getAmountofEnergies());
        assertEquals(2, pb.getCardManager().getInvokeDeck().getCards().size());
        assertEquals(0, pb.getCardManager().getCards().size());
    }

    @Given("the player draw the card with the GrimoirEffect")
    public void draw_card_grimoir_effect() {
        Card card = new Card("GrimoirEffect", 19, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new GrimoirEffect("GrimoirEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("GrimoirEffect"));
    }

    @Then("the player invoke the card, gains energies and increase his energies capacity while the card is invoked")
    public void grimoir_effect() {
        int nbEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        assertEquals(nbEnergiesBefore + 2, pb.getEnergyManager().getAmountofEnergies());
        assertEquals(10, pb.getEnergyManager().getGauge());
    }

    @Given("the player draw a card with the IshtarEffect")
    public void draw_card_ishtar_effect() {
        Card card = new Card("IshtarEffect", 20, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new IshtarEffect("IshtarEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("IshtarEffect"));
    }

    @Then("the player invoke the card and trade his energies for crystals")
    public void trade_energies() {
        for (int i = 0; i < 3; i++) {
            pb.getEnergyManager().addEnergy(EnergyType.AIR);
        }

        int nbCrystalsBefore = pb.getCrystal();
        int nbEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        if (pb.getCardManager().getCards().get(0).getEffect().canActivate(gameEngine, player)) {
            Card card = pb.getCardManager().getCards().get(0);
            card.getEffect().applyEffect(gameEngine, player);
        }
        int nbCrystalsAfter = pb.getCrystal();
        int nbEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();
        assertEquals(nbCrystalsBefore + 9, nbCrystalsAfter);
        assertEquals(nbEnergiesBefore - 3, nbEnergiesAfter);
    }

    @Given("the player draw the card with the KairnEffect")
    public void draw_card_kairn_effect() {
        player.setCopyStrategy(new RandomCopyStrategy(null));
        player.setSacrificeEnergyStrategy(new RandomSacrificeEnergyStrategy(null));
        Card card = new Card("KairnEffect", 21, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new KairnEffect("KairnEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("KairnEffect"));
    }

    @Then("the player invoke the card, when he activates the effect, sacrifice an energy and make players lose crystals")
    public void kairn_effect() {
        for (int i = 0; i < 5; i++) {
            pb.getEnergyManager().addEnergy(EnergyType.values()[Util.getRandomInt(4)]);
        }

        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!(personalBoard.equals(pb))) {
                personalBoard.addCrystal(4);
            }
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        pb.getCardManager().getInvokeDeck().getCards().get(0).getEffect().applyEffect(gameEngine, player);
        assertEquals(4, pb.getEnergyManager().getAmountofEnergies());

        for (PersonalBoard personalBoard : gameEngine.getPlayersCentralManager().getPersonalBoardList()) {
            if (!(personalBoard.equals(pb))) {
                assertEquals(0, personalBoard.getCrystal());
            }
        }
    }

    @Given("the player draw the card with the WaterEffect")
    public void draw_with_water_effect() {
        Card card = new Card("Amulet of Water", 18, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new WaterEffect("WaterEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);

        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);

        assertEquals(pb.getCardManager().getCards().size(), nbCardBefore + 1);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("WaterEffect"));
    }

    @Then("the player invoke the card, gain {int} energies of his choice, and put them on the WaterAmulet")
    public void invoke_water_then_gain_energies_and_put_on_waterAmulet(int energiesAmount) {
        int nbCardInvokedBefore = pb.getCardManager().getInvokeDeck().getCards().size();

        int nbEnergyBeforeOnWaterAmulet = 0;
        if(!(pb.getEnergyManager().getWaterAmulet() == null)) {
            nbEnergyBeforeOnWaterAmulet = Arrays.stream(pb.getEnergyManager().getWaterAmulet().getEnergyList()).sum();
        }

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        assertEquals(pb.getCardManager().getInvokeDeck().getCards().size(), nbCardInvokedBefore + 1);

        assertEquals(Arrays.stream(pb.getEnergyManager().getWaterAmulet().getEnergyList()).sum(), nbEnergyBeforeOnWaterAmulet + energiesAmount);
    }

    @Given("the player draw the card with the PowerEffect")
    public void draw_with_power_effect() {
        Card card = new Card("Power Potion", 19, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new PowerEffect("Power Potion", EffectType.DEFAULT, gameEngine);
        newEffect.setCard(card);
        card.setEffect(newEffect);

        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);

        assertEquals(pb.getCardManager().getCards().size(), nbCardBefore + 1);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Power Potion"));
    }

    @Then("the player invoke the card, sacrifice it, draw {int} card and gain {int} invocation gauge point")
    public void invoke_power_sacrifice_draw_gain_invoc(int drawAmount, int invocUpAmount) {
        int nbCardInvokedBefore = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbCardDispileBefore = pb.getGameEngine().getDeck().dispileCardsSize();
        int nbCardBefore = pb.getCardManager().getCards().size();
        int invocationGaugeBefore = pb.getCardManager().getInvokeDeck().getGaugeSize();

        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        pb.getCardManager().getInvokeDeck().getCards().get(0).getEffect().applyEffect(pb.getGameEngine(), pb.getPlayer());

        assertEquals(pb.getCardManager().getInvokeDeck().getCards().size(), nbCardInvokedBefore);
        assertEquals(pb.getGameEngine().getDeck().dispileCardsSize(), nbCardDispileBefore + 1);
        assertEquals(nbCardBefore, drawAmount);
        assertEquals(pb.getCardManager().getCards().size(), drawAmount);
        assertEquals(pb.getCardManager().getInvokeDeck().getGaugeSize(), invocationGaugeBefore + invocUpAmount);
    }

    @Given("the player draw the card with the LifeEffect")
    public void draw_card_life_effect() {
        Card card = new Card("LifeEffect", 30, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new LifeEffect("LifeEffect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        newEffect.setCard(card);
        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);
        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("LifeEffect"));
    }

    @Then("the player invoke the card and when he activate the effect, sacrifice the card and crystallize all his energies")
    public void life_effect() {
        for (int i = 0; i < 4; i++) {
            pb.getEnergyManager().addEnergy(EnergyType.values()[Util.getRandomInt(4)]);
        }
        int nbEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        pb.getCardManager().invoke(StrategyUtils.getRandomInvokableCard(player.getFacadeIA()));
        pb.getCardManager().getInvokeDeck().getCards().get(0).getEffect().applyEffect(gameEngine, player);
        int nbCardsHandAfter = pb.getCardManager().getCards().size();
        int nbCardsInvokeAfter = pb.getCardManager().getInvokeDeck().getCards().size();
        int nbEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();
        int nbCrystalsAfter = pb.getCrystal();

        assertEquals(0, nbCardsHandAfter);
        assertEquals(0, nbCardsInvokeAfter);
        assertEquals(0, nbEnergiesAfter);
        assertEquals(nbEnergiesBefore * 4, nbCrystalsAfter);
    }

    @Given("the player draw the card with MendiantEffect, he has {int} energy in stock")
    public void thePlayerDrawTheCardWithMendiantEffectHeHasEnergyInStock(int arg0) {
        Card card = new Card("MendiantEffect", 19, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new MendiantEffect("MendiantEffect", EffectType.END_ROUND, gameEngine);
        newEffect.setCard(card);
        card.setEffect(newEffect);

        int nbCardBefore = pb.getCardManager().getCards().size();
        pb.getCardManager().addCard(card);

        assertEquals(pb.getCardManager().getCards().size(), nbCardBefore + 1);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("MendiantEffect"));
    }

    @Then("the player invoke the card, at the end of the round he gain {int} energy.")
    public void thePlayerInvokeTheCardAtTheEndOfTheRoundHeGainEnergy(int arg0) {
        gameEngine.setDescription(new StringBuilder());
        int nbEnergiesBefore = pb.getEnergyManager().getAmountofEnergies();
        assertEquals(0, nbEnergiesBefore);
        int nbCardBefore = pb.getCardManager().getCards().size();
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());
        pb.getCardManager().invoke(player.chooseCardToInvokeForFree(), true);

        int nbCardAfter = pb.getCardManager().getCards().size();
        Util.checkPermanentEffect(pb, gameEngine, EffectType.END_ROUND);
        int nbEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();

        // une énergie bonus
        assertEquals(1, nbEnergiesAfter);
        pb.getEnergyManager().addEnergy(EnergyType.FIRE, 2);
        Util.checkPermanentEffect(pb, gameEngine, EffectType.END_ROUND);
        nbEnergiesAfter = pb.getEnergyManager().getAmountofEnergies();

        // pas d'energie bonus
        assertEquals(3, nbEnergiesAfter);
    }
}
