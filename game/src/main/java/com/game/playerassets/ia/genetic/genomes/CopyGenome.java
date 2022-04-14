package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class CopyGenome extends AbstractGenome {
    private final static int CONSTRAINT = 4;

    public CopyGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public CopyGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public CopyGenome() {
        super(CONSTRAINT);
    }
}
