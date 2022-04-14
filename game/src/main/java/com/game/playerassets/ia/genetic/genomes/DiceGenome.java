package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class DiceGenome extends AbstractGenome {
    private final static int CONSTRAINT = 5;

    public DiceGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public DiceGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public DiceGenome() {
        super(CONSTRAINT);
    }
}
