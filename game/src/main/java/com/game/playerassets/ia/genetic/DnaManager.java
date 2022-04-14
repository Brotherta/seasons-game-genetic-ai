package com.game.playerassets.ia.genetic;

import com.game.engine.SeasonType;
import com.game.playerassets.ia.genetic.genomes.*;
import com.game.playerassets.ia.genetic.subject.Subject;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.StrategyFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DnaManager {
    private final String filepath;
    private JSONObject root;
    private JSONObject dnaJSON;
    private static DnaManager instance;
    private final static int NBGEN = 12;

    private final static String GENOMES = "genomes";
    private final static String DNA = "dna";


    private final static String gameplayGenome = "gameplayGenome";
    private final static String cardGenome = "cardGenome";
    private final static String ChangeTimeGenome = "changeTimeGenome";
    private final static String CopyGenome = "copyGenome";
    private final static String DiceGenome = "diceGenome";
    private final static String EnergyGenome = "energyGenome";
    private final static String GiveCardGenome = "giveCardGenome";
    private final static String InvocationForFreeGenome = "invocationForFreeGenome";
    private final static String PreludeGenome = "preludeGenome";
    private final static String RerollGenome = "rerollGenome";
    private final static String ReturnInHandGenome = "returnInHandGenome";
    private final static String SacrificeCardGenome = "sacrificeCardGenome";
    private final static String SacrificeEnergyGenome = "sacrificeEnergyGenome";
    private final static String PATH_GENOMES = "./genetics_data/genomes/";


    public DnaManager(String filepath) {
        this.filepath = filepath;
        JSONParser parser = new JSONParser();
        try {
            File file = new File(filepath);

            if (!file.exists()) {
                root = new JSONObject();
            } else {
                root = (JSONObject) parser.parse(new FileReader(filepath));
            }

            initJson();
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));

        }

    }

    public void exportDNA(DNA dna) {
        dnaJSON.put(GENOMES, new JSONObject());
        JSONObject gen = (JSONObject) dnaJSON.get(GENOMES);

//        gameplayGenome
        JSONObject game = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            game.put(i, dna.getGameplayGenome().getGenInt(i));
        }
        gen.put(gameplayGenome, game);

//        cardGenome
        JSONObject card = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            card.put(i, dna.getCardGenome().getGenInt(i));
        }
        gen.put(cardGenome, card);

//        ChangeTimeGenome
        JSONObject ChangeTime = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            ChangeTime.put(i, dna.getChangeTimeGenome().getGenInt(i));
        }
        gen.put(ChangeTimeGenome, ChangeTime);

//        CopyGenome

        JSONObject Copy = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Copy.put(i, dna.getCopyGenome().getGenInt(i));
        }
        gen.put(CopyGenome, Copy);

//        DiceGenome
        JSONObject Dice = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Dice.put(i, dna.getDiceGenome().getGenInt(i));
        }
        gen.put(DiceGenome, Dice);

//        EnergyGenome
        JSONObject Energy = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Energy.put(i, dna.getEnergyGenome().getGenInt(i));
        }
        gen.put(EnergyGenome, Energy);

//        GiveCardGenome
        JSONObject GiveCard = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            GiveCard.put(i, dna.getGiveCardGenome().getGenInt(i));
        }
        gen.put(GiveCardGenome, GiveCard);

//       InvocationForFreeGenome
        JSONObject InvocationFF = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            InvocationFF.put(i, dna.getInvocationForFreeGenome().getGenInt(i));
        }
        gen.put(InvocationForFreeGenome, InvocationFF);

//        PreludeGenome
        JSONObject Prelude = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Prelude.put(i, dna.getPreludeGenome().getGenInt(i));
        }
        gen.put(PreludeGenome, Prelude);

//        RerollGenome
        JSONObject Reroll = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Reroll.put(i, dna.getRerollGenome().getGenInt(i));
        }
        gen.put(RerollGenome, Reroll);

//        ReturnInHandGenome
        JSONObject Return = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            Return.put(i, dna.getReturnInHandGenome().getGenInt(i));
        }
        gen.put(ReturnInHandGenome, Return);

