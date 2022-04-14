package stats;

import ia.IAType;

import java.util.Objects;

public class PlayerStats {

    private String name;
    private int iaTypeOrdinal;
    private int winAmount;
    private int gameAmount;
    private int points;
    private int pointsByCrystals;
    private int pointsByCards;
    private int pointsMalusByBonus;
    private int pointMalusByCards;
    private int crystalsGained;
    private int dealtCards;
    private int invokedCards;
    private int activatedCards;
    private int invocationGauge;
    private int finalCardOnBoard;

    public PlayerStats() {
        name = "";
        iaTypeOrdinal = IAType.RANDOM_IA.ordinal();
        winAmount = 0;
        gameAmount = 0;
        points = 0;
        pointsByCrystals = 0;
        pointsByCards = 0;
        pointsMalusByBonus = 0;
        crystalsGained = 0;
        dealtCards = 0;
        invokedCards = 0;
        activatedCards = 0;
        invocationGauge = 0;
        finalCardOnBoard = 0;
        pointMalusByCards = 0;
    }

    public int getIaTypeOrdinal() {
        return iaTypeOrdinal;
    }

    public void setIaTypeOrdinal(IAType type) {
        this.iaTypeOrdinal = type.ordinal();
    }

    public int getPoints() {
        return points;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public int getGameAmount() {
        return gameAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWinAmount(int winAmount) {
        this.winAmount = winAmount;
    }

    public void addGame() {
        gameAmount++;
    }

    public void setGameAmount(int gameAmount) {
        this.gameAmount = gameAmount;
    }

    public int getPointsByCrystals() {
        return pointsByCrystals;
    }

    public void setPointsByCrystals(int pointsByCrystals) {
        this.pointsByCrystals = pointsByCrystals;
    }

    public int getPointsByCards() {
        return pointsByCards;
    }

    public void setPointsByCards(int pointsByCards) {
        this.pointsByCards = pointsByCards;
    }

    public int getPointsMalusByBonus() {
        return pointsMalusByBonus;
    }

    public void setPointsMalusByBonus(int pointsMalusByBonus) {
        this.pointsMalusByBonus = pointsMalusByBonus;
    }

    public int getCrystalsGained() {
        return crystalsGained;
    }

    public void setCrystalsGained(int crystalsGained) {
        this.crystalsGained = crystalsGained;
    }

    public int getDealtCards() {
        return dealtCards;
    }

    public void setDealtCards(int dealtCards) {
        this.dealtCards = dealtCards;
    }

    public int getInvokedCards() {
        return invokedCards;
    }

    public void setInvokedCards(int invokedCards) {
        this.invokedCards = invokedCards;
    }

    public int getActivatedCards() {
        return activatedCards;
    }

    public void setActivatedCards(int activatedCards) {
        this.activatedCards = activatedCards;
    }

    public int getInvocationGauge() {
        return invocationGauge;
    }

    public void setInvocationGauge(int invocationGauge) {
        this.invocationGauge = invocationGauge;
    }

    public int getFinalCardOnBoard() {
        return finalCardOnBoard;
    }

    public void setFinalCardOnBoard(int finalCardOnBoard) {
        this.finalCardOnBoard = finalCardOnBoard;
    }

    public void setPointMalusByCards(int pointMalusByCards) {
        this.pointMalusByCards = pointMalusByCards;
    }

    public int getPointMalusByCards() {
        return pointMalusByCards;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "name='" + name + '\'' +
                ", winAmount=" + winAmount +
                ", gameAmount=" + gameAmount +
                ", points=" + points +
                ", pointsByCrystals=" + pointsByCrystals +
                ", pointsByCards=" + pointsByCards +
                ", pointsMalusByBonus=" + pointsMalusByBonus +
                ", pointsMalusByCards=" + pointMalusByCards +
                ", crystalsGained=" + crystalsGained +
                ", dealtCards=" + dealtCards +
                ", invokedCards=" + invokedCards +
                ", activatedCards=" + activatedCards +
                ", invocationGauge=" + invocationGauge +
                ", finalCardOnBoard=" + finalCardOnBoard +
                '}';
    }

    public void updateStats(PlayerStats stats) {
        if (Objects.equals(stats.getName(), getName())) {
            winAmount += stats.getWinAmount();
            gameAmount += stats.getGameAmount();
            points += stats.getPoints();
            pointsByCards += stats.getPointsByCards();
            pointsByCrystals += stats.getPointsByCrystals();
            pointsMalusByBonus += stats.getPointsMalusByBonus();
            pointMalusByCards += stats.getPointMalusByCards();
            crystalsGained += stats.getCrystalsGained();
            dealtCards += stats.getDealtCards();
            invokedCards += stats.getInvokedCards();
            activatedCards += stats.getActivatedCards();
            invocationGauge += stats.getInvocationGauge();
            finalCardOnBoard += finalCardOnBoard;
        }
    }
}
