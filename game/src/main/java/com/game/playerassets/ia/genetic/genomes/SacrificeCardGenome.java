package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class SacrificeCardGenome extends AbstractGenome {
    private final static int CONSTRAINT = 14;

    public SacrificeCardGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public SacrificeCardGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public SacrificeCardGenome() {
        super(CONSTRAINT);
    }
}
