package com.game.engine.bonus;

import com.game.engine.card.Card;
import com.game.engine.energy.EnergyManager;
import com.game.playerassets.PersonalBoard;

import java.util.Arrays;

public class BonusManager {

    private int bonusAmount;
    private final PersonalBoard personalBoard;
    public static final int BONUS_BASE_AMOUNT_FROM_RULES = 3;

    public BonusManager(int bonusAmount, PersonalBoard personalBoard) {
        this.bonusAmount = bonusAmount;
        this.personalBoard = personalBoard;
    }

    public boolean exchangeEnergy(int[] from, int[] to) {
        if (getBonusAmount() > 0) {
            if ((from.length == 4 && to.length == 4)
                    && (Arrays.stream(from).sum() == 2 && Arrays.stream(to).sum() == 2)
                    && (personalBoard.getEnergyManager().hasEnoughEnergy(from))) {
                personalBoard.getEnergyManager().consumeEnergy(from);
                personalBoard.getEnergyManager().addEnergy(to);
                decreaseBonusAmount();

                personalBoard.getDescription().append(String.format("%n\t-> used exchange bonus, from %s to %s", EnergyManager.displayEnergiesFromAnArray(from), EnergyManager.displayEnergiesFromAnArray(to)));
                return true;
            }
            personalBoard.getDescription().append(String.format("%n\t-> wanted to use exchange bonus, but the quantities are wrong (from %s to %s) !", EnergyManager.displayEnergiesFromAnArray(from), EnergyManager.displayEnergiesFromAnArray(to)));
        } else {
            personalBoard.getDescription().append("%n\t-> wanted to use exchange bonus, but no more bonuses available !");
        }
        return false;
    }

    public boolean crystallizeEnergy(int[] from) {
        if (getBonusAmount() > 0) {
            if (personalBoard.getEnergyManager().hasEnoughEnergy(from)) {
                personalBoard.getDescription().append("\n\t-> used crystallize bonus !");
                personalBoard.crystallize(from);
                decreaseBonusAmount();
                return true;
            }
        } else {
            personalBoard.getDescription().append("\n\t-> wanted to use crystallize bonus, but no more bonuses available !");
        }
        return false;
    }

    public void doubleDrawCard() {
        if (getBonusAmount() > 0) {
            var cards = personalBoard.drawCardFromDeck(2);
            personalBoard.getPlayer().getFacadeIA().setChoosableCardsF(cards);
            Card chosen = personalBoard.getPlayer().chooseBetweenCards();

            personalBoard.getCardManager().addCard(chosen);

            for (Card c : cards) {
                personalBoard.getGameEngine().getDeck().addCardToDispileCards(c);
            }

            personalBoard.getDescription().append(String.format("%n\t-> chooses to use a double draw card bonus, had the choice between [%s] and [%s] and choose [%s].", cards.get(0).getName(), cards.get(1).getName(), chosen.getName()));
            decreaseBonusAmount();
        }
    }

    public void upInvocation() {
        if (getBonusAmount() > 0) {
            personalBoard.getCardManager().getInvokeDeck().upInvocationGauge(1);
            personalBoard.getDescription().append("\n\t-> chooses to use a bonus and up the invocation gauge.");
            decreaseBonusAmount();
            return;
        }
        personalBoard.getDescription().append("\n\t->wanted to use invocation bonus, but no bonuses available !");
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    public void decreaseBonusAmount() {
        this.bonusAmount--;
    }

    public void reset() {
        this.bonusAmount = BONUS_BASE_AMOUNT_FROM_RULES;
    }
}
