package server;

import ia.IAType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stats.PlayerStats;

import java.io.*;
import java.util.Arrays;

public class StatsJSONFileManager {

    private final String filepath;
    private JSONObject root;
    private JSONObject playersJson;

    public StatsJSONFileManager(String filepath) {
        this.filepath = filepath;
        JSONParser parser = new JSONParser();

        try {
            File file = new File(filepath);

            if (!file.exists()) {
                root = new JSONObject();
            } else {
                root = (JSONObject) parser.parse(new FileReader(filepath));
            }

            initJson();
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

    public void updatePlayerStatsToFile(PlayerStats stats) {
        PlayerStats oldStats = loadPlayerStats(IAType.values()[stats.getIaTypeOrdinal()], stats.getName());
        if (oldStats != null) {
            stats.updateStats(oldStats);
        }
        JSONObject statsJson = playerStatsToJsonObject(stats);
        refreshPercentages(stats);
        savePlayerJson(statsJson, IAType.values()[stats.getIaTypeOrdinal()], stats.getName());

    }

    private void savePlayerJson(JSONObject playerJson, IAType iaType, String playerName) {
        if (!playersJson.containsKey(iaType.name())) {
            playersJson.put(iaType.name(), new JSONObject());
        }
        JSONObject iaTypeJson = (JSONObject) playersJson.get(iaType.name());
        iaTypeJson.put(playerName, playerJson);
    }

    public PlayerStats loadPlayerStats(IAType iaType, String playerName) {
        if (!playersJson.containsKey(iaType.name())) {
            return null;
        }
        JSONObject iaTypeJson = (JSONObject) playersJson.get(iaType.name());
        if (!iaTypeJson.containsKey(playerName)) {
            return null;
        }

        JSONObject playerJson = (JSONObject) iaTypeJson.get(playerName);

        PlayerStats ps = new PlayerStats();
        ps.setName(playerName);
        ps.setIaTypeOrdinal(iaType);

        ps.setActivatedCards(((Long) playerJson.get(ACTIVATED_CARDS)).intValue());
        ps.setCrystalsGained(((Long) playerJson.get(CRYSTALS_GAINED)).intValue());
        ps.setDealtCards(((Long) playerJson.get(DEALT_CARDS)).intValue());

        ps.setPoints(((Long) playerJson.get(POINTS)).intValue());
        ps.setPointsByCards(((Long) playerJson.get(POINTS_BY_CARDS)).intValue());
        ps.setPointsByCrystals(((Long) playerJson.get(POINTS_BY_CRYSTALS)).intValue());
        ps.setPointsMalusByBonus(((Long) playerJson.get(POINTS_MALUS_BY_BONUS)).intValue());
        ps.setPointMalusByCards(((Long) playerJson.get(POINTS_MALUS_BY_CARDS)).intValue());

        ps.setInvocationGauge(((Long) playerJson.get(INVOCATION_GAUGE)).intValue());
        ps.setInvokedCards(((Long) playerJson.get(INVOKED_CARDS)).intValue());
        ps.setFinalCardOnBoard(((Long) playerJson.get(FINAL_CARD_ON_BOARD)).intValue());
        ps.setWinAmount(((Long) playerJson.get(WIN_AMOUNT)).intValue());
        ps.setGameAmount(((Long) playerJson.get(GAME_AMOUNT)).intValue());

        return ps;
    }

    public JSONObject playerStatsToJsonObject(PlayerStats stats) {
        JSONObject playerJson = new JSONObject();

        playerJson.put(ACTIVATED_CARDS, stats.getActivatedCards());
        playerJson.put(CRYSTALS_GAINED, stats.getCrystalsGained());
        playerJson.put(DEALT_CARDS, stats.getDealtCards());

        playerJson.put(POINTS, stats.getPoints());
        playerJson.put(POINTS_BY_CARDS, stats.getPointsByCards());
        playerJson.put(POINTS_BY_CRYSTALS, stats.getPointsByCrystals());
        playerJson.put(POINTS_MALUS_BY_BONUS, stats.getPointsMalusByBonus());
        playerJson.put(POINTS_MALUS_BY_CARDS, stats.getPointMalusByCards());

        playerJson.put(INVOCATION_GAUGE, stats.getInvocationGauge());
        playerJson.put(INVOKED_CARDS, stats.getInvokedCards());
        playerJson.put(FINAL_CARD_ON_BOARD, stats.getFinalCardOnBoard());
        playerJson.put(WIN_AMOUNT, stats.getWinAmount());
        playerJson.put(GAME_AMOUNT, stats.getGameAmount());

        return playerJson;
    }

    private void initJson() {
        if (root == null) {
            root = new JSONObject();
        }

        if (!root.containsKey(PLAYERS)) {
            root.put(PLAYERS, new JSONObject());
        }
        playersJson = (JSONObject) root.get(PLAYERS);
    }

    private void refreshPercentages(PlayerStats stats) {
        IAType playerIaType = IAType.values()[stats.getIaTypeOrdinal()];
        if (!root.containsKey(AVERAGE)) {
            root.put(AVERAGE, new JSONObject());
        }

        JSONObject totalsJson = (JSONObject) root.get(AVERAGE);
        if (!totalsJson.containsKey(playerIaType.name())) {
            totalsJson.put(playerIaType.name(), new JSONObject());
        }
        JSONObject iaTypeJson = (JSONObject) totalsJson.get(playerIaType.name());

        JSONObject playerJson = new JSONObject();

        playerJson.put(ACTIVATED_CARDS, (stats.getActivatedCards() * 100) / stats.getGameAmount());
        playerJson.put(CRYSTALS_GAINED, (stats.getCrystalsGained() * 100) / stats.getGameAmount());
        playerJson.put(DEALT_CARDS, (stats.getDealtCards() * 100) / stats.getGameAmount());

        playerJson.put(POINTS, (stats.getPoints() * 100) / stats.getGameAmount());
        playerJson.put(POINTS_BY_CARDS, (stats.getPointsByCards() * 100) / stats.getGameAmount());
        playerJson.put(POINTS_BY_CRYSTALS, (stats.getPointsByCrystals() * 100) / stats.getGameAmount());
        playerJson.put(POINTS_MALUS_BY_BONUS, (stats.getPointsMalusByBonus() * 100) / stats.getGameAmount());
        playerJson.put(POINTS_MALUS_BY_CARDS, (stats.getPointMalusByCards() * 100) / stats.getGameAmount());

        playerJson.put(INVOCATION_GAUGE, (stats.getInvocationGauge() * 100) / stats.getGameAmount());
        playerJson.put(INVOKED_CARDS, (stats.getInvokedCards() * 100) / stats.getGameAmount());
        playerJson.put(FINAL_CARD_ON_BOARD, (stats.getFinalCardOnBoard() * 100) / stats.getGameAmount());
        playerJson.put(WIN_RATE, (stats.getWinAmount() * 100) / stats.getGameAmount() + "%");

        iaTypeJson.put(stats.getName(), playerJson);
    }

    public void saveGlobalJson() {
        try (FileWriter fw = new FileWriter(filepath)) {

            fw.write(root.toJSONString());
            fw.flush();
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

    private static final String PLAYERS = "players";

    private static final String ACTIVATED_CARDS = "activatedCards";
    private static final String CRYSTALS_GAINED = "crystalsGained";
    private static final String DEALT_CARDS = "dealtCards";
    private static final String INVOCATION_GAUGE = "invocationGauge";
    private static final String INVOKED_CARDS = "invokedCards";
    private static final String FINAL_CARD_ON_BOARD = "finalCardOnBoard";

    private static final String POINTS = "points";
    private static final String POINTS_BY_CARDS = "pointsByCards";
    private static final String POINTS_BY_CRYSTALS = "pointsByCrystals";
    private static final String POINTS_MALUS_BY_BONUS = "pointsMalusByBonus";
    private static final String POINTS_MALUS_BY_CARDS = "pointsMalusByCards";

    private static final String WIN_AMOUNT = "winAmount";
    private static final String GAME_AMOUNT = "gameAmount";
    private static final String WIN_RATE = "winRate";

    private static final String AVERAGE = "averages";
}
