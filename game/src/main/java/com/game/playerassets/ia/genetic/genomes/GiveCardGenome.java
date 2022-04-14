package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class GiveCardGenome extends AbstractGenome {
    private final static int CONSTRAINT = 10;

    public GiveCardGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public GiveCardGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public GiveCardGenome() {
        super(CONSTRAINT);
    }
}
