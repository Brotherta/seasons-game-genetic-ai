package com.game.engine;

import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.utils.Util;
import com.utils.stats.StatsManager;

import java.util.List;


/**
 * Gameloop is the core of the game. It's the conductor of the game. It will schedule the Status of the game.
 */
public class GameLoop {
    private final int nbGames;
    private GameStatus status;
    private final GameEngine engine;
    private final boolean boolRend;
    private final StatsManager statsManager;
    private final String DNAFILE;

    public GameLoop(int numberPlayer, int numberGames, boolean boolRend) {
        this.nbGames = numberGames;
        this.boolRend = boolRend;
        status = GameStatus.STARTING;
        engine = new GameEngine(numberPlayer);
        statsManager = engine.getStatsManager();
        this.DNAFILE = null;
    }

    public GameLoop(List<Subject> subjects, int numberGames) {
        this.boolRend = false;
        status = GameStatus.STARTING;
        this.nbGames = numberGames;
        engine = new GameEngine(subjects);
        statsManager = engine.getStatsManager();
        this.DNAFILE = null;
    }

    public GameLoop(int numberPlayer, int numberGames, boolean boolRend, String DNAFILE) {
        this.nbGames = numberGames;
        this.boolRend = boolRend;
        status = GameStatus.STARTING;
        this.DNAFILE = DNAFILE;
        engine = new GameEngine(numberPlayer,DNAFILE);
        statsManager = engine.getStatsManager();

    }

    public GameLoop(int numberGames, boolean boolRend, List<String> dnaList) {
        this.nbGames = numberGames;
        this.boolRend = boolRend;
        status = GameStatus.STARTING;
        this.DNAFILE = null;
        engine = new GameEngine(true,dnaList);
        statsManager = engine.getStatsManager();


    }


    /**
     * Start the loop of the game. While the game status is not quitting, the game will be run. It can restart a new
     * game. At the end of all games, it save the stats in a json file.
     */
    public void start() {
        int currentNbGame = 1;
        while (!status.equals(GameStatus.QUITTING)) {

            if (status.equals(GameStatus.RESTARTING)) {
                for (PersonalBoard pb : engine.getPlayersCentralManager().getPersonalBoardList()) {
                    pb.setScore(0);
                }

                if (currentNbGame < nbGames) {
                    reset();
                    status = GameStatus.STARTING;
                    currentNbGame += 1;
                } else {
                    status = GameStatus.QUITTING;
                }
            } else {
                status = GameStatus.RUNNING;
                loop();
                status = GameStatus.RESTARTING;
            }
        }
    }

    /**
     * Specific loop for one game.
     */
    private void loop() {
        engine.setPrelude(true);
        engine.getPlayersCardsManager().doPrelude();
        engine.setPrelude(false);
        render();

        while (status.equals(GameStatus.RUNNING)) {
            int year = engine.getBoard().getYear();
            int yearNumber = Board.getNbYear();
            if (year > yearNumber) {
                engine.getPlayersCentralManager().cleanAllCrystals(engine.getDescription());
                engine.getPlayersCentralManager().countScoreFromCards();
                engine.getPlayersCentralManager().countPenalties(engine.getDescription());
                engine.showsResults();
                status = GameStatus.QUITTING;
            } else {
                engine.doTurn(year);
            }
            render();
            engine.clearDescription();
            renderPermanentEffect(EffectType.END_ROUND);
        }
        render();
        renderPermanentEffect(EffectType.END_GAME);
        engine.showsResults();
        updateStatsEndGame();
    }

    private void renderPermanentEffect(EffectType type) {
        for (Player player : engine.getPlayersCentralManager().getPlayerList()) {
            player.clearDescription();
        }
        Util.checkPermanentEffect(engine.getPlayersCentralManager().getPersonalBoardList(), engine, type);
        for (Player player : engine.getPlayersCentralManager().getPlayerList()) {
            engine.getDescription().append(player.getDescription());
        }
        render();
        engine.clearDescription();
    }

    private void reset() {
        for (PersonalBoard pb : engine.getPlayersCentralManager().getPersonalBoardList()) {
            pb.getEnergyManager().reset();
            pb.getCardManager().reset();
            pb.reset();
            pb.getBonusManager().reset();
            pb.getGameObserver().reset();
        }
        engine.getBoard().reset();
    }

    private void updateStatsEndGame() {
        statsManager.addWin(StatsManager.findWinner(engine));
        for (PersonalBoard pb : engine.getPlayersCentralManager().getPersonalBoardList()) {
            Player player = pb.getPlayer();
            statsManager.addGame(player);
            statsManager.addPoints(player, player.getScore());
            statsManager.addInvocationGauge(player, pb.getCardManager().getInvokeDeck().getGaugeSize());
            statsManager.addFinalCardOnBoard(player, pb.getCardManager().getInvokeDeck().getCards().size());
            statsManager.addPointsMalusByCards(player, pb.getCardManager().getCards().size() * 5);
        }
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameEngine getEngine() {
        return engine;
    }

    /**
     * If the verbose arguments is activated, it will render the description of a turn.
     */
    protected void render() {
        if (boolRend) {
            System.out.println(engine.toString());
        }
    }

    public int getNbGames() {
        return nbGames;
    }

    public boolean isBoolRend() {
        return boolRend;
    }
}


