package com.game.playerassets.ia;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.card.Invocation;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.engine.energy.EnergyManager;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.strategy.Combo;
import com.game.playerassets.observer.GameObserver;
import com.game.playerassets.observer.PlayerContainer;
import com.utils.Util;
import com.utils.loaders.cards.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FacadeIA {
    private final PersonalBoard personalBoard;
    private final GameEngine engine;
    private int[] energiesToChoose;
    private int nbEnergiesToChoose;
    private int[] energiesToCrystallize;
    private ArrayList<Card> cardsToChoose;
    private Card cardToUse;
    private Card freeCard;
    private boolean invokeForFree = false;
    private Combo comboToUse;
    private int timeStep;
    private Player playerToCopy;

    public FacadeIA(PersonalBoard pb, GameEngine gameEngine) {
        this.personalBoard = pb;
        this.engine = gameEngine;
        energiesToChoose = new int[EnergyType.values().length];
        Arrays.fill(energiesToChoose, -1);
        cardsToChoose = new ArrayList<>();
        energiesToCrystallize = new int[EnergyType.values().length];
        timeStep = 0 ;
    }

    /**
     * CONTAINER METHOD
     */

    public ArrayList<Card> getChoosableCardsF() {
        if (cardsToChoose.isEmpty()) {
            throw new ObjectToChooseEmptyException("Cards to choose are not initialized");
        }
        ArrayList<Card> cardToReturn = new ArrayList<>(cardsToChoose);
        cardsToChoose.clear();
        return new ArrayList<>(cardToReturn);
    }

    public void setChoosableCardsF(List<Card> cardToChoose) {
        this.cardsToChoose.clear();
        this.cardsToChoose = new ArrayList<>(cardToChoose);
    }

    public Card getCardToUseF() {
        Card cardToReturn = cardToUse;
        cardToUse = null;
        return cardToReturn;
    }

    public void setCardToUseF(Card card) {
        this.cardToUse = card;
    }

    public Card getCardToUseFreeF() {
        Card cardToReturn = freeCard;
        freeCard = null;
        return cardToReturn;
    }

    public void setCardToUseFreeF(Card card) {
        this.freeCard = card;
    }

    public int[] getChoosableEnergiesF() {
        int[] energyReturned = Arrays.copyOf(energiesToChoose, energiesToChoose.length);
        Arrays.fill(energiesToChoose, -1);
        return energyReturned;
    }

    public void setChoosableEnergiesF(int[] energiesToChoose, int nbEnergiesToChoose) {
        Arrays.fill(this.energiesToChoose, 0);
        this.energiesToChoose = Arrays.copyOf(energiesToChoose, energiesToChoose.length);
        this.nbEnergiesToChoose = nbEnergiesToChoose;
    }

    public int getNbChoosableEnergiesF() {
        int copy = nbEnergiesToChoose;
        nbEnergiesToChoose = 0;
        return copy;
    }

    public void setEnergiesToCrystallizeF(int[] energies) {
        Arrays.fill(this.energiesToCrystallize, 0);
        this.energiesToCrystallize = Arrays.copyOf(energies, energies.length);
    }

    public int[] getEnergiesToCrystallizeF() {
        int[] energyReturned = Arrays.copyOf(energiesToCrystallize, energiesToCrystallize.length);
        Arrays.fill(energiesToCrystallize, 0);
        return energyReturned;
    }

    public void setTimeStepF(int stepmax) {
        timeStep = stepmax;
    }

    public int getTimeStepF() {
        int copy = timeStep;
        timeStep = 0;
        return copy;
    }

    public Player getPlayerToCopyF() {
        Player copy = playerToCopy;
        playerToCopy = null;
        return copy;
    }

    public void setPlayerToCopyF(Player playerToCopy) {
        this.playerToCopy = playerToCopy;
    }

    public boolean hasConflictsF(int id) {
        return personalBoard.getPlayerTurnManager().getConflictTable().hasConflicts(id);
    }

    public void updateConflictsF(int id) {
        personalBoard.getPlayerTurnManager().getConflictTable().updateConflicts(id);
    }

    public void updateConflictsFalseF(int id) {
        personalBoard.getPlayerTurnManager().getConflictTable().updateConflictsFalse(id);
    }

    public boolean mustWaitF(int id) {
        return personalBoard.getPlayerTurnManager().getConflictTable().hasToWait(id);
    }

    public void setMustWaitF(int id) {
        personalBoard.getPlayerTurnManager().getConflictTable().updateWait(id);
    }

    public boolean isInvokeForFree() {
        boolean temp = invokeForFree;
        return temp ;
    }

    public void setInvokeForFree(boolean invokeForFree) {
        this.invokeForFree = invokeForFree;
    }


    /**
     * ##################################### END CONTAINER METHODS ####################################
     */

    /**
     * CARDS METHODS
     */


    public List<Card> getActivableCardsF() {
        ArrayList<Card> cardToActivate = new ArrayList<>();
        Invocation invokeDeck = personalBoard.getCardManager().getInvokeDeck();
        for (Card card : invokeDeck.getCards()) {
            if (card.getEffect().getIsActivationEffect()
                    && card.getEffect().canActivate(engine, getMeF())
                    && !invokeDeck.isAlreadyActivated(card)) {
                cardToActivate.add(card);
            }
        }
        return cardToActivate;
    }

    public List<Card> getInvokableCardsF() {
        List<Card> cards = new ArrayList<>();
        for (Card card : getCardsF()) {
            if (canInvokeThisCardF(card)) {
                cards.add(card);
            }
        }
        return cards;
    }

    public List<Card> getCardsF() {
        return personalBoard.getCardManager().getCards();
    }

    public List<Card> getInvokedCardsF() {
        return personalBoard.getCardManager().getInvokeDeck().getCards();
    }

    public List<Card> getAllCardF() {
        ArrayList<Card> cards = new ArrayList<>(getCardsF());
        cards.addAll(personalBoard.getCardManager().getNextYearCards(engine.getBoard().getYear() - 1));
        return cards;
    }

    public List<Card> getTemporaryHandF() {
        return personalBoard.getCardManager().getTemporaryHand();
    }

    public ArrayList<Card> MagicObjectInvokedF(){
        ArrayList<Card> magicObjects = new ArrayList<>();
        for(Card c: personalBoard.getCardManager().getInvokeDeck().getCards()){
            if(c.getType() == Type.OBJECT){
                magicObjects.add(c);
            }
        }
        return magicObjects;
    }

    public boolean canInvokeThisCardF(Card c) {
        return personalBoard.getCardManager().canInvokeThisCard(c);
    }

    public boolean hasAmuletF(){
        return personalBoard.getEnergyManager().getWaterAmulet() != null;
    }

    public Card getCardInIvocationF() {
        return personalBoard.getCardManager().getCardInInvocation();
    }

    public int getInvocationGaugeF() {
        return personalBoard.getCardManager().getInvokeDeck().getGaugeSize();
    }

    public boolean canInvokeAnyCardWithoutGaugeF() {
        for (Card card : getCardsF()) {
            if (personalBoard.getCardManager().canInvokeThisCardWithoutGauge(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ##################################### END CARDS METHODS ####################################
     */

    /**
     * ENERGIES METHODS
     */

    public int[] amuletArrayF() {
        if(hasAmuletF()){
            return Arrays.copyOf(personalBoard.getEnergyManager().getWaterAmulet().getEnergyList(),EnergyType.values().length);
        }
        else{
            return new int[EnergyType.values().length] ;
        }
    }

    public int[] getTotalCardsEnergyCostF() {
        return personalBoard.getTotalCardsEnergyCost();
    }

    public int[] chooseRandomsEnergiesF(int sum) {
        return EnergyManager.chooseRandomsEnergies(personalBoard.getEnergyManager(), sum);
    }

    public int[] getAmountOfEnergiesArrayF() {
        return Arrays.copyOf(personalBoard.getEnergyManager().getAmountOfEnergiesArray(),personalBoard.getEnergyManager().getAmountOfEnergiesArray().length);
    }

    public int[] getEnergiesRepeatedF(int energiesNb) {
        int[] energies = new int[4];
        int[] amuletEnergies = amuletArrayF();
        int[] energyInStock = getAmountOfEnergiesArrayF();

        for (int i = 0; i < EnergyType.values().length; i++) {
            if (amuletEnergies[i] + energyInStock[i] >= energiesNb) {
                energies[i] = 1;
            }
        }
        return energies;
    }

    public int getAmountOfEnergiesLeftF() {
        return personalBoard.getEnergyManager().getGauge() - personalBoard.getEnergyManager().getAmountofEnergies();
    }

    public int  getEnergyPriceF(EnergyType energy, SeasonType seasonType) {
        return personalBoard.getEnergyManager().getEnergyPrice(energy,seasonType);
    }

    public EnergyType getMostValuableEnergyTypeF(SeasonType seasonType){
        for(EnergyType eT : EnergyType.values()){
            if(getEnergyPriceF(eT,seasonType) == 3){
                return eT;
            }
        }
        return EnergyType.WATER;
    }



    /**
     * ##################################### END ENERGIES METHODS ####################################
     */

    /**
     * DICE METHODS
     */
    public DiceFace getBoardDiceFaceF(){
        return engine.getBoard().getBoardDice().getActualFace();
    }

    public DiceFace getDiceFaceF() {
        return personalBoard.getActualDice().getActualFace();
    }

    public ArrayList<Dice> getRolledDicesF() {
        return engine.getPlayersCentralManager().getDicesToChoose();
    }

    /**
     * ##################################### END DICE METHODS ####################################
     */

    /**
     * PLAYER METHODS
     */

    public Player getMeF() {
        return personalBoard.getPlayer();
    }

    /**
     * @return the hashmap assiociating players to thiers info relative to what this player can see
     */
    public HashMap<Player, PlayerContainer> getInfoOFTheOthersF() {
        return personalBoard.getGameObserver().getContainer().getDictPlayerContainer();
    }

    public ArrayList<Player> getPlayersF() {
        return Util.otherPlayers(engine.getPlayersCentralManager().getPlayerList(), personalBoard.getPlayer());
    }

    public int getScoreF() {
        return personalBoard.getScore();
    }

    public int getBonusAmountF() {
        return personalBoard.getBonusManager().getBonusAmount();
    }

    public int getCrystalF() {
        return personalBoard.getCrystal();
    }

    public ArrayList<GameplayChoice> getGameplayChoiceF() {
        return personalBoard.getPlayerTurnManager().getChoicesAvailable();
    }

    public boolean hasEnoughGaugeF() {
        return personalBoard.getCardManager().getInvokeDeck().hasEnoughGauge();
    }



    /**
     * ##################################### END PLAYER METHODS ####################################
     */

    /**
     * GAME METHODS
     */

    public boolean isPreludeF() {
        return engine.isPrelude();
    }

    public GameObserver getObserverF() {
        return personalBoard.getGameObserver();
    }

    public SeasonType getCurrentSeasonsF(){
        return engine.getBoard().getSeason();
    }

    public int getMonthF(){
        return engine.getBoard().getMonth();
    }

    public int getYearF() {
        return engine.getBoard().getYear();
    }


    /**
     * ##################################### END GAME METHODS ####################################
     */

    /**
     * COMBO METHODS
     */

    public Combo createComboF() {
        return new Combo(personalBoard.getPlayerTurnManager());
    }

    public Combo getComboToUse() {
        Combo returnCombo = comboToUse;
        comboToUse = null;
        return returnCombo;
    }

    public void setComboToUse(Combo combo) {
        comboToUse = combo;
    }

    public boolean isLastF() {
        List<Player> players = getPlayersF();
        int myCrystals = personalBoard.getCrystal();
        for (Player other : players) {
            PersonalBoard pb = engine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(other.getNumPlayer());
            if (pb.getCrystal() <= myCrystals) {
                return false;
            }
        }
        return true;
    }


    /**
     * ##################################### COMBO METHODS ####################################
     */

}
