package com.game.engine;

import com.game.engine.dice.Dice;

/**
 * Classe qui contient toutes les informations du plateau de jeu.
 */
public class Board {
    private static final int NB_YEAR = 3;
    private static final int NB_MONTH = 12;
    private int year;
    private int month;
    private SeasonType seasonType;
    private Dice boardDice;

    public Board() {
        this.year = 1;
        this.month = 0;
        this.seasonType = SeasonType.WINTER;
    }

    public boolean timeForward(int diceValue) {
        boolean changeYear = false;
        int tmp = diceValue + month;

        if(tmp < 0 && year > 1){
            month = (((tmp % NB_MONTH) + NB_MONTH) % NB_MONTH);
            year --;
            changeYear = true;
        }
        else if (tmp < 0){
            month = (((tmp % NB_MONTH) + NB_MONTH) % NB_MONTH);
        }
        else{
            if (tmp >= 12) {
                year++;
                changeYear = true;

            }
            month = tmp % NB_MONTH;
        }

        seasonType = getSeasonByMonth();

        return changeYear;
    }

    public SeasonType getSeasonByMonth() {
        if (month <= 2) {
            return SeasonType.WINTER;
        } else if (month <= 5) {
            return SeasonType.SPRING;
        } else if (month <= 8) {
            return SeasonType.SUMMER;
        }
        return SeasonType.AUTUMN;
    }

    public void setBoardDice(Dice boardDice) {
        this.boardDice = boardDice;
    }

    public int getBoardDiceDistance() {
        return boardDice.getActualFace().getDistance();
    }

    public Dice getBoardDice() {
        return boardDice;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public static int getNbYear() {
        return NB_YEAR;
    }

    public SeasonType getSeason() {
        return seasonType;
    }

    public void reset() {
        this.year = 1;
        this.month = 0;
        this.seasonType = SeasonType.WINTER;
    }
}
