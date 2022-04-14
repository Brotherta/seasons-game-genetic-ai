package com.utils.stats;

import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.IA;
import com.game.playerassets.ia.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import stats.PlayerStats;

import java.util.ArrayList;
import java.util.HashMap;

public class StatsManager {
    private HashMap<Player, PlayerStats> statsMap;

    public StatsManager(ArrayList<Player> players) {
        initStats(players);
    }

    private void initStats(ArrayList<Player> players) {
        statsMap = new HashMap<>();
        for (Player player : players) {
            PlayerStats playerStats = new PlayerStats();
            playerStats.setName(player.getName());
            if (player instanceof IA) {
                playerStats.setIaTypeOrdinal(((IA) player).getType());
            }
            statsMap.put(player, playerStats);
        }
    }

    public JSONArray sendData(GameEngine engine) {
        JSONArray data = new JSONArray();
        for (Player player : engine.getPlayersCentralManager().getPlayerList()) {
            PlayerStats playerStats = statsMap.get(player);
            data.put(new JSONObject(playerStats));
        }
        return data;
    }

    /**
     * Finds the winner, currently, if there are 2 players with the same score, the first of the list is declared winner
     *
     * @return the player who won the game.
     */
    public static Player findWinner(GameEngine engine) {
        int winner = 0;
        for (Player p : engine.getPlayersCentralManager().getPlayerList()) {
            int currentScore = p.getScore();
            if (currentScore > engine.getPlayersCentralManager().getPlayerByID(winner).getScore()) {
                winner = p.getNumPlayer();
            }
        }

        return engine.getPlayersCentralManager().getPlayerByID(winner);
    }

    /**
     * Attention cette méthode est matrixée
     *
     * @return Display the player name
     */
    public static int[] getPlayersClassement(ArrayList<Player> players) {
        int[] classements = new int[4];
        for (Player player : players) {
            classements[player.getNumPlayer()] = player.getScore();
        }
        return classements;
    }

    public static boolean isTheLast(ArrayList<Player> players, Player p) {
        int score = players.get(players.indexOf(p)).getScore();
        for (int i : getPlayersClassement(players)) {
            if (i < score) {
                return false;
            }
        }
        return true;
    }

    public void addWin(Player player) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setWinAmount(playerStats.getWinAmount() + 1);
    }

    public void addGame(Player player) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.addGame();
    }

    public void addPoints(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setPoints(playerStats.getPoints() + amount);
    }

    public void addPointsByCrystals(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setPointsByCrystals(playerStats.getPointsByCrystals() + amount);
    }

    public void addPointsByCards(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setPointsByCards(playerStats.getPointsByCards() + amount);
    }

    public void addPointsMalusByBonus(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setPointsMalusByBonus(playerStats.getPointsMalusByBonus() + amount);
    }

    public void addCrystalsGained(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setCrystalsGained(playerStats.getCrystalsGained() + amount);
    }

    public void addDealtCards(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setDealtCards(playerStats.getDealtCards() + amount);
    }

    public void addActivatedCards(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setActivatedCards(playerStats.getActivatedCards() + amount);
    }

    public void addInvocationGauge(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setInvocationGauge(playerStats.getInvocationGauge() + amount);
    }

    public void addInvokedCard(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setInvokedCards(playerStats.getInvokedCards() + amount);
    }

    public void addFinalCardOnBoard(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setFinalCardOnBoard(playerStats.getFinalCardOnBoard() + amount);
    }

    public void addPointsMalusByCards(Player player, int amount) {
        PlayerStats playerStats = statsMap.get(player);
        playerStats.setPointMalusByCards(playerStats.getPointMalusByCards() + amount);
    }

    public int getScore(Player player) {
        PlayerStats playerStats = statsMap.get(player);
        return playerStats.getPoints();
    }

    public int getWinamount(Player player) {
        PlayerStats playerStats = statsMap.get(player);
        return playerStats.getWinAmount();
    }    
}
