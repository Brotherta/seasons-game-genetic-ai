package com.game.engine.card;

import com.game.engine.Board;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.utils.Event;
import com.utils.Util;
import com.utils.stats.StatsManager;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    private final List<Card>[] distributedCard;
    private final List<Card> temporaryHand;
    private final List<Card> cards;
    private final Invocation invokeDeck;
    private final PersonalBoard personalBoard;
    private StatsManager statsManager;
    private Card cardInInvocation;
    private final static int GRISMINE = 21;
    private final static int BOOT = 7;

    public CardManager(PersonalBoard personalBoard) {
        this.distributedCard = new ArrayList[Board.getNbYear()];
        for (int i = 0; i < Board.getNbYear(); i++) {
            distributedCard[i] = new ArrayList<>();
        }
        this.temporaryHand = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.invokeDeck = new Invocation();
        this.personalBoard = personalBoard;
    }

    public Invocation getInvokeDeck() {
        return invokeDeck;
    }

    public void addCard(Card card) {
        statsManager.addDealtCards(personalBoard.getPlayer(), 1);
        cards.add(card);
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
    }

    public void addCardToTemporaryHands(Card card) {
        temporaryHand.add(card);
    }

    public void clearTemporaryHand() {
        temporaryHand.clear();
    }

    public void clearPersonalDeck() {
        cards.clear();
    }

    public void addCardToYear(Card card, int year) {
        distributedCard[year].add(card);
    }

    /***
     * Invokes is a method that is called by the IA to invoke a card from the player's hand adn spend the cost, in crystal or energy
     * to add finally the score of the card
     */
    public void invoke(Card c) {
        invoke(c, false);
    }

    public void invoke(Card card, boolean free) {
        checkSpecialCondition(card);

        //check invocation power (the stars)
        cardInInvocation = card;

        getCards().remove(card);
        int energies = 0;
        int crystalCost = 0;
        Util.checkPermanentEffect(personalBoard, personalBoard.getGameEngine(), EffectType.INVOKE);
        if (!free) {
            personalBoard.getEnergyManager().consumeEnergy(card.getEnergyCost());
            personalBoard.spendCrystal(card.getCrystalCost());
            crystalCost = card.getCrystalCost();
            for (int i = 0; i < 4; i++) {
                energies += card.getEnergyCost()[i];
            }
        }
        personalBoard.getDescription().append(String.format("%n\t-> Invoke %s for %d energies and %d crystals", card.getName(), energies, crystalCost));
        EffectTemplate effect = card.getEffect();
        if (effect.getIsSingleEffect()) {
            personalBoard.getDescription().append(String.format("%n\t-> the card %s actives a single effect : ", card.getName()));
            effect.applyEffect(personalBoard.getGameEngine(), personalBoard.getPlayer());
        }
        statsManager.addPointsByCards(personalBoard.getPlayer(), card.getPoints());
        getInvokeDeck().addCard(card);

        personalBoard.getGameEngine().notifyAllObserver(personalBoard.getGameObserver(), Event.INVOKE, card);
        statsManager.addInvokedCard(personalBoard.getPlayer(), 1);
        cardInInvocation = null;
    }

    private void checkSpecialCondition(Card card) {
        Player p = personalBoard.getPlayer();
        int step = p.getFacadeIA().getTimeStepF();
        Player playerchosen = p.getFacadeIA().getPlayerToCopyF();
        personalBoard.getPlayerTurnManager().addGameplayChoice(GameplayChoice.INVOKE);
        if (card.getNumber() == BOOT && step == 0) {
            p.getFacadeIA().setInvokeForFree(true);
            p.chooseTimeToChange();

        } else {
            p.getFacadeIA().setTimeStepF(step);
        }
        if (card.getNumber() == GRISMINE && playerchosen == null) {
            p.choosePlayerToCopy();
        } else {
            p.getFacadeIA().setPlayerToCopyF(playerchosen);
        }
        personalBoard.getPlayerTurnManager().clearGameplayChoice();

    }

    /**
     * must be called to remove a specific card from the Invoke deck do not remove the card yourself !!
     *
     * @param card the card to sacrifice
     */
    public void sacrificeCard(Card card) {
        for (Card c : getInvokeDeck().getCards()) {
            if (c.getName().compareTo(card.getName()) == 0) {
                getInvokeDeck().getCards().remove(c);
                break;
            }
        }
        card.getEffect().unapplyEffect(personalBoard.getGameEngine(), personalBoard.getPlayer());
        personalBoard.getGameEngine().getDeck().addCardToDispileCards(card);
    }

    public Card drawCard(GameEngine engine) {
        statsManager.addDealtCards(personalBoard.getPlayer(), 1);
        Card card = engine.getDeck().drawCard();
        addCard(card);
        return card;
    }

    public List<Card> drawCards(GameEngine engine, int cardAmount) {
        statsManager.addDealtCards(personalBoard.getPlayer(), cardAmount);
        ArrayList<Card> cardArrayList = new ArrayList<>();
        for (int i = 0; i < cardAmount; i++) {
            cardArrayList.add(engine.getDeck().drawCard());
        }

        return cardArrayList;
    }

    public void addNextYearDeck(int year) {
        for (int i = 0; i < distributedCard[year].size(); i++) {
            addCard((Card) distributedCard[year].get(i));
        }
        distributedCard[year].clear();
    }

    public boolean canInvokeAnyCard() {
        for (Card c : personalBoard.getCardManager().getCards()) {
            if (canInvokeThisCard(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean canInvokeThisCard(Card c) {
        return personalBoard.getCardManager().getInvokeDeck().hasEnoughGauge()
                && c.getCrystalCost() <= personalBoard.getCrystal()
                && personalBoard.getEnergyManager().hasEnoughEnergy(c.getEnergyCost());
    }

    public boolean canInvokeThisCardWithoutGauge(Card c) {
        return c.getCrystalCost() <= personalBoard.getCrystal()
                && personalBoard.getEnergyManager().hasEnoughEnergy(c.getEnergyCost());
    }

    public boolean hasCardToActivate() {
        boolean canActivate = false;
        for (Card card : invokeDeck.getCards()) {
            if (card.getEffect().getIsActivationEffect()
                    && card.getEffect().canActivate(personalBoard.getGameEngine(), personalBoard.getPlayer())
                    && !invokeDeck.isAlreadyActivated(card)) {
                canActivate = true;
                break;
            }
        }
        return canActivate;
    }

    public void reset() {
        clearPersonalDeck();
        invokeDeck.reset();
    }

    public void setStatsManager(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public List getCardsOfYearI(int i) {
        return distributedCard[i];
    }

    public List<Card> getTemporaryHand() {
        return temporaryHand;
    }

    public List<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getNextYearCards(int year) {
        if (year < 3) {
            return new ArrayList<Card>(distributedCard[year]);
        }
        return new ArrayList<>();
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public Card getCardInInvocation() {
        return cardInInvocation;
    }

}
