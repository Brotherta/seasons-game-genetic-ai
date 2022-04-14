package com.game.playerassets.ia.genetic;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.ia.genetic.subject.SubjectComparator;

import org.json.simple.JSONObject;

public class Genetic {

    private final int nbGen;
    private final int popSize;
    private final double mutantsRate;
    private final double childrenRate;
    private final double winnersRate;
    private final double mutationRate;
    private final int nbGames;

    private final JSONObject tournamentStatsManager;

    private Population population;
    private Integer idAcc;

    private final static String PATH_TOURNAMENTS = "./genetics_data/tournaments/";

    public Genetic(int nbGen, int popSize, int nbGames, double mutantsRate, double childrenRate, double winnersRate,
            double mutationRate) {
        this.idAcc = 0;
        this.nbGen = nbGen;
        this.popSize = popSize;
        this.mutantsRate = mutantsRate;
        this.childrenRate = childrenRate;
        this.winnersRate = winnersRate;
        this.mutationRate = mutationRate;
        this.nbGames = nbGames;
        this.population = new Population(popSize, childrenRate, mutantsRate, 10); // todo modifié !
        this.tournamentStatsManager = new JSONObject();
    }

    public void start(String nameGenome, String nameTournament) {
        start(nameGenome, nameTournament, null);
    }

    public void start(String nameGenome, String nameTournament, String dnafile) {
        int nthGen = 0;
        if (dnafile == null) {
            for (int i = 0; i < 10; i++) {

                DNA dna = new DNA();
                if (i > 0) {
                    dna.mutateGenome(0);
                }
                Subject subject = new Subject(dna, 0, idAcc);
                this.population.addSubject(subject);
                idAcc++;
            }
        } else {
            DnaManager manager = new DnaManager(dnafile);

            for (int i = 0; i < popSize; i++) {
                Subject subject = new Subject(new DNA(), 0, idAcc);
                manager.loadDNA(subject, dnafile);
                if (i > 0) {
                    subject.getDna().mutateGenome(0);
                }
                this.population.addSubject(subject);
                idAcc++;
            }

        }

        population.initOriginalPopulation();

        Tournament tournament = new Tournament(population);

        System.out.printf("Tournament %d Originals N0\n", tournament.idTournament);
        tournament.prepareOriginal();
        tournament.start(nbGames);
        population.setWinners(winnersRate);

        getInfoTournament(tournament); // first tournament

        while (nthGen < nbGen) {
            System.out.printf("\n================== GEN %d ==================\n", nthGen);

            System.out.println("Updating population...");
            population.updatePopulation(winnersRate, mutantsRate, mutationRate, childrenRate, nthGen + 1);
            tournament.initRecurrentTournament();

            System.out.printf("Tournament %d Originals VS Mutants \n", tournament.idTournament);
            tournament.prepareOriginalVsMutant();
            tournament.start(nbGames);

            System.out.printf("Tournament %d Children VS Mutants\n", tournament.idTournament);
            tournament.prepareMutantVsChild();
            tournament.start(nbGames);

            System.out.printf("Tournament %d Originals VS Children\n", tournament.idTournament);
            tournament.prepareOriginalVsChild();
            tournament.start(nbGames);

            getInfoTournament(tournament);
            population.removeBadSubjects(winnersRate, popSize);

            System.out.println("intermediate winners : ");
            for (int i = 0; i < 6; i++) {
                System.out.printf("classement: %d; Subject ID: %d; gen: %d\n", i,
                        population.getPopulation().get(i).getId(), population.getPopulation().get(i).getBirthGen());
            }
            population.exportWinners(nameGenome + "_" + nthGen + ".json");

            nthGen++;
        }

        System.out.printf("Tournament %d Final\n", tournament.idTournament);
        tournament.prepareOriginal();
        tournament.start(nbGames);
        getInfoTournament(tournament);

        population.setWinners(winnersRate);
        population.exportWinners(nameGenome + ".json");

        System.out.println("Winners : ");
        for (int i = 0; i < 6; i++) {
            System.out.printf("classement: %d; Subject ID: %d; gen: %d\n", i, population.getPopulation().get(i).getId(),
                    population.getPopulation().get(i).getBirthGen());
        }

        exportTournament(nameTournament + ".json");
    }

    private void getInfoTournament(Tournament t) {
        JSONObject tournament = new JSONObject();
        // tournament + idtournament

        String id = String.format("tournament %d", t.idTournament);

        List<Subject> subjectList = t.getTournamentPopulation();

        // sort the tournament population by score to get the rank.

        subjectList.sort(new SubjectComparator());

        int rank = 1;
        for (Subject subject : subjectList) {
            // System.out.println(subject.getId());
            JSONObject currentSubject = new JSONObject();
            String idSubject = Integer.toString(subject.getId());
            currentSubject.put("rank", rank);
            currentSubject.put("score", subject.getScore());
            currentSubject.put("nbWin", subject.getWinAmount());
            currentSubject.put("birthGen", subject.getBirthGen());
            currentSubject.put("age", subject.getNbGenLived());
            currentSubject.put("nbGame", subject.getNbGame());
            currentSubject.put("nbMatch", subject.getNbMatch());

            tournament.put(idSubject, currentSubject);
            rank++;
        }

        tournamentStatsManager.put(id, tournament);
    }

    private void exportTournament(String tournamentName) {

        try (FileWriter fw = new FileWriter(PATH_TOURNAMENTS + tournamentName)) {

            fw.write(tournamentStatsManager.toJSONString());
            fw.flush();
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

}
