package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class PreludeGenome extends AbstractGenome {
    private final static int CONSTRAINT = 11;

    public PreludeGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public PreludeGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public PreludeGenome() {
        super(CONSTRAINT);
    }
}
