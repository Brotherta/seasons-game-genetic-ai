package com.game.playerassets.ia.genetic;

import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.ia.genetic.subject.SubjectComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Population {

    private List<Subject> population;
    private List<Subject> original;
    private List<Subject> winners;
    private List<Subject> mutants;
    private List<Subject> children;
    private HashMap<Integer, List<Integer>> matchups;
    private final int populationSize;
    private Integer idAcc;
    private final double childrenRate;
    private final double mutantsRate;
    private final int newSubjectCreated;


    public Population(int populationSize, double childrenRate, double mutantsRate, Integer idAcc) {
        this.population = new ArrayList<>();
        this.original = new ArrayList<>();
        this.winners = new ArrayList<>();
        this.mutants = new ArrayList<>();
        this.children = new ArrayList<>();
        this.matchups = new HashMap<>();
        this.populationSize = populationSize;
        this.childrenRate = childrenRate;
        this.mutantsRate = mutantsRate;
        this.newSubjectCreated = (int) (populationSize * childrenRate + populationSize * mutantsRate);
        this.idAcc = idAcc;
    }

    public void addMatchups(int id1, int id2) {
        matchups.computeIfAbsent(id1, k -> new ArrayList<>());
        matchups.computeIfAbsent(id2, k -> new ArrayList<>());

        matchups.get(id1).add(id2);
        matchups.get(id2).add(id1);
    }

    public boolean alreadyFight(int id1, int id2) {
        return matchups.get(id1).contains(id2);
    }

    public void resetMatchups(int id) {
        matchups.get(id).clear();
    }

    /**
     * @param winnersRate les n meilleurs joueurs
     * @return renvoie les n meilleurs joueurs par rapport à leur score
     */
    public void setWinners(double winnersRate) {
        int nthFirst = (int) (winnersRate * populationSize);

        winners.clear();

        population.sort(new SubjectComparator());
        for (int i = 0; i < nthFirst; i++) {
            winners.add(population.get(i));
        }
    }

    public void removeBadSubjects(double winnersRate, int totalNbPop) {
        original.clear();
        setWinners(winnersRate);

        for (int i = 0; i < totalNbPop; i++) {
            original.add(population.get(i));
        }

        population.clear();
        population.addAll(original);

        for (Subject s : population) {
            s.resetScore();
        }

    }

    private void makeChildren(int gen) { // todo rendre le mélange meilleur avec du random.
        // winners
        children.clear();

        int nbChildren = (int) (childrenRate * populationSize);
        int firstParent = 0;
        while (nbChildren > 0) {

            for (int secondParent = 0; secondParent < winners.size(); secondParent++) {
                if (nbChildren < 0) {
                    break;
                }
                if (secondParent == firstParent) { // pour pas faire un clone d'un winner.
                    secondParent++;
                }

                Subject parent1 = winners.get(firstParent);
                Subject parent2 = winners.get(secondParent);

                Subject newChild = new Subject(new DNA(parent1.getDna()), gen, idAcc);
                newChild.getDna().crossDna(parent2.getDna());

                idAcc++;

                nbChildren--;
                children.add(newChild);
                addSubject(newChild);
            }

            firstParent++;
        }
    }

    private void makeMutant(double mutationRate, int gen) {
        mutants.clear();
        int nbMutants = (int) (mutantsRate * populationSize);
//        System.out.println(nbMutants); //nombre de mutants
        int numSubject = 0; // We get the first of the winners
        Subject winner = winners.get(numSubject); // get the first of winners.

        int tmpNbMutants = Math.min(1, nbMutants / 2); // number of mutants that are muted from the selected winners.
        for (int i = 0; i < nbMutants; i++) {
            if (tmpNbMutants < 0) {
                numSubject  = (numSubject + 1) % winners.size();
                winner = winners.get(numSubject);
            }

            Subject newMutant = new Subject(new DNA(winner.getDna()), gen, idAcc);
            idAcc++;
            newMutant.getDna().mutateGenome(mutationRate);
            mutants.add(newMutant);
            addSubject(newMutant);

            tmpNbMutants--;
        }
    }

    public void updatePopulation(double nbWinners, double mutantsRate, double mutationRate, double nbChildren, int gen) {
        setWinners(nbWinners);
        makeMutant(mutationRate, gen);
        makeChildren(gen);
    }

    public void exportWinners(String path) {
        Subject bestSubject = winners.get(0);

        DnaManager dnaManager = new DnaManager(path);
        dnaManager.exportDNA(bestSubject.getDna());
    }

    public void addSubject(Subject subject) {
        population.add(subject);
        matchups.put(subject.getId(), new ArrayList<>());
    }

    public void initOriginalPopulation() {
        original.clear();
        original.addAll(population);
    }

    public List<Subject> getPopulation() {
        return population;
    }

    public void setPopulation(List<Subject> population) {
        this.population = population;
    }

    public List<Subject> getOriginal() {
        return original;
    }

    public void setOriginal(List<Subject> original) {
        this.original = original;
    }

    public List<Subject> getWinners() {
        return winners;
    }

    public List<Subject> getMutants() {
        return mutants;
    }

    public void setMutants(List<Subject> mutants) {
        this.mutants = mutants;
    }

    public List<Subject> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
    }

    public HashMap<Integer, List<Integer>> getMatchups() {
        return matchups;
    }

    public void setMatchups(HashMap<Integer, List<Integer>> matchups) {
        this.matchups = matchups;
    }
}
