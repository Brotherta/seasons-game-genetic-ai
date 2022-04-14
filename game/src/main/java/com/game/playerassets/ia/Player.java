package com.game.playerassets.ia;

import com.game.playerassets.ia.strategy.Strategy;
import ia.IAType;

/**
 * A model of a play from the point of view of the game engine
 */
public abstract class Player implements TurnActions {

    /**
     * Pseudo
     */
    private final String name;
    /**
     * Facade of the Personal game board of the player
     */
    private FacadeIA facadeIA;
    /**
     * Player num
     */

    private final IAType type;
    private int numPlayer;
    private StringBuilder description;

    private Strategy diceStrategy;
    private Strategy cardStrategy;
    private Strategy preludeStrategy;
    private Strategy gameplayStrategy;
    private Strategy sacrificeCardStrategy;
    private Strategy returnInHandStrategy;
    private Strategy sacrificeEnergyStrategy;
    private Strategy invocationFreeStrategy;
    private Strategy energyStrategy;
    private Strategy copyStrategy;
    private Strategy changeTimeStrategy;
    private Strategy rerollStrategy;
    private Strategy giveCards;

    protected Player(String name, FacadeIA fpb, int num, IAType type) {
        this.name = name;
        this.facadeIA = fpb;
        this.numPlayer = num;
        this.type = type;
        this.description = new StringBuilder();
    }

    public FacadeIA getFacadeIA() {
        return facadeIA;
    }

    /**
     * @return Player's score
     */
    public int getScore() {
        return facadeIA.getScoreF();
    }

    public StringBuilder getDescription() {
        return description;
    }

    public void clearDescription() {
        this.description = new StringBuilder();
    }

    /**
     * @return Display the player score
     */
    public String displayScore() {
        return Integer.toString(getScore());
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public IAType getType() {
        return type;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public Strategy getDiceStrategy() {
        return diceStrategy;
    }

    public Strategy getCardStrategy() {
        return cardStrategy;
    }

    public Strategy getPreludeStrategy() {
        return preludeStrategy;
    }

    public Strategy getGameplayStrategy() {
        return gameplayStrategy;
    }

    public Strategy getSacrificeCardStrategy() {
        return sacrificeCardStrategy;
    }

    public Strategy getReturnInHandStrategy() { return returnInHandStrategy;}

    public Strategy getSacrificeEnergyStrategy() {
        return sacrificeEnergyStrategy;
    }

    public Strategy getInvocationFreeStrategy() {
        return invocationFreeStrategy;
    }

    public Strategy getEnergyStrategy() {
        return energyStrategy;
    }

    public Strategy getCopyStrategy() {
        return copyStrategy;
    }

    public Strategy getRerollStrategy() {
        return rerollStrategy;
    }

    public Strategy getGiveCards() {
        return giveCards;
    }

    public Strategy getChangeTimeStrategy() {return changeTimeStrategy;}

    public void setDiceStrategy(Strategy diceStrategy) {
        this.diceStrategy = diceStrategy;
    }

    public void setCardStrategy(Strategy cardStrategy) {
        this.cardStrategy = cardStrategy;
    }

    public void setPreludeStrategy(Strategy preludeStrategy) {
        this.preludeStrategy = preludeStrategy;
    }

    public void setGameplayStrategy(Strategy gameplayStrategy) {
        this.gameplayStrategy = gameplayStrategy;
    }

    public void setSacrificeCardStrategy(Strategy sacrificeCardStrategy) {this.sacrificeCardStrategy = sacrificeCardStrategy;}

    public void setReturnInHandStrategy(Strategy returnInHandStrategy) {this.returnInHandStrategy = returnInHandStrategy;}

    public void setChangeTimeStrategy(Strategy changeTimeStrategy) {
        this.changeTimeStrategy = changeTimeStrategy;
    }

    public void setSacrificeEnergyStrategy(Strategy sacrificeEnergyStrategy) {
        this.sacrificeEnergyStrategy = sacrificeEnergyStrategy;
    }

    public void setInvocationFreeStrategy(Strategy invocationFreeStrategy) {
        this.invocationFreeStrategy = invocationFreeStrategy;
    }

    public void setEnergyStrategy(Strategy energyStrategy) {
        this.energyStrategy = energyStrategy;
    }

    public void setCopyStrategy(Strategy copyStrategy) {
        this.copyStrategy = copyStrategy;
    }

    public void setGiveCards(Strategy giveCards) {
        this.giveCards = giveCards;
    }

    public void setRerollStrategy(Strategy rerollStrategy) {
        this.rerollStrategy = rerollStrategy;
    }

    public void setFacadeIA(FacadeIA facadeIA) {
        this.facadeIA = facadeIA;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }
}