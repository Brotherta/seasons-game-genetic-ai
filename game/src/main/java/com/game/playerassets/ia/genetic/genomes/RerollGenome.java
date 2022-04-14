package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class RerollGenome extends AbstractGenome {
    private final static int CONSTRAINT = 13;

    public RerollGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public RerollGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public RerollGenome() {
        super(CONSTRAINT);
    }
}
