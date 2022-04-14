package com.game.playerassets.ia.genetic;

import java.util.List;

import com.game.playerassets.ia.genetic.subject.Subject;

/**
 * MutationVerifier
 */
public class MutationVerifier {
    
    public static void main(String[] args) {
        
        DNA dna = new DNA();
        dna.mutateAllGenome();
        Subject subject = new Subject(dna, 0, 1);

        Subject newMutant = new Subject(new DNA(subject.getDna()), 1, 2);

        newMutant.getDna().mutateGenome(0.01);

        int acc = 0;

        for (int i = 0; i < 13; i++) {
            Genome g = subject.getDna().getGenomeByType(i);
            Genome g2 = newMutant.getDna().getGenomeByType(i);

            for (int j = 0; j < 12; j++) {
                List<Integer> genInt = g.getGenInt(j);
                List<Integer> genInt2 = g2.getGenInt(j);
                
                for (int k = 0; k < genInt.size(); k++) {
                    if (genInt.get(k).compareTo(genInt2.get(k)) != 0) {
                        acc++;
                    }
                }
            }
        }

        System.out.println(acc);   

    }

}