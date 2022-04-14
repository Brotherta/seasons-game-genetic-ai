package com.game.engine.gamemanager.players;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.card.Invocation;
import com.game.engine.dice.Cup;
import com.game.engine.dice.Dice;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.IAFactory;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.DNA;
import com.game.playerassets.ia.genetic.DnaManager;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.observer.GameObserver;
import com.utils.Util;
import ia.IAType;

import java.util.*;


/**
 * The Players Managers, will ask each turn to each player his choices, according
 * to what is possible.
 */
public class PlayersCentralManager {

    /**
     * GameEngine, parent of GameEnginePlayersManager
     */
    private final GameEngine engine;
    /**
     * HashMap which contain for each Player, his PersonalBoard
     */
    private final HashMap<Player, PersonalBoard> playMap;
    private int firstPlayerToPlay;
    private final ArrayList<Dice> dicesToChoose;
    private PlayerTurnManager currentPlayerTurn;

    /**
     * @param engine        GameEngine, parent of GameEnginePlayersManager
     * @param playersAmount Amount of player you want to generate
     */
    public PlayersCentralManager(GameEngine engine, int playersAmount) {
        this.engine = engine;
        this.playMap = new HashMap<>();
        this.dicesToChoose = new ArrayList<>();
        initPlayers(playersAmount);
        attachObservers();
    }

    public PlayersCentralManager(GameEngine engine, List<Subject> subjects) {
        this.engine = engine;
        this.playMap = new HashMap<>();
        this.dicesToChoose = new ArrayList<>();
        initSubjects(subjects);
        attachObservers();
    }

    /**
     * For now, it simply does 50% Simple IA 50% RandomIA
     *
     * @param playersAmount Amount of player you want to generate
     */
    private void initPlayers(int playersAmount) {
        IAFactory iaFactory = IAFactory.getInstance();
        int numLoaded = -1;
        Subject Loaded = null;

        if (engine.isGenetic() && engine.getDNAFILE() != null) {

            // numLoaded = Util.getR().nextInt(playersAmount);
            numLoaded = 0;


            DnaManager dnaLoader = new DnaManager(engine.getDNAFILE());
            Loaded = new Subject(new DNA(), 0, numLoaded);
            dnaLoader.loadDNA(Loaded, engine.getDNAFILE());
            for (int i = 0; i < playersAmount; i++) {

                PersonalBoard pb = new PersonalBoard(engine);
                FacadeIA fpb = new FacadeIA(pb, engine);

                if (i == numLoaded) {

                    Loaded.getPlayer().setFacadeIA(fpb);
                    pb.setPlayer(Loaded.getPlayer());
                    pb.setSubject(Loaded);
                    registerNewPlayer(Loaded.getPlayer(), pb);

                } else {
                    Player p;

                    if (i % 2 != 0) {
                        p = iaFactory.generate(IAType.COMPOSE_IA, i, pb, fpb);
                    } else {
                        p = iaFactory.generate(IAType.RANDOM_IA, i, pb, fpb);
                    }
                    pb.setPlayer(p);
                    registerNewPlayer(p, pb);
                }
            }
            initObserversMap();
            firstPlayerToPlay = 0;
        }

        else {


            for (int i = 0; i < playersAmount; i++) {
                PersonalBoard pb = new PersonalBoard(engine);
                FacadeIA fpb = new FacadeIA(pb, engine);
                Player p;
                if (i == 0) {
                    p = iaFactory.generate(IAType.COMPOSE_IA, i, pb, fpb);
                } else {
                    p = iaFactory.generate(IAType.RANDOM_IA, i, pb, fpb);
                }
                pb.setPlayer(p);
                registerNewPlayer(p, pb);
            }

            initObserversMap();
            firstPlayerToPlay = 0;
        }
    }

    public void initSubjects(List<Subject> players) {
        for (Subject subject : players) {
            PersonalBoard pb = new PersonalBoard(engine);
            FacadeIA facadeIA = new FacadeIA(pb, engine);
            Player player = subject.getPlayer();
            player.setFacadeIA(facadeIA);
            pb.setSubject(subject);
            pb.setPlayer(player);
            registerNewPlayer(player, pb);
        }
        initObserversMap();
        firstPlayerToPlay = 0;
    }


    private void initObserversMap() {
        for (Player p : playMap.keySet()) {
            PersonalBoard pb = playMap.get(p);
            GameObserver go = pb.getGameObserver();
            for (Player pBis : playMap.keySet()) {
                if (!p.equals(pBis)) {
                    go.registerPlayer(pBis);
                }
            }
        }
    }

    public void attachObservers() {
        for (Player p : playMap.keySet()) {
            engine.attach(playMap.get(p).getGameObserver());
        }
    }

    /**
     * Create a PlayerTurnManager for each player in the right order
     * and then call the playTurn() method.
     *
     * @param description GameEngine description
     */
    public void playPlayersTurn(StringBuilder description) {
        for (int i = 0; i < getAmountOfPlayers(); i++) {
            if (engine.getBoard().getYear() > 3) {
                break;
            }
            int playerIndex = (firstPlayerToPlay + i) % getAmountOfPlayers();

            PlayerTurnManager ptm = getPersonalBoardOfPlayerByID(playerIndex).getPlayerTurnManager();
            ptm.reset();
            currentPlayerTurn = ptm;

            ptm.playTurn();

            description.append(String.format("%s%n", ptm.getDescription()));
            ptm.getPlayer().clearDescription();
        }

        spinPlayerOrder(); // Adjust the order for the next turn
    }

