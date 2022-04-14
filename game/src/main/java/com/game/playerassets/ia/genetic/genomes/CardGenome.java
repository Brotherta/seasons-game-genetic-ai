package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class CardGenome extends AbstractGenome {
    private final static int CONSTRAINT = 1;

    public CardGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public CardGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public CardGenome() {
        super(CONSTRAINT);
    }
}
