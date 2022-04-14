package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.card.Deck;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.utils.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Au moment où vous invoquez Naria la prophétesse, piochez autant
 * de cartes que de joueurs (vous y compris). Choisissez-en une et placez-
 * la dans votre main. Ensuite, parmi les cartes restantes, distribuez-en
 * une de votre choix à chacun de vos adversaires.
 */
public class NariaEffect extends AbstractEffect {

    public NariaEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        Deck deck = gameEngine.getDeck();

        int to_draw = gameEngine.getPlayersCentralManager().getAmountOfPlayers();

        if (deck.getCards().size() + deck.dispileCardsSize() < to_draw) {
            description.append(String.format("there is not enough cards, %s draws one card", player.getName()));
        } else {
            List<Card> drawnCards = playerDrawsCards(gameEngine, player, pb, to_draw);
            dealCardsToOtherPlayers(gameEngine, player, drawnCards, pb);
            description.append(String.format("%s draws cards, keep 1 and give others to players", player.getName()));
        }
    }

    private List<Card> playerDrawsCards(GameEngine gameEngine, Player player, PersonalBoard pb, int to_draw) {
        ArrayList<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < to_draw; i++) {
            Card card = gameEngine.getDeck().drawCard();
            drawnCards.add(card);
        }
        player.getFacadeIA().setChoosableCardsF(drawnCards);
        Card chosenCard = player.chooseBetweenCards();
        updateDrawnCards(player, drawnCards, chosenCard);
        pb.getCardManager().addCard(chosenCard);
        gameEngine.notifyAllObserver(pb.getGameObserver(), Event.DRAW_CARD, 1);

        return drawnCards;
    }

    private void dealCardsToOtherPlayers(GameEngine gameEngine, Player player, List<Card> drawnCards, PersonalBoard pb) {
        Map<Player, Card> playerCardMap = player.choosePlayerToGiveACard();
        ArrayList<Player> players = player.getFacadeIA().getPlayersF();
        for (Player receivingPlayer : players) {
            Card card = playerCardMap.get(receivingPlayer);
            PersonalBoard personalBoard = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(receivingPlayer.getNumPlayer());
            personalBoard.getCardManager().addCard(card);
            gameEngine.notifyAllObserver(pb.getGameObserver(), Event.DRAW_CARD, 1);
            updateDrawnCards(player, drawnCards, card);
        }
    }

    private void updateDrawnCards(Player player, List<Card> drawnCards, Card cardToRemove) {
        drawnCards.remove(cardToRemove);
        player.getFacadeIA().setChoosableCardsF(drawnCards);
    }
}