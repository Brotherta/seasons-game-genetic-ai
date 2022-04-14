package com.game.engine.gamemanager.players;

import com.game.engine.card.Card;
import com.game.engine.dice.DiceFace;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.GameplayChoice;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.strategy.ConflictTable;
import com.utils.Event;

import java.util.ArrayList;

public class PlayerTurnManager {

    /**
     * The Game Engine
     */
    private final GameEngine engine;
    /**
     * The player managed
     */
    private Player player;
    /**
     * The personal board associated with the player
     */
    private final PersonalBoard personalBoard;
    /**
     * The description for the logs
     */
    private StringBuilder description;

    private final ArrayList<GameplayChoice> choicesAvailable;

    private final ConflictTable conflictTable;

    /**
     * @param engine        The Game Engine
     * @param personalBoard The personal board of the player going to be managed
     */
    public PlayerTurnManager(GameEngine engine, PersonalBoard personalBoard) {
        this.engine = engine;
        this.personalBoard = personalBoard;
        this.choicesAvailable = new ArrayList<>();
        this.conflictTable = new ConflictTable();
    }

    public void reset() {
        choicesAvailable.clear();
    }

    /**
     * Make the player turn
     * Make a list of choices, and ask to the player his choices until there is no choices available
     * or until the player chose to stop the turn
     */
    public void playTurn() {
        player = personalBoard.getPlayer();
        description = player.getDescription();
        description.append(String.format("%n[%s] TURN:%n", personalBoard.getPlayer().getName()));
        description.append(String.format("%s", personalBoard));

        checkSpecialEffect();
        personalBoard.processDiceFaceProperty(); // Read the dice properties ( gain energies...))
        PlayerTurnActionsProcessor actionsProcessor = new PlayerTurnActionsProcessor(this);

        /* The list with the choices */
        initPlayerChoices();

        while (!choicesAvailable.isEmpty() && engine.getBoard().getYear() <= 3) {
            checkAmountEnergies();
            conflictTable.initTable();
            GameplayChoice choice = player.makeGameplayChoice();
            choicesAvailable.clear();
            switch (choice) {
                case CRYSTALLIZE -> actionsProcessor.processCrystallize();

                case INVOKE -> actionsProcessor.processInvoke();

                case DRAW_CARD_WITH_BONUS -> {
                    actionsProcessor.processDoubleDrawCard();
                    engine.notifyAllObserver(personalBoard.getGameObserver(), Event.DRAW_CARD_WITH_BONUS, 1);
                }
                case DRAW_CARD -> {
                    actionsProcessor.processDrawCard();
                    engine.notifyAllObserver(personalBoard.getGameObserver(), Event.DRAW_CARD, 1);
                }
                case CRYSTALLIZE_BONUS -> actionsProcessor.processCrystallizeBonus();

                case EXCHANGE_BONUS -> actionsProcessor.processExchangeBonus();

                case INVOKE_BONUS -> actionsProcessor.processInvocationBonus();

                case ACTIVATE -> actionsProcessor.processActivation();

                case COMBO -> actionsProcessor.processCombo();

                case STOP_TURN -> actionsProcessor.processStopTurn(choicesAvailable);
            }

            if (choice != GameplayChoice.STOP_TURN) {
                updateChoices();
            }
        }
    }

    private void checkAmountEnergies(){
        int[] energies = personalBoard.getEnergyManager().getAmountOfEnergiesArray();
        for(int i = 0; i < 4;i++){
            if (energies[i] < 0){
                personalBoard.getEnergyManager().setDebugEnergy(i);
            }
        }
    }


    private void checkSpecialEffect() {
        boolean reroll = false;
        Card maliceCard = null;

        for (Card card : getPersonalBoard().getCardManager().getInvokeDeck().getCards()) {
            if (card.getName().compareTo("Dice of Malice") == 0) {
                reroll = player.chooseIfReRollDice();
                maliceCard = card;
            }
        }

        if (reroll) {
            description.append("\n\t-> chooses to activate Dice of Malice : ");
            maliceCard.getEffect().applyEffect(getPersonalBoard().getGameEngine(), player);
            personalBoard.getCardManager().getInvokeDeck().addActivatedCard(maliceCard);
        }
    }

    /**
     * Look at the dices and initialize the choices available. Look at the bonus too.
     */
    private void initPlayerChoices() {
        choicesAvailable.clear();
        DiceFace diceFace = personalBoard.getActualDice().getActualFace(); // The actual dice of the player

        if (personalBoard.getCardManager().canInvokeAnyCard()) {
            choicesAvailable.add(GameplayChoice.INVOKE); // We can invoc every turn
        }
        if (diceFace.isSell() && personalBoard.getEnergyManager().getAmountofEnergies() > 0) {
            choicesAvailable.add(GameplayChoice.CRYSTALLIZE);
        }
        if (diceFace.isDrawCard() && engine.getDeck().dispileCardsSize() > 0) {
            choicesAvailable.add(GameplayChoice.DRAW_CARD);

            if (personalBoard.getBonusManager().getBonusAmount() > 0) {
                choicesAvailable.add(GameplayChoice.DRAW_CARD_WITH_BONUS);
            }
        }
        if (personalBoard.getCardManager().hasCardToActivate()) {
            choicesAvailable.add(GameplayChoice.ACTIVATE);
        }
        addBonusChoicesAvailable();
    }

    private void updateChoices() {
        DiceFace diceFace = personalBoard.getActualDice().getActualFace(); // The actual dice of the player
        if (personalBoard.getCardManager().canInvokeAnyCard()) {
            choicesAvailable.add(GameplayChoice.INVOKE);
        }
        if (personalBoard.getCardManager().hasCardToActivate()) {
            choicesAvailable.add(GameplayChoice.ACTIVATE);
        }
        if (diceFace.isSell() && personalBoard.getEnergyManager().getAmountofEnergies() > 2) {
            choicesAvailable.add(GameplayChoice.CRYSTALLIZE);
        }
        addBonusChoicesAvailable();
    }

    /**
     * Add as much bonus choice as possible (bonusAmount)
     */
    private void addBonusChoicesAvailable() {
        int bonusAmount = personalBoard.getBonusManager().getBonusAmount();
        if (bonusAmount > 0) {
            choicesAvailable.add(GameplayChoice.CRYSTALLIZE_BONUS);
            choicesAvailable.add(GameplayChoice.EXCHANGE_BONUS);
            choicesAvailable.add(GameplayChoice.INVOKE_BONUS);
        }
    }

    /**
     * @return The player managed
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The personal board of the managed player
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * @return The description of the player
     */
    public StringBuilder getDescription() {
        return description;
    }

    public ArrayList<GameplayChoice> getChoicesAvailable() {
        return choicesAvailable;
    }

    public void addGameplayChoice(GameplayChoice gameplayChoice) {
        choicesAvailable.add(gameplayChoice);
    }

    protected GameEngine getEngine() {
        return engine;
    }

    public ConflictTable getConflictTable() {
        return conflictTable;
    }

    public void clearGameplayChoice() {
        choicesAvailable.clear();
    }
}
