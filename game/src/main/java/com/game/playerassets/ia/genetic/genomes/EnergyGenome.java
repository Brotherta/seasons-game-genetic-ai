package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class EnergyGenome extends AbstractGenome {
    private final static int CONSTRAINT = 7;

    public EnergyGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public EnergyGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public EnergyGenome() {
        super(CONSTRAINT);
    }
}
