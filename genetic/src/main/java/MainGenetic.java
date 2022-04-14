import java.io.File;
import java.util.Scanner;

import com.game.playerassets.ia.genetic.Genetic;

public class MainGenetic {

    private final static int NB_GEN = 10
    ;
    private final static int POPULATION_SIZE = 20;
    private final static double POPULATION_MUTANT = 0.9;
    private final static double POPULATION_CHILD = 0.30;
    private final static double POPULATION_WINNERS = 0.40;
    private final static double MUTATION_RATE = 0.01;
    private final static int NB_GAMES = 300;

    private final static String PATH = "genetics_data/";
    private final static String PATH_TOURNAMENTS = "./genetics_data/tournaments/";
    private final static String PATH_GENOMES = "./genetics_data/genomes/";

    public MainGenetic() {
        try {
            File file = new File(PATH);
            File file1 = new File(PATH_TOURNAMENTS);
            File file2 = new File(PATH_GENOMES);

            if (!file.exists()) {
                file.mkdir();
            }
            if (!file1.exists()) {
                file1.mkdir();
            }
            if (!file2.exists()) {
                file2.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchWithStaticValue() {
        String name = getName();
        
        Genetic genetic = new Genetic(NB_GEN, POPULATION_SIZE, NB_GAMES, POPULATION_MUTANT, POPULATION_CHILD, POPULATION_WINNERS, MUTATION_RATE);
        genetic.start(name+"_g", name+"_t");
    }


    private void launchVariatingNumberOfGames_1_50() {
        String t = getName();

        for (int i = 10; i < 80; i += 10) {
            Genetic genetic = new Genetic(NB_GEN, POPULATION_SIZE, i, POPULATION_MUTANT, POPULATION_CHILD, POPULATION_WINNERS, MUTATION_RATE);
            genetic.start(t + "_nbGames_" + String.valueOf(i) + "_g", t + "_nbGames_" + String.valueOf(i) + "_t");
        }
    }
    private void launchVariatingMutRate_0_0_1_0_05() {
        String t = getName();
        double[] mutArray = new double[]{0.01,0.02,0.03,0.04,0.05,0.06,0.07,0.08,0.09,0.1};
        for (int i = 0; i < mutArray.length; i ++) {
            Genetic genetic = new Genetic(NB_GEN, POPULATION_SIZE, NB_GAMES, POPULATION_MUTANT, POPULATION_CHILD, POPULATION_WINNERS, mutArray[i]);
            genetic.start(t + "_MutRate_" + String.valueOf(mutArray[i]) + "_g", t + "_MutRate_" + String.valueOf(mutArray[i]) + "_t");
        }
    }
    private void launchVariatingNbChild_5_30() {
        String t = getName();

        for (double i = 0.05; i < 0.5; i += 0.1) {
            Genetic genetic = new Genetic(NB_GEN, POPULATION_SIZE, NB_GAMES, i, POPULATION_CHILD, POPULATION_WINNERS, MUTATION_RATE);
            genetic.start(t + "_nbChild_" + String.valueOf(i) + "_g", t + "_nbChild_" + String.valueOf(i) + "_t");
        }
    }
    private void launchWithDnaFile(String dnaFile){
        String name = getName();
        
        Genetic genetic = new Genetic(NB_GEN, POPULATION_SIZE, NB_GAMES, POPULATION_MUTANT, POPULATION_CHILD, POPULATION_WINNERS, MUTATION_RATE);
        genetic.start(name+"_g", name+"_t",dnaFile);
    }

    private String getName() {
        String tournamentName;
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("Enter the name of this run");
            tournamentName = s.nextLine();
        } while (tournamentName == null);
        return tournamentName;
    }

    public static void main(String[] args) {
        MainGenetic mg = new MainGenetic();
        mg.launchWithStaticValue();
        // mg.launchVariatingNumberOfGames_1_50();
        //mg.launchVariatingMutRate_0_0_1_0_05();
        // mg.launchVariatingNbChild_5_30();
        mg.launchWithDnaFile("./genetics_data/genomes/_MutRate_0.01_g.json");
    }
}
