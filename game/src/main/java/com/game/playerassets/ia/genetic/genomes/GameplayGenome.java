package com.game.playerassets.ia.genetic.genomes;

import com.game.playerassets.ia.genetic.AbstractGenome;
import com.game.playerassets.ia.strategy.Strategy;

public class GameplayGenome extends AbstractGenome {
    private final static int CONSTRAINT = 451;

    public GameplayGenome(Strategy[] genome) {
        super(genome, CONSTRAINT);
    }

    public GameplayGenome(Strategy genome) {
        super(genome, CONSTRAINT);
    }

    public GameplayGenome() {
        super(CONSTRAINT);
    }
}
