package com.game.engine.gamemanager.players;

import com.game.engine.Board;
import com.game.engine.card.Card;
import com.game.engine.card.Deck;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Stack;

public class PlayersCardsManager {

    /**
     * The Game Engine
     */
    private final GameEngine engine;
    /**
     * The deck of the Game Engine
     */
    private final Deck deck;
    /**
     * The players manager
     */
    private final PlayersCentralManager pcm;

    /**
     * @param engine The Game Engine
     */
    public PlayersCardsManager(GameEngine engine) {
        this.engine = engine;
        this.deck = engine.getDeck();
        this.pcm = engine.getPlayersCentralManager();
    }

    /**
     * Deals nine cards to each player.
     **/
    private ArrayList<Card>[] dealInitialCard() {
        deck.initDeck();
        ArrayList<Card>[] tmpDecks = new ArrayList[pcm.getAmountOfPlayers()];
        for (int i = 0; i < pcm.getAmountOfPlayers(); i++) {
            tmpDecks[i] = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                Card c = deck.drawCard();
                tmpDecks[i].add(c);
            }
        }
        return tmpDecks;
    }

    /**
     * Manage the prelude phase. with MonsieurCo
     */
    public void doPrelude() {
        engine.setDescription(new StringBuilder());
        StringBuilder description = engine.getDescription();
        description.append(String.format("%n[PRELUDE PHASE]%n"));

        ArrayList<Card>[] tmpDecks = dealInitialCard();
        ArrayList<Player> players = pcm.getPlayerList();

        int amountOfPlayers = pcm.getAmountOfPlayers();

        for (int turn = 9; turn > 0; turn--) {
            for (int i = 0; i < amountOfPlayers; i++) {
                ArrayList<Card> tmpCards = tmpDecks[(i + turn) % amountOfPlayers];
                players.get(i).getFacadeIA().setChoosableCardsF(tmpCards);
                Card card = players.get(i).chooseBetweenCards();

                String tmpCardsToString = Util.cardListToString(tmpCards);
                description.append(String.format("[%s] cards to choose : ", players.get(i).getName()));
                description.append(tmpCardsToString);
                description.append(String.format("%n -> choose ")).append(String.format("%s%n", card.getName()));

                pcm.getPersonalBoardOfPlayerByID(i).getCardManager().addCardToTemporaryHands(card);
                tmpCards.remove(card);
            }
        }

        for (PersonalBoard personalBoard : pcm.getPersonalBoardList()) {
            Player p = personalBoard.getPlayer();
            Stack<Card> sortedCard = p.manageCard();
            personalBoard.getCardManager().clearTemporaryHand();
            distributeCards(sortedCard, personalBoard);
        }

        updateCards(0);
        description.append(String.format("%n[END PRELUDE PHASE]%n"));
    }

    private void distributeCards(Stack<Card> sortedCard, PersonalBoard pb) {
        for (int i = Board.getNbYear() - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                Card card = sortedCard.pop();
                pb.getCardManager().addCardToYear(card, i);
            }
        }

        engine.getDescription().append(String.format("%n[%s] choose to order the cards as following :", pb.getPlayer().getName()));
        engine.getDescription().append(String.format("%n    [YEAR 1] -> %s", Util.cardListToString(pb.getCardManager().getCardsOfYearI(0))));
        engine.getDescription().append(String.format("%n    [YEAR 2] -> %s", Util.cardListToString(pb.getCardManager().getCardsOfYearI(1))));
        engine.getDescription().append(String.format("%n    [YEAR 3] -> %s", Util.cardListToString(pb.getCardManager().getCardsOfYearI(2))));
    }

    /**
     * Update the seasons of each player's card manager
     */
    public void updateCards(int year) {
        for (PersonalBoard pb : pcm.getPersonalBoardList()) {
            pb.getCardManager().addNextYearDeck(year);
        }
    }
}
