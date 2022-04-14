package com.game.playerassets.ia.genetic;

import com.game.engine.SeasonType;
import com.game.playerassets.ia.strategy.Strategy;

import java.util.List;

public interface Genome {
    Strategy getGen(int year, SeasonType season);

    List<Integer> getGenInt( int year, SeasonType season);
    List<Integer> getGenInt( int idx);

    int getConstraint();

    void mutateAll();

    void mutateSwitch(int year, SeasonType season, double rate);

    void mutateSwitchAll(double rate);

    void mutateGene(int indexGene, int type, List<Integer> listGenInt);

    void clone(Genome genomeTo);
}