//       SacrificeCardGenome
        JSONObject SacrificeCard = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            SacrificeCard.put(i, dna.getSacrificeCardGenome().getGenInt(i));
        }
        gen.put(SacrificeCardGenome, SacrificeCard);

//        SacrificeEnergyGenome
        JSONObject SacrificeEnergy = new JSONObject();
        for (int i = 0; i < NBGEN; i++) {
            SacrificeEnergy.put(i, dna.getSacrificeEnergyGenome().getGenInt(i));
        }
        gen.put(SacrificeEnergyGenome, SacrificeEnergy);

        dnaJSON.put(GENOMES, gen);

        saveGlobalJson();
    }

    public void loadDNA(Subject s, String filePath) {
        DNA subjectDna = s.getDna();
        JSONParser parser = new JSONParser();
        try {
            root = (JSONObject) parser.parse(new FileReader(filePath));
            dnaJSON = (JSONObject) root.get(DNA);
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }

        JSONObject genomeRoot = (JSONObject) dnaJSON.get(GENOMES);

        for (Object genomeType : genomeRoot.keySet()) {
            Strategy[] strategies = new Strategy[12];
            int i = 0;
            JSONObject currentGenome = (JSONObject) genomeRoot.get(genomeType);
            for (Object generation : currentGenome.values()) {
                List<Integer> strategiesNumber = new ArrayList<>();
                for (Long elt : (List<Long>) generation) {
                    strategiesNumber.add(elt.intValue());
                }
                Strategy newStrat = StrategyFactory.getInstance().getStrategyFromIntList(strategiesNumber);
                strategies[i] = newStrat;
                i++;
            }
            switch ((String) genomeType) {
                case SacrificeEnergyGenome -> subjectDna.setSacrificeEnergyGenome(new SacrificeEnergyGenome(strategies));
                case GiveCardGenome -> subjectDna.setGiveCardGenome(new GiveCardGenome(strategies));
                case InvocationForFreeGenome -> subjectDna.setInvocationForFreeGenome(new InvocationForFreeGenome(strategies));
                case PreludeGenome -> subjectDna.setPreludeGenome(new PreludeGenome(strategies));
                case RerollGenome -> subjectDna.setRerollGenome(new RerollGenome(strategies));
                case DiceGenome -> subjectDna.setDiceGenome(new DiceGenome(strategies));
                case ReturnInHandGenome -> subjectDna.setReturnInHandGenome(new ReturnInHandGenome(strategies));
                case gameplayGenome -> subjectDna.setGameplayGenome(new GameplayGenome(strategies));
                case cardGenome -> subjectDna.setCardGenome(new CardGenome(strategies));
                case ChangeTimeGenome -> subjectDna.setChangeTimeGenome(new ChangeTimeGenome(strategies));
                case EnergyGenome -> subjectDna.setEnergyGenome(new EnergyGenome(strategies));
                case SacrificeCardGenome -> subjectDna.setSacrificeCardGenome(new SacrificeCardGenome(strategies));
                case CopyGenome -> subjectDna.setCopyGenome(new CopyGenome(strategies));
            }
        }
        s.linkDNA(1, SeasonType.WINTER);
    }

    public void saveGlobalJson() {
        try (FileWriter fw = new FileWriter(PATH_GENOMES + filepath)) {

            fw.write(root.toJSONString());
            fw.flush();
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }


    public void initJson() {
        if (root == null) {
            root = new JSONObject();
        }
        if (!root.containsKey(DNA)) {
            root.put(DNA, new JSONObject());
        }
        dnaJSON = (JSONObject) root.get(DNA);
    }


    public static void main(String[] args) {
        DNA dna = new DNA();
        Subject s = new Subject(new DNA(), 0, 0);
        DnaManager mana = new DnaManager("./test.json");
        dna.mutateAllGenome();
        mana.exportDNA(dna);

        mana.loadDNA(s, "./test.json");

        mana.exportDNA(s.getDna());
    }
}
