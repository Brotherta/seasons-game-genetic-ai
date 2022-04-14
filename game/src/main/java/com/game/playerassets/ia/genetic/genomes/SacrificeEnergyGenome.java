package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class SacrificeEnergyGenome extends AbstractGenome {
    private final static int CONSTRAINT = 15;

    public SacrificeEnergyGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public SacrificeEnergyGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public SacrificeEnergyGenome() {
        super(CONSTRAINT);
    }
}
