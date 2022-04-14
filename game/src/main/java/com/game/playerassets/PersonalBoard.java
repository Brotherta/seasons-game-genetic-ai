package com.game.playerassets;

import com.game.engine.SeasonType;
import com.game.engine.bonus.BonusManager;
import com.game.engine.card.Card;
import com.game.engine.card.CardManager;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.engine.gamemanager.players.PlayerTurnManager;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.observer.GameObserver;
import com.utils.Event;
import com.utils.Util;
import com.utils.stats.StatsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalBoard {
    private int score;
    private int crystal;
    private final EnergyManager energyManager;
    private final CardManager cardManager;
    private final BonusManager bonusManager;
    private Dice actualDice;
    private SeasonType currentSeason;
    private Player player;
    private final GameEngine gameEngine;
    private StatsManager statsManager;
    private GameObserver gameObserver;
    private final PlayerTurnManager ptm;
    private Subject subject;

    public PersonalBoard(GameEngine gameEngine) {
        this.currentSeason = SeasonType.WINTER;
        this.energyManager = new EnergyManager(this);
        this.cardManager = new CardManager(this);
        this.bonusManager = new BonusManager(BonusManager.BONUS_BASE_AMOUNT_FROM_RULES, this);
        this.gameEngine = gameEngine;
        this.ptm = new PlayerTurnManager(gameEngine, this);
    }

    public void addScore(int val) {
        this.score = this.score + val;
    }

    public void addCrystal(int crystal) {
        statsManager.addCrystalsGained(player, crystal);
        this.crystal = this.crystal + crystal;
    }

    public void loseCrystal(int crystal) {
        if (this.crystal - crystal < 0) {
            this.crystal = 0;
        } else {
            this.crystal -= crystal;
        }
    }

    /***
     * crystallize is consuming @param nbEnergy in the player energy manager to add the double of this amount in crystal
     */
    public void crystallize(EnergyType type, int nbEnergy) {
        energyManager.consumeEnergy(type, nbEnergy);
        int gainedCrystal = nbEnergy * energyManager.getEnergyPrice(type, currentSeason);
        addCrystal(gainedCrystal);
        getDescription().append(String.format("%n\t-> crystallizes %d %s energies into %d crystals", nbEnergy, type.name(), gainedCrystal));
        gameEngine.notifyAllObserver(gameObserver, Event.CRYSTALLIZE, null);
        Util.checkPermanentEffect(this, gameEngine, EffectType.CRYSTAL);
    }

    public void crystallize(int[] energies) {
        for (EnergyType type : EnergyType.values()) {
            if (energies[type.ordinal()] > 0) {
                crystallize(type, energies[type.ordinal()]);
            }
        }
    }

    /***
     * reduce amount of crystal from the players crystals
     */
    public boolean spendCrystal(int amount) {
        if (amount > crystal) {
            return false;
        } else {
            crystal = crystal - amount;
            return true;
        }
    }

    public void drawCardFromDice() {
        Card c = getCardManager().drawCard(getGameEngine());
        getDescription().append(String.format("%n\t-> draws %s from the dice effect", c.getName()));
    }

    public List<Card> drawCardFromDeck(int amount) {
        ArrayList<Card> c = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            c.add(gameEngine.getDeck().drawCard());
        }
        return c;
    }

    public void reset() {
        crystal = 0;
        score = 0;
        ptm.reset();
    }

    public void processDiceFaceProperty() {
        DiceFace diceFace = getActualDice().getActualFace();
        if (diceFace.isInvocation()) {
            processInvocationDice();
        }
        processCrystalDice();
        if (Arrays.stream(getActualDice().getActualFace().getEnergiesAmount()).sum() > 0) {
            processEnergyDice();
        }
    }

    private void processCrystalDice() {
        int crystals = getActualDice().getActualFace().getCrystal();
        if (crystals > 0) {
            addCrystal(crystals);
            getDescription().append(String.format("%n\t-> gains (%d) crystals from the dice ", crystals));
        }
    }

    private void processEnergyDice() {
        int[] diceEnergiesAmount = getActualDice().getActualFace().getEnergiesAmount();
        int[] energiesChosen;
        if (Arrays.stream(diceEnergiesAmount).sum() + energyManager.getAmountofEnergies() > energyManager.getGauge()) {
            player.getFacadeIA().setChoosableEnergiesF(Arrays.copyOf(diceEnergiesAmount, diceEnergiesAmount.length),
                    Math.min(Arrays.stream(diceEnergiesAmount).sum(), player.getFacadeIA().getAmountOfEnergiesLeftF()));
            energiesChosen = player.chooseEnergies();
        } else {
            energiesChosen = diceEnergiesAmount;
        }

        getEnergyManager().addEnergy(energiesChosen);
        boolean gained = false;
        getDescription().append("\n\t-> gains");
        for (int i = 0; i < diceEnergiesAmount.length; i++) {
            if (diceEnergiesAmount[i] > 0) {
                getDescription().append(String.format(" (%d) [%s] Energy(s)", diceEnergiesAmount[i], EnergyType.values()[i]));
                gained = true;
            }
        }
        if (gained) {
            getDescription().append(" from the dice ");
        }
    }

    private void processInvocationDice() {
        getCardManager().getInvokeDeck().upInvocationGauge(1);
        getDescription().append("\n\t-> up his invocation capacity by 1 from the dice");
    }




    /**
     * Setters
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setActualDice(Dice actualDice) {
        this.actualDice = actualDice;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameObserver(GameObserver gameObserver) {
        this.gameObserver = gameObserver;
    }

    public void setStatsManager(StatsManager statsManager) {
        this.statsManager = statsManager;
        this.cardManager.setStatsManager(statsManager);
    }

    public void setSeason(SeasonType seasonType) {
        currentSeason = seasonType;
    }


    /**
     * Getters
     */
    public CardManager getCardManager() {
        return cardManager;
    }

    public Dice getActualDice() {
        return actualDice;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public EnergyManager getEnergyManager() {
        return energyManager;
    }

    public BonusManager getBonusManager() {
        return bonusManager;
    }

    public int getCrystal() {
        return crystal;
    }

    public SeasonType getCurrentSeason() {
        return currentSeason;
    }

    public int[] getTotalCardsEnergyCost() {
        int[] arr = new int[4];
        for (Card c : this.getCardManager().getCards()) {
            for (int i = 0; i < c.getEnergyCost().length; i++) {
                arr[i] += c.getEnergyCost()[i];
            }
        }
        return arr;
    }

    public StringBuilder getDescription() {
        return player.getDescription();
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public GameObserver getGameObserver() {
        return gameObserver;
    }

    public PlayerTurnManager getPlayerTurnManager() {
        return ptm;
    }

    public Subject getSubject() {
        return subject;
    }



    /**
     * Override methods
     */
    @Override
    public String toString() {
        String pString = "";
        pString += "[Dice : " + getActualDice().getActualFace().facePropertyToString() + "]\n";
        pString += "[Energies : " + getEnergyManager().toString() + "]\n";
        if (player.getFacadeIA().hasAmuletF()) {
            pString += "[Amulet of Water : " + EnergyManager.displayEnergiesFromAnArray(getEnergyManager().getWaterAmulet().getEnergyList()) + "]\n";
        }
        pString += "[Bonus Amount Left : " + getBonusManager().getBonusAmount() + "]\n";
        pString += "[Invocation Gauge : " + getCardManager().getInvokeDeck().getGaugeSize() + "]\n";
        pString += "[Crystals Stock : " + getCrystal() + "]\n";
        pString += "[Cards in hand : " + Util.cardListToString(cardManager.getCards()) + "]\n";
        pString += "[Cards invoked : " + Util.cardListToString(cardManager.getInvokeDeck().getCards()) + "]";
        return pString;
    }


}
