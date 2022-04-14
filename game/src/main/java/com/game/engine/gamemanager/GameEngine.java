package com.game.engine.gamemanager;

import com.game.engine.Board;
import com.game.engine.SeasonType;
import com.game.engine.card.Deck;
import com.game.engine.dice.Cup;
import com.game.engine.dice.Dice;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.players.PlayersCardsManager;
import com.game.engine.gamemanager.players.PlayersCentralManager;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.DNA;
import com.game.playerassets.ia.genetic.DnaManager;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.observer.AbstractSubject;
import com.utils.Util;
import com.utils.loaders.cards.CardsLoader;
import com.utils.loaders.dice.DicesLoader;
import com.utils.stats.StatsManager;
import ia.IAType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The game engine distributes all the data between the different objects of the game.
 * It is the principal class of the project.
 */
public class GameEngine extends AbstractSubject {

    private final long gameID;

    private Cup[] cups;
    private final Board board;
    private Deck deck;
    private StringBuilder description;
    private StatsManager statsManager;
    private final PlayersCentralManager playersCentralManager;
    private PlayersCardsManager playersCardsManager;
    private boolean isPrelude;
    private final boolean isGenetic;
    private String DNAFILE = null;

    /**
     * constructeur fait pour les test principalement
     * cela permet de s'abstraire de la gameloop pour generer les joueurs
     *
     * @param nbPlayer amount of players
     */
    public GameEngine(int nbPlayer) {
        gameID = System.currentTimeMillis();
        board = new Board();
        playersCentralManager = new PlayersCentralManager(this, nbPlayer);
        isPrelude = true;
        isGenetic = false;
        initGameEngine();
    }

    public GameEngine(List<Subject> players) {
        gameID = System.currentTimeMillis();
        board = new Board();
        playersCentralManager = new PlayersCentralManager(this, players);
        isPrelude = true;
        isGenetic = true;
        initGameEngine();
    }
    public GameEngine(int nbPlayer,String DNAFile) {
        gameID = System.currentTimeMillis();
        board = new Board();
        isGenetic = true;
        this.DNAFILE = DNAFile;
        playersCentralManager = new PlayersCentralManager(this, nbPlayer);
        isPrelude = true;

        initGameEngine();
    }

    public GameEngine(boolean load, List<String> dnaList) {
        load = !load ;
        board = new Board();
        gameID = System.currentTimeMillis();
        isGenetic = true;
        isPrelude = true;

        List<Subject> subjectList = new ArrayList<>();
        int id = 0;
        DnaManager dnaLoader = new DnaManager(dnaList.get(0));
        for(String path : dnaList){
            Subject currentSubject = new Subject(new DNA(), 0,id,dnaList.get(id).substring(24));

            dnaLoader.loadDNA(currentSubject,dnaList.get(id));
            subjectList.add(currentSubject);
            id++;
        }
        playersCentralManager = new PlayersCentralManager(this,subjectList);
        initGameEngine();




    }

    private void initGameEngine() {
        loadDices();
        loadDeck();
        statsManager = new StatsManager(getPlayersCentralManager().getPlayerList());
        playersCardsManager = new PlayersCardsManager(this);
        initStatsManager();
    }

    private void initStatsManager() {
        for (PersonalBoard pb : getPlayersCentralManager().getPersonalBoardList()) {
            pb.setStatsManager(statsManager);
        }
    }

    public void loadDices() {
        File f = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("dices.json")).getPath());
        cups = DicesLoader.getDicesLoader(f.getPath()).loadCups(playersCentralManager.getAmountOfPlayers() + 1);
    }

    public void loadDeck() {
        File f = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("cards.json")).getPath());
        this.deck = CardsLoader.getCardsLoader(f.getPath()).loadDeck(playersCentralManager.getAmountOfPlayers(), this);
    }

    /**
     * Shows the score of the last game.
     */
    public void showsResults() {
        description.append("Score:");
        for (Player p : playersCentralManager.getPlayerList()) {
            description.append(String.format("%n\t%s\t\tScore : %s", p, p.displayScore()));
        }
        description.append(String.format("%nwinner : %s", StatsManager.findWinner(this)));
    }

    public void doTurn(int year) {
        description = new StringBuilder();

        description.append(String.format("%n#############################[YEAR %s / MONTH %s / SEASON %s]#############################",
                        year, (getBoard().getMonth() + 1), getBoard().getSeason()));
        description.append("\n[Choosing Dice Phase]\n");

        Dice boardDice = playersCentralManager.askPlayersToChooseTheirDice(description);
        description.append(String.format("%nDice remaining : %s | %d",boardDice.getActualFace().facePropertyToString(),boardDice.getActualFace().getDistance()));
        getBoard().setBoardDice(boardDice);

        description.append("\n\n[STARTING ROUND]\n");
        playersCentralManager.playPlayersTurn(description);
        updateSeason(year);

        getPlayersCentralManager().updateSeasonForPersonalBoard(board.getSeason());
    }

    public void updateSeason(int year) {
        SeasonType seasonType = board.getSeason();

        if (board.timeForward(board.getBoardDiceDistance()) && year < 3) {
            playersCardsManager.updateCards(year);
        }

        if (!board.getSeason().equals(seasonType)) {


                for (PersonalBoard pb : getPlayersCentralManager().getPersonalBoardList()) {
                    if(pb.getPlayer().getType() == IAType.SUBJECT_IA){
                        Subject s = pb.getSubject();
                        s.linkDNA(getBoard().getYear(), getBoard().getSeason());
                    }

                }
            Util.checkPermanentEffect(playersCentralManager.getPersonalBoardList(), this, EffectType.SEASON);
        }
    }

    public void setStatsManager(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public void setDescription(StringBuilder description) {
        this.description = description;
    }

    public void clearDescription() {
        description = new StringBuilder();
    }

    public void setPrelude(boolean prelude) {
        isPrelude = prelude;
    }

    public boolean isPrelude() {
        return isPrelude;
    }

    public PlayersCentralManager getPlayersCentralManager() {
        return playersCentralManager;
    }

    public PlayersCardsManager getPlayersCardsManager() {
        return playersCardsManager;
    }

    public Cup[] getCups() {
        return cups;
    }

    public Board getBoard() {
        return board;
    }

    public Deck getDeck() {
        return deck;
    }

    public long getGameID() {
        return gameID;
    }

    public String getDNAFILE() {
        return DNAFILE;
    }

    public void setDNAFILE(String DNAFILE) {
        this.DNAFILE = DNAFILE;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public StringBuilder getDescription() {
        return description;
    }

    public boolean isGenetic() {
        return isGenetic;
    }

    @Override
    public String toString() {
        return description.toString();
    }
}
