package com.game.playerassets.ia.genetic;

import com.game.engine.SeasonType;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyFactory;
import com.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractGenome implements Genome {
    private final static int SIZE = 12;
    private final Strategy[] genome;
    private final int CONSTRAINT;
    private final Random r;

    public AbstractGenome(Strategy[] genome, int constraint) {
        this.genome = genome;
        this.r = new Random();
        this.CONSTRAINT = constraint;
    }

    public AbstractGenome(Strategy genome, int constraint) {
        this.CONSTRAINT = constraint;
        this.r = new Random();
        this.genome = new Strategy[SIZE];
        for (int i = 0; i < SIZE; i++) {
            this.genome[i] = genome.clone();
        }
    }

    public AbstractGenome(int constraint) {
        this.CONSTRAINT = constraint;
        this.r = new Random();
        this.genome = new Strategy[SIZE];
    }

    @Override
    public Strategy getGen(int year, SeasonType season) {
        return genome[year - 1 + season.ordinal()];
    }

    @Override
    public void clone(Genome genomeTo) {
        for (int i = 0; i < SIZE; i++) {
            List<Integer> intStrat = genomeTo.getGenInt(i);
            this.genome[i] = StrategyFactory.getInstance().getStrategyFromIntList(intStrat);
        }
    }

    @Override
    public List<Integer> getGenInt(int year, SeasonType season) {
        Strategy next = getGen(year, season);
        List<Integer> genomeInt = new ArrayList<>();

        do {
            genomeInt.add(next.getId());
            next = next.getNextStrategy();
        } while (next != null);
        return genomeInt;

    }

    @Override
    public List<Integer> getGenInt(int idx) {
        Strategy next = genome[idx];
        List<Integer> genomeInt = new ArrayList<>();

        do {
            genomeInt.add(next.getId());
            next = next.getNextStrategy();
        } while (next != null);
        return genomeInt;
    }

    @Override
    public int getConstraint() {
        return CONSTRAINT;
    }

    @Override
    public void mutateAll() {
        for (int i = 0; i < SIZE; i++) {
            Strategy strategy = genome[i];
            Strategy next = genome[i];
            List<Integer> genomeInt = new ArrayList<>();

            do {
                genomeInt.add(next.getId());
                next = next.getNextStrategy();
            } while (next != null);

            genomeInt.remove(Integer.valueOf(getConstraint()));
            Collections.shuffle(genomeInt);
            genomeInt.add(getConstraint());
            genome[i] = StrategyFactory.getInstance().getStrategyFromIntList(genomeInt);
        }
    }

    @Override
    public void mutateGene(int indexGene, int indexSeason, List<Integer> listGenInt) {
        int strategyToMutate = listGenInt.get(indexGene);

        int newIndex;
        do {
            newIndex = r.nextInt(0, listGenInt.size() - 1);
        } while (newIndex != indexGene);

        int strategyToMove = listGenInt.get(newIndex);

        listGenInt.set(newIndex, strategyToMutate);
        listGenInt.set(indexGene, strategyToMove);

        genome[indexSeason] = StrategyFactory.getInstance().getStrategyFromIntList(listGenInt);
    }

    @Override
    public void mutateSwitch(int year, SeasonType season, double rate) {
        List<Integer> genomeInt = getGenInt(year, season);
        genomeInt.remove(Integer.valueOf(getConstraint()));

        int len = genomeInt.size();

        int nbToMutate = (int) (len * rate);
        List<Integer> indexMutated = new ArrayList<>();

        while (nbToMutate > 0) {
            int index = Util.getRandomInt(len);
            if (indexMutated.contains(index) && index == len - 1) {
            } else {
                indexMutated.add(index);
                indexMutated.add(index + 1);
                nbToMutate--;
                Integer fst = genomeInt.get(index);
                Integer snd = genomeInt.get(index + 1);
                genomeInt.set(index + 1, fst);
                genomeInt.set(index, snd);
            }
        }
        genomeInt.add(getConstraint());

        genome[year - 1 + season.ordinal()] = StrategyFactory.getInstance().getStrategyFromIntList(genomeInt);

    }

    @Override
    public void mutateSwitchAll(double rate) {// ne peut pas etre supérieur a 1, a remplacer par la methode voulue pour
                                              // la mutation
        for (int i = 0; i < SIZE; i++) {
            List<Integer> genomeInt = getGenInt(i);
            genomeInt.remove(Integer.valueOf(getConstraint()));

            int len = genomeInt.size();

            int nbToMutate = (int) (len * rate);
            List<Integer> indexMutated = new ArrayList<>();
            while (nbToMutate > 0) {
                int index = Util.getRandomInt(len - 1);
                if (indexMutated.contains(index)) {
                } else {
                    indexMutated.add(index);
                    if (rate <= 0.5) {
                        indexMutated.add(index + 1);
                    }
                    nbToMutate--;
                    Integer fst = genomeInt.get(index);
                    Integer snd = genomeInt.get(index + 1);
                    genomeInt.set(index + 1, fst);
                    genomeInt.set(index, snd);
                }
            }
            genomeInt.add(getConstraint());
            genome[i] = StrategyFactory.getInstance().getStrategyFromIntList(genomeInt);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
