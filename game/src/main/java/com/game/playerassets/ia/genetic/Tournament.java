package com.game.playerassets.ia.genetic;

import com.game.engine.GameLoop;
import com.game.playerassets.ia.genetic.subject.Subject;

import java.util.ArrayList;
import java.util.List;


public class Tournament {

    private Population population;
    private List<Subject> fightingPopulation;
    private List<Subject> tournamentPopulation;
    public int idTournament;


    public Tournament(Population population) {
        this.population = population;
        fightingPopulation = new ArrayList<>();
        idTournament = 0;
        tournamentPopulation = new ArrayList<>();
    }

    public void prepareOriginal() {
        this.fightingPopulation.clear();
        this.tournamentPopulation.clear();
        this.tournamentPopulation.addAll(population.getOriginal());
        this.fightingPopulation.addAll(population.getOriginal());
    }

    public void initRecurrentTournament() {
        this.tournamentPopulation.clear();
        this.tournamentPopulation.addAll(population.getOriginal());
        this.tournamentPopulation.addAll(population.getMutants());
        this.tournamentPopulation.addAll(population.getChildren());
    }

    public void prepareOriginalVsMutant() {
        this.fightingPopulation.clear();
        this.fightingPopulation.addAll(population.getOriginal());
        this.fightingPopulation.addAll(population.getMutants());
    }

    public void prepareOriginalVsChild() {
        this.fightingPopulation.clear();
        this.fightingPopulation.addAll(population.getOriginal());
        this.fightingPopulation.addAll(population.getChildren());
    }

    public void prepareMutantVsChild() {
        this.fightingPopulation.clear();
        this.fightingPopulation.addAll(population.getMutants());
        this.fightingPopulation.addAll(population.getChildren());
    }

    public void start(int nbGames) {
        int nbParticipant = fightingPopulation.size();
        int start = 1;
        int nbMatch = ((nbParticipant-1) * nbParticipant) / 2;

        int match = 1;
        for (int i = 0; i < nbParticipant-1; i++) {
            for (int j = start; j < nbParticipant; j++) {
                Subject s1 = fightingPopulation.get(i);
                Subject s2 = fightingPopulation.get(j);

                System.out.printf("match %d/%d  [%d vs %d]\r",match, nbMatch, s1.getId(), s2.getId());
                if (!population.alreadyFight(s1.getId(), s2.getId())) { // todo voir l'incidence sur plusieurs générations
                    match(s1, s2, nbGames);
                }
                match++;
            }
            start++;
        }

        // reset matchups of all participants.
        for (Subject s : fightingPopulation) {
            population.resetMatchups(s.getId());
        }

        idTournament++;
    }

    private void match(Subject s1, Subject s2, int nbGames) {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(s1);
        subjects.add(s2);
        s1.getPlayer().setNumPlayer(0);
        s2.getPlayer().setNumPlayer(1);
        population.addMatchups(s1.getId(), s2.getId());
        GameLoop gl = new GameLoop(subjects, nbGames); // allée
        gl.start();
        s1.addScore(gl.getStatsManager().getScore(s1.getPlayer()));
        s2.addScore(gl.getStatsManager().getScore(s2.getPlayer()));
        s1.addWin(gl.getStatsManager().getWinamount(s1.getPlayer()));
        s2.addWin(gl.getStatsManager().getWinamount(s2.getPlayer()));
        
        subjects.clear();subjects.add(s1);
        subjects.add(s2);
        s1.getPlayer().setNumPlayer(1);
        s2.getPlayer().setNumPlayer(0);
        gl = new GameLoop(subjects, nbGames); // retour
        gl.start();
        s1.addScore(gl.getStatsManager().getScore(s1.getPlayer()));
        s2.addScore(gl.getStatsManager().getScore(s2.getPlayer()));
        s1.addWin(gl.getStatsManager().getWinamount(s1.getPlayer()));
        s2.addWin(gl.getStatsManager().getWinamount(s2.getPlayer()));

        s1.addGame(2*nbGames);
        s1.addMatch(1);
        s2.addGame(2*nbGames);
        s2.addMatch(1);
        
    }


    public List<Subject> getTournamentPopulation() {
        return tournamentPopulation;
    }
}
