package com.game.engine.effects;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.effects.effects.MaliceEffect;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.reroll.RandomRerollStrategy;
import com.utils.Util;
import com.utils.loaders.cards.Type;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DiceMaliceEffectStepdefs {

    PersonalBoard pb;
    Player player;
    GameEngine gameEngine;

    @Given("a game with {int} players for Malice Effect")
    public void init_game(int nbPlayers) {
        gameEngine = new GameEngine(nbPlayers);
        player = gameEngine.getPlayersCentralManager().getPlayerByID(0);
        pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(0);
        pb.getCardManager().reset();
        pb.getCardManager().getInvokeDeck().upInvocationGauge(10);

    }

    /**
     * TEST OF MALICE EFFECT
     */
    @Given("the player invoke the card with the MaliceEffect")
    public void thePlayerInvokeTheCardWithTheMaliceEffect() {
        int nbCardBefore = pb.getCardManager().getCards().size();

        Card card = new Card("Dice of Malice", 17, 0, 0, new int[]{0, 0, 0, 0}, Type.OBJECT);
        EffectTemplate newEffect = new MaliceEffect("Malice Effect", EffectType.DEFAULT, gameEngine);
        card.setEffect(newEffect);
        pb.getCardManager().addCard(card);

        int nbCardAfter = pb.getCardManager().getCards().size();
        assertTrue(nbCardAfter > nbCardBefore);
        assertTrue(card.getEffect().getName().equalsIgnoreCase("Malice Effect"));
    }

    @Mock
    Strategy stratReDrawDice = mock(RandomRerollStrategy.class);

    @Then("the player choose to activate his card to redraw the dice")
    public void thePlayerChooseToActivateHisCardToRedrawTheDice() {
        MockedStatic<Util> utilsMocked = mockStatic(Util.class);
        player.setRerollStrategy(stratReDrawDice);
        when(stratReDrawDice.canAct(player.getFacadeIA())).thenReturn(true);
        utilsMocked.when(() -> Util.getRandomInt(2))
                .thenReturn(1);
        player.getFacadeIA().setChoosableCardsF(pb.getCardManager().getCards());
        pb.getCardManager().invoke(player.chooseCardToInvokeForFree());

        DiceFace df1 = new DiceFace(0, false, false, false, 1, 0, new int[4]);
        DiceFace df2 = new DiceFace(1, false, false, false, 2, 0, new int[4]);
        Dice falseDice = new Dice(SeasonType.WINTER, new DiceFace[] {df1, df2}, 0);
        pb.setActualDice(falseDice);

        assertEquals(pb.getActualDice().getActualFace(), df1);
        int before = pb.getCrystal();

        pb.getGameEngine().getPlayersCentralManager().setCurrentPlayerTurn(pb.getPlayerTurnManager());
        pb.getPlayerTurnManager().playTurn();

        int after = pb.getCrystal();
        assertEquals(pb.getActualDice().getActualFace(), df2);
        assertEquals(after, before + 2);

        utilsMocked.close();
    }

}
