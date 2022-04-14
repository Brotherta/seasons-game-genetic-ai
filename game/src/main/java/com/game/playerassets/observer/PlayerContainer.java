package com.game.playerassets.observer;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.playerassets.ia.Player;

import java.util.ArrayList;

public class PlayerContainer {
    private int nbCardinHand;
    private int[] energies;
    private int crystal;
    private int invocationGauge;
    private Dice playerDice;
    private ArrayList<Card> invokedCards;
    private int bonusUsed;

    public PlayerContainer() {
        nbCardinHand = 3;
        energies = new int[]{0, 0, 0, 0};
        invocationGauge = 0;
        playerDice = null;
        invokedCards = new ArrayList<>();
        bonusUsed = 0;
        crystal = 0;
    }

    public void updateInvokedCards(Player p) {
        nbCardinHand -= 1;
        //on fait une copie de la liste des cartes invoquée par le player p
        invokedCards = new ArrayList<>(p.getFacadeIA().getInvokedCardsF());
        invocationGauge = p.getFacadeIA().getInvocationGaugeF();
    }

    public void updateCrystalPoint(Player p) {
        crystal = p.getFacadeIA().getCrystalF();
    }

    public void updateBonusUsed() {
        bonusUsed += 1;
    }

    public void updateEnergies(int[] energies) {
        this.energies = energies.clone();
    }

    public Card lastCardInvoked() {
        int last = invokedCards.size() - 1;
        return invokedCards.get(last);
    }

    public void updateInvokeGauge(int i){
        invocationGauge += i;
    }

    public int getCrystal() {
        return crystal;
    }

    public int getNbCardinHand() {
        return nbCardinHand;
    }

    public void setNbCardinHand(int nbCardinHand) {
        this.nbCardinHand = nbCardinHand;
    }

    public int[] getEnergies() {
        return energies;
    }

    public void setEnergies(int[] energies) {
        this.energies = energies;
    }

    public int getInvocationGauge() {
        return invocationGauge;
    }

    public void setInvocationGauge(int invocationGauge) {
        this.invocationGauge = invocationGauge;
    }

    public Dice getPlayerDice() {
        return playerDice;
    }

    public void setPlayerDice(Dice playerDice) {
        this.playerDice = playerDice;
    }

    public ArrayList<Card> getInvokedCards() {
        return invokedCards;
    }

    public void setInvokedCards(ArrayList<Card> invokedCards) {
        this.invokedCards = invokedCards;
    }

    public int getBonusUsed() {
        return bonusUsed;
    }

    public void setBonusUsed(int bonusUsed) {
        this.bonusUsed = bonusUsed;
    }

    public void updateNbCards(int nb) {
        nbCardinHand += nb;
    }

    public boolean hasEnoughGauge(){return invocationGauge - invokedCards.size() > 0;}
}
