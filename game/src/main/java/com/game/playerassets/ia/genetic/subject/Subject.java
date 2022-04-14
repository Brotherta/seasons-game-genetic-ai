package com.game.playerassets.ia.genetic.subject;

import com.game.engine.SeasonType;
import com.game.playerassets.ia.IA;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.DNA;

import ia.IAType;

public class Subject {
    private final int id;
    private final DNA dna ;
    private final Player player;
    private int score;
    private int winAmount;
    private final int birthGen;
    private final int nbGenLived;
    private int nbGame ;
    private int nbMatch ;

    public Subject(DNA dna, int generation, int num) {
        this.id = num;
        this.dna = dna;
        this.birthGen = generation;
        this.nbGenLived = 0;
        this.score = 0;
        this.winAmount = 0;
        this.nbGame = 0;
        this.nbMatch=0;
        this.player = new IA(IAType.SUBJECT_IA,String.format("subject %d",num), null, num);
        linkDNA(1, SeasonType.WINTER);
    }
  
    public Subject(DNA dna, int generation, int num, String name) {
        this.id = num;
        this.dna = dna;
        this.birthGen = generation;
        this.nbGenLived = 0;
        this.score = 0;
        this.player = new IA(IAType.SUBJECT_IA,name, null, num);
        linkDNA(1, SeasonType.WINTER);
    }

    public void linkDNA(int year, SeasonType s){
        player.setCardStrategy(dna.getCardGenome().getGen(year,s));
        player.setDiceStrategy(dna.getDiceGenome().getGen(year,s));
        player.setGameplayStrategy(dna.getGameplayGenome().getGen(year,s));
        player.setSacrificeEnergyStrategy(dna.getSacrificeEnergyGenome().getGen(year,s));
        player.setPreludeStrategy(dna.getPreludeGenome().getGen(year,s));
        player.setSacrificeCardStrategy(dna.getSacrificeCardGenome().getGen(year,s));
        player.setRerollStrategy(dna.getRerollGenome().getGen(year,s));
        player.setReturnInHandStrategy(dna.getReturnInHandGenome().getGen(year,s));
        player.setInvocationFreeStrategy(dna.getInvocationForFreeGenome().getGen(year,s));
        player.setEnergyStrategy(dna.getEnergyGenome().getGen(year,s));
        player.setCopyStrategy(dna.getCopyGenome().getGen(year,s));
        player.setGiveCards(dna.getGiveCardGenome().getGen(year,s));
        player.setChangeTimeStrategy(dna.getChangeTimeGenome().getGen(year,s));
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public DNA getDna() {
        return dna;
    }

    public int getBirthGen() {
        return birthGen;
    }

    public int getNbGenLived() {
        return nbGenLived;
    }

    public int getScore() {
        return score;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public void resetScore() {
        score = 0;
        winAmount = 0;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addWin(int win) {
        this.winAmount += win;
    }
    
    public void addGame(int nbgame){
        this.nbGame += nbgame;
    }
    public void addMatch(int nbMatch){
        this.nbMatch += nbMatch;
    }
    public int getNbGame(){
        return nbGame;
        }
    public int getNbMatch() {
        return nbMatch;
    }



}
