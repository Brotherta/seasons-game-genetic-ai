package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class ChangeTimeGenome extends AbstractGenome {
    private final static int CONSTRAINT = 3;

    public ChangeTimeGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public ChangeTimeGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public ChangeTimeGenome() {
        super(CONSTRAINT);
    }
}
