package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class ReturnInHandGenome extends AbstractGenome {
    private final static int CONSTRAINT = 432;

    public ReturnInHandGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public ReturnInHandGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public ReturnInHandGenome() {
        super(CONSTRAINT);
    }
}
