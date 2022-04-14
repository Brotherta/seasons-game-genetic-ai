package com.game.playerassets.ia.genetic;

import com.game.engine.SeasonType;
import com.game.playerassets.ia.IAFactory;
import com.game.playerassets.ia.Player;
import com.game.playerassets.ia.genetic.genomes.*;
import com.utils.Util;
import ia.IAType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DNA {

    private GameplayGenome gameplayGenome;
    private CardGenome cardGenome;
    private ChangeTimeGenome changeTimeGenome;
    private CopyGenome copyGenome;
    private DiceGenome diceGenome;
    private EnergyGenome energyGenome;
    private GiveCardGenome giveCardGenome;
    private InvocationForFreeGenome invocationForFreeGenome;
    private PreludeGenome preludeGenome;
    private RerollGenome rerollGenome;
    private ReturnInHandGenome returnInHandGenome;
    private SacrificeCardGenome sacrificeCardGenome;
    private SacrificeEnergyGenome sacrificeEnergyGenome;

    private final static int NB_GENE = 1188; // 12 * 99 (13 type by 12 seasons by 99 strategies)

    private List<Genome> genomes;

    public DNA(DNA dna) {
        this.genomes = new ArrayList<>();
        this.gameplayGenome = new GameplayGenome(); this.gameplayGenome.clone(dna.getGameplayGenome()); genomes.add(gameplayGenome);
        this.cardGenome = new CardGenome(); this.cardGenome.clone(dna.getCardGenome()); genomes.add(cardGenome);
        this.changeTimeGenome = new ChangeTimeGenome(); this.changeTimeGenome.clone(dna.getChangeTimeGenome()); genomes.add(changeTimeGenome);
        this.copyGenome = new CopyGenome(); this.copyGenome.clone(dna.getCopyGenome()); genomes.add(copyGenome);
        this.diceGenome = new DiceGenome(); this.diceGenome.clone(dna.getDiceGenome()); genomes.add(diceGenome);
        this.energyGenome = new EnergyGenome(); this.energyGenome.clone(dna.getEnergyGenome()); genomes.add(energyGenome);
        this.giveCardGenome = new GiveCardGenome(); this.giveCardGenome.clone(dna.getGiveCardGenome()); genomes.add(giveCardGenome);
        this.invocationForFreeGenome = new InvocationForFreeGenome(); this.invocationForFreeGenome.clone(dna.getInvocationForFreeGenome()); genomes.add(invocationForFreeGenome);
        this.preludeGenome = new PreludeGenome(); this.preludeGenome.clone(dna.getPreludeGenome()); genomes.add(preludeGenome);
        this.rerollGenome = new RerollGenome(); this.rerollGenome.clone(dna.getRerollGenome()); genomes.add(rerollGenome);
        this.returnInHandGenome = new ReturnInHandGenome(); this.returnInHandGenome.clone(dna.getReturnInHandGenome()); genomes.add(returnInHandGenome);
        this.sacrificeCardGenome = new SacrificeCardGenome(); this.sacrificeCardGenome.clone(dna.getSacrificeCardGenome()); genomes.add(sacrificeCardGenome);
        this.sacrificeEnergyGenome = new SacrificeEnergyGenome(); this.sacrificeEnergyGenome.clone(dna.getSacrificeEnergyGenome()); genomes.add(sacrificeEnergyGenome);
    }

    public DNA() { // Use compose IA
        Player base = IAFactory.getInstance().generate(IAType.COMPOSE_IA,0, null, null);
        this.gameplayGenome = new GameplayGenome(base.getGameplayStrategy());
        this.cardGenome = new CardGenome(base.getCardStrategy());
        this.changeTimeGenome = new ChangeTimeGenome(base.getChangeTimeStrategy());
        this.copyGenome = new CopyGenome(base.getCopyStrategy());
        this.diceGenome = new DiceGenome(base.getDiceStrategy());
        this.energyGenome = new EnergyGenome(base.getEnergyStrategy());
        this.giveCardGenome = new GiveCardGenome(base.getGiveCards());
        this.invocationForFreeGenome = new InvocationForFreeGenome(base.getInvocationFreeStrategy());
        this.preludeGenome = new PreludeGenome(base.getPreludeStrategy());
        this.rerollGenome = new RerollGenome(base.getRerollStrategy());
        this.returnInHandGenome = new ReturnInHandGenome(base.getReturnInHandStrategy());
        this.sacrificeCardGenome = new SacrificeCardGenome(base.getSacrificeCardStrategy());
        this.sacrificeEnergyGenome = new SacrificeEnergyGenome(base.getSacrificeEnergyStrategy());
        genomes = new ArrayList<>();
        genomes.add(gameplayGenome);
        genomes.add(cardGenome);
        genomes.add(changeTimeGenome);
        genomes.add(copyGenome);
        genomes.add(diceGenome);
        genomes.add(energyGenome);
        genomes.add(giveCardGenome);
        genomes.add(invocationForFreeGenome);
        genomes.add(preludeGenome);
        genomes.add(rerollGenome);
        genomes.add(returnInHandGenome);
        genomes.add(sacrificeCardGenome);
        genomes.add(sacrificeEnergyGenome);
    }

    public void mutateAllGenome() {
        for (Genome genome : genomes) {
            genome.mutateAll();
        }
    }

    public void mutateGenome(double mutationRate) {
        
        Random random = new Random();

        int seasonNb = random.nextInt(0, 12);
        Genome gGameplay = getGenomeByType(0);
        int typeRandom = random.nextInt(1,13);
        Genome gRandom = getGenomeByType(typeRandom);

        // Mutation of gameplay genome.
        int tmp = seasonNb % 4;
        SeasonType s = SeasonType.values()[tmp];
        int year = seasonNb / 4;
        List<Integer> genIntListGameplay = gGameplay.getGenInt(year+1, s);
        int indexGene = random.nextInt(0, genIntListGameplay.size()-1);

        gGameplay.mutateGene(indexGene, seasonNb, genIntListGameplay);

        // Mutation of a Random genome except the genome gameplay.
        
        List<Integer> genIntListRandom = gRandom.getGenInt(year+1, s);
        indexGene = random.nextInt(0, genIntListRandom.size()-1);

        gRandom.mutateGene(indexGene, seasonNb, genIntListRandom);

        
        
        // int nbGeneToMutate = (int) (mutationRate*NB_GENE);
        
        // int gameplayCount = (int) (nbGeneToMutate) * 4/5;

        // for (int i = 0; i < nbGeneToMutate; i++) {
        
        //     Random random = new Random();

        //     int seasonNb = random.nextInt(0, 12);

        //     int type;
        //     if (gameplayCount > 0) {
        //         type = 0;
        //         gameplayCount--;
        //     }
        //     else {
        //         type = random.nextInt(1, 13); //13
        //     }
        //     Genome g = getGenomeByType(type);

        //     int tmp = seasonNb % 4;
        //     SeasonType s = SeasonType.values()[tmp];
        //     int year = seasonNb / 4;
        //     List<Integer> genIntList = g.getGenInt(year+1, s);

        //     int indexGene = random.nextInt(0, genIntList.size()-1); // -1 to prevent mutate the constraint
            
        //     // todo gérer les gènes déjà muté.
        //     g.mutateGene(indexGene, seasonNb, genIntList);
        //     // g.mutateSwitch(year+1, s, mutationRate/10);
        // }

    }

    public void crossDna(DNA dnaParent2) {
        List<Integer> typeToCross = new ArrayList<>();

        do {
            int tmpType = Util.getRandomInt(0, 13);
            if (!typeToCross.contains(tmpType)) {
                typeToCross.add(tmpType);
            }
        } while(typeToCross.size() != 6);

        for (int type : typeToCross) {
            setGenomeByType(type, dnaParent2.getGenomeByType(type));
        }
    }

    public Genome getGenomeByType(int numType) {
        Genome toReturn = null;
        switch (numType) {
            case 0 -> toReturn = gameplayGenome;
            case 1 -> toReturn = cardGenome;
            case 2 -> toReturn = changeTimeGenome;
            case 3 -> toReturn = copyGenome;
            case 4 -> toReturn = diceGenome;
            case 5 -> toReturn = energyGenome;
            case 6 -> toReturn = giveCardGenome;
            case 7 -> toReturn = invocationForFreeGenome;
            case 8 -> toReturn = preludeGenome;
            case 9 -> toReturn = rerollGenome;
            case 10 -> toReturn = returnInHandGenome;
            case 11 -> toReturn = sacrificeCardGenome;
            case 12 -> toReturn = sacrificeEnergyGenome;
            default -> throw new IllegalArgumentException("Wrong type ok genome given !");
        }
        return toReturn;
    }

    private void setGenomeByType(int numType, Genome newGenome) {
        switch (numType) {
            case 0 -> {
                gameplayGenome = new GameplayGenome();
                gameplayGenome.clone(newGenome);
            }
            case 1 -> {
                cardGenome = new CardGenome();
                cardGenome.clone(newGenome);
            }
            case 2 -> {
                changeTimeGenome = new ChangeTimeGenome();
                changeTimeGenome.clone(newGenome);
            }
            case 3 -> {
                copyGenome = new CopyGenome();
                copyGenome.clone(newGenome);
            }
            case 4 -> {
                diceGenome = new DiceGenome();
                diceGenome.clone(newGenome);
            }
            case 5 -> {
                energyGenome = new EnergyGenome();
                energyGenome.clone(newGenome);
            }
            case 6 -> {
                giveCardGenome = new GiveCardGenome();
                giveCardGenome.clone(newGenome);
            }
            case 7 -> {
                invocationForFreeGenome = new InvocationForFreeGenome();
                invocationForFreeGenome.clone(newGenome);
            }
            case 8 -> {
                preludeGenome = new PreludeGenome();
                preludeGenome.clone(newGenome);
            }
            case 9 -> {
                rerollGenome = new RerollGenome();
                rerollGenome.clone(newGenome);
            }
            case 10 -> {
                returnInHandGenome = new ReturnInHandGenome();
                returnInHandGenome.clone(newGenome);
            }
            case 11 -> {
                sacrificeCardGenome = new SacrificeCardGenome();
                sacrificeCardGenome.clone(newGenome);
            }
            case 12 -> {
                sacrificeEnergyGenome = new SacrificeEnergyGenome();
                sacrificeEnergyGenome.clone(newGenome);
            }
            default -> throw new IllegalArgumentException("Wrong type ok genome given !");
        }
    }

    public GameplayGenome getGameplayGenome() {
        return gameplayGenome;
    }

    public void setGameplayGenome(GameplayGenome gameplayGenome) {
        this.gameplayGenome = gameplayGenome;
    }

    public CardGenome getCardGenome() {
        return cardGenome;
    }

    public void setCardGenome(CardGenome cardGenome) {
        this.cardGenome = cardGenome;
    }

    public ChangeTimeGenome getChangeTimeGenome() {
        return changeTimeGenome;
    }

    public void setChangeTimeGenome(ChangeTimeGenome changeTimeGenome) {
        this.changeTimeGenome = changeTimeGenome;
    }

    public CopyGenome getCopyGenome() {
        return copyGenome;
    }

    public void setCopyGenome(CopyGenome copyGenome) {
        this.copyGenome = copyGenome;
    }

    public DiceGenome getDiceGenome() {
        return diceGenome;
    }

    public void setDiceGenome(DiceGenome diceGenome) {
        this.diceGenome = diceGenome;
    }

    public EnergyGenome getEnergyGenome() {
        return energyGenome;
    }

    public void setEnergyGenome(EnergyGenome energyGenome) {
        this.energyGenome = energyGenome;
    }

    public GiveCardGenome getGiveCardGenome() {
        return giveCardGenome;
    }

    public void setGiveCardGenome(GiveCardGenome giveCardGenome) {
        this.giveCardGenome = giveCardGenome;
    }

    public InvocationForFreeGenome getInvocationForFreeGenome() {
        return invocationForFreeGenome;
    }

    public void setInvocationForFreeGenome(InvocationForFreeGenome invocationForFreeGenome) {
        this.invocationForFreeGenome = invocationForFreeGenome;
    }

    public PreludeGenome getPreludeGenome() {
        return preludeGenome;
    }

    public void setPreludeGenome(PreludeGenome preludeGenome) {
        this.preludeGenome = preludeGenome;
    }

    public RerollGenome getRerollGenome() {
        return rerollGenome;
    }

    public void setRerollGenome(RerollGenome rerollGenome) {
        this.rerollGenome = rerollGenome;
    }

    public ReturnInHandGenome getReturnInHandGenome() {
        return returnInHandGenome;
    }

    public void setReturnInHandGenome(ReturnInHandGenome returnInHandGenome) {
        this.returnInHandGenome = returnInHandGenome;
    }

    public SacrificeCardGenome getSacrificeCardGenome() {
        return sacrificeCardGenome;
    }

    public void setSacrificeCardGenome(SacrificeCardGenome sacrificeCardGenome) {
        this.sacrificeCardGenome = sacrificeCardGenome;
    }

    public SacrificeEnergyGenome getSacrificeEnergyGenome() {
        return sacrificeEnergyGenome;
    }

    public void setSacrificeEnergyGenome(SacrificeEnergyGenome sacrificeEnergyGenome) {
        this.sacrificeEnergyGenome = sacrificeEnergyGenome;
    }
}
