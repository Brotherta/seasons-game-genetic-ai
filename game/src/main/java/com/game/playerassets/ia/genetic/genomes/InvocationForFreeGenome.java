package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class InvocationForFreeGenome extends AbstractGenome {
    private final static int CONSTRAINT = 1;

    public InvocationForFreeGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public InvocationForFreeGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public InvocationForFreeGenome() {
        super(CONSTRAINT);
    }
}