    /**
     * Get every dices of the cup, ask each player in the right order
     * which dice they want, and then return the last dice.
     *
     * @param description the description of the engine.
     * @return The last dice for the Game Board
     */
    public Dice askPlayersToChooseTheirDice(StringBuilder description) {
        Cup cup = engine.getCups()[engine.getBoard().getSeason().ordinal()];
        dicesToChoose.clear();

        for (Dice d : cup.getDices())
            d.rollFace();

        Collections.addAll(dicesToChoose, cup.getDices());
        ArrayList<Player> playerList = getPlayerList();

        for (int i = 0; i < playerList.size(); i++) {
            int playerIndex = (firstPlayerToPlay + i) % playerList.size();

            Dice d = getPlayerByID(playerIndex).chooseDiceFace();
            getPersonalBoardOfPlayerByID(playerIndex).setActualDice(d);
            description.append(String.format("%n[%s] -> chooses dice :%s |", getPlayerByID(playerIndex).getName(), d.getActualFace().facePropertyToString()));
            dicesToChoose.remove(d);
        }
        return dicesToChoose.get(0);
    }

    /**
     * Use to convert the crystalls into score points at the end of the game.
     */
    public void cleanAllCrystals(StringBuilder description) {
        description.append("\nEnd of the game:\n");
        for (Player p : getPlayerList()) {
            PersonalBoard pb = getPersonalBoardOfPlayerByID(p.getNumPlayer());
            description.append(String.format("[%s] convert : ", p));
            int amountCrystals = pb.getCrystal();
            pb.addScore(amountCrystals);
            engine.getStatsManager().addPointsByCrystals(p, amountCrystals);
            description.append(String.format("%d crystals | to points%n", amountCrystals));
        }
    }

    /**
     * Add the score for every cards for each players
     */
    public void countScoreFromCards() {
        //process the point from the card that were invoced
        for (PersonalBoard pb : getPersonalBoardList()) {

            Invocation invoke = pb.getCardManager().getInvokeDeck();
            for (Card c : invoke.getCards()) {
                pb.addScore(c.getPoints());
            }
            //if some cards are left in players hand eh got a malus of point
            for (int i = 0; i < pb.getCardManager().getCards().size(); i++) {
                pb.addScore(-5);
            }
        }
    }

    /**
     * Count all bonuses penalties and update the scores
     */
    public void countPenalties(StringBuilder description) {
        description.append("\n");
        description.append("\tBonuses summary:");

        for (PersonalBoard pb : getPersonalBoardList()) {
            description.append("\n");
            Player p = pb.getPlayer();

            switch (pb.getBonusManager().getBonusAmount()) {
                case 1 -> {
                    pb.setScore(pb.getScore() - 12);
                    engine.getStatsManager().addPointsMalusByBonus(p, 12);
                    description.append(String.format("\t[%s] -> 1 bonus left. 12pts penalty.", p.getName()));
                }
                case 2 -> {
                    pb.setScore(pb.getScore() - 5);
                    engine.getStatsManager().addPointsMalusByBonus(p, 5);
                    description.append(String.format("\t[%s] -> 2 bonus left. 5pts penalty.", p.getName()));
                }
                case 3 -> description.append(String.format("\t[%s] -> 3 bonus left. No penalty.", p.getName()));
                default -> {
                    pb.setScore(pb.getScore() - 20);
                    engine.getStatsManager().addPointsMalusByBonus(p, 20);
                    description.append(String.format("\t[%s] -> 0 bonus left. 20pts penalty.", p.getName()));
                }
            }
        }

        description.append("\n\n");
    }

    /**
     * Update the season for each personal board. Use to set the selling price of the energy.
     *
     * @param seasonType actual season.
     */
    public void updateSeasonForPersonalBoard(SeasonType seasonType) {
        for (PersonalBoard pb : getPersonalBoardList()) {
            pb.setSeason(seasonType);
        }
    }

    /**
     * Spin the current first player to play the turn
     */
    private void spinPlayerOrder() {
        firstPlayerToPlay = (firstPlayerToPlay + 1) % getAmountOfPlayers();
    }

    /**
     * Register a duo of Player/PersonalBoard to the HashMap
     *
     * @param p  Player
     * @param pb PersonalBoard associated to the player
     */
    private void registerNewPlayer(Player p, PersonalBoard pb) {
        if (!playMap.containsKey(p)) {
            pb.setGameObserver(new GameObserver(p));
            playMap.put(p, pb);
        }

    }

    /**
     * @param i The ID of the player
     * @return The desired player, the first of the list by default.
     */
    public Player getPlayerByID(int i) {
        for (Player p : getPlayerList()) {
            if (p.getNumPlayer() == i) {
                return p;
            }
        }
        return getPlayerList().get(0);
    }

    /**
     * @return the amount of players registered to the game
     */
    public int getAmountOfPlayers() {
        return getPlayerList().size();
    }

    /**
     * @param i The player's ID
     * @return The personal board of the player
     */
    public PersonalBoard getPersonalBoardOfPlayerByID(int i) {
        return playMap.get(getPlayerByID(i));
    }

    /**
     * @return Return the list of payers from the HashMap keys
     */
    public ArrayList<Player> getPlayerList() {
        ArrayList<Player> players = new ArrayList<>(playMap.keySet());
        players.sort(Comparator.comparingInt(Player::getNumPlayer));
        return players;
    }

    /**
     * @return Return the list of personalBoard from the HashMap values.
     */
    public ArrayList<PersonalBoard> getPersonalBoardList() {
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>(playMap.values());
        personalBoards.sort(Comparator.comparingInt(o -> o.getPlayer().getNumPlayer()));
        return personalBoards;
    }

    public ArrayList<Dice> getDicesToChoose() {
        return dicesToChoose;
    }

    public void setCurrentPlayerTurn(PlayerTurnManager currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public PlayerTurnManager getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }
}
