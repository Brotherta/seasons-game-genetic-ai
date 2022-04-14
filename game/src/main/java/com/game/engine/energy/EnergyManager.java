package com.game.engine.energy;

import com.game.engine.SeasonType;
import com.game.playerassets.PersonalBoard;
import com.utils.Util;

import java.util.Arrays;

/**
 * Gestionnaire de l'inventaire d'Energy, permet la manipulation du stock d'Energy
 */
public class EnergyManager {

    private int gauge;
    private static final int MAX_GAUGE = 10;
    private static final int INITIAL_GAUGE = 7;
    private final int[] energyList;
    private Amulet waterAmulet;
    private static final int[][] energyCost = new int[][]{ // Energies en fonction des saisons
            {1, 2, 3, 1}, // Air
            {1, 1, 2, 3}, // Water
            {2, 3, 1, 1}, // Fire
            {3, 1, 1, 2} // Earth
    };

    /**
     * Initialise les piles
     */
    public EnergyManager(PersonalBoard personalBoard) {
        this.energyList = new int[EnergyType.values().length];
        this.gauge = 7;
        for (int i = 0; i < energyList.length; i++) {
            this.energyList[i] = 0;
        }
    }

    public boolean hasEnoughRoom(int amount) {
        if (amount < 0) {
            return false;
        }
        boolean enough = true;
        int total = getAmountofEnergies();
        if (total + amount > gauge) {
            enough = false;
        }
        return enough;
    }

    public int getGauge() {
        return gauge;
    }

    /**
     * Ajoute l'energie dans la pile correspondante au type
     *
     * @param type Type de l'Energy souhaitée
     */
    public void addEnergy(EnergyType type) {
        if (getAmountofEnergies() < gauge) {
            energyList[type.ordinal()]++;
        }
    }

    /**
     * Ajoute l'energie dans la pile correspondante au type dans la quantitée souhaitée
     *
     * @param type     Type de l'Energy souhaitée
     * @param quantity Quantitée souhaitée
     */
    public void addEnergy(EnergyType type, int quantity) {
        for (int i = 0; i < quantity; i++) {
            addEnergy(type);
        }
    }

    public void addEnergy(int[] energies) {
        for (int i = 0; i < energies.length; i++) {
            for (int nbEnergy = energies[i]; nbEnergy > 0; nbEnergy--) {
                energyList[i]++;
                if (getAmountofEnergies() == gauge) {
                    return;
                }
            }
        }
    }

    /**
     * Vérifie si la consommation est possible, si oui, consomme et met à jour la pile correspondante.
     *
     * @param type     Type de l'Energy souhaitée
     * @param quantity Quantité souhaitée
     */
    public void consumeEnergy(EnergyType type, int quantity) {
        for (int i = 0; i < quantity; i++) {
            energyList[type.ordinal()]--;
        }
    }

    public void consumeEnergy(int[] energyCost) {
        for (EnergyType type : EnergyType.values()) {
            consumeEnergy(type, energyCost[type.ordinal()]);
        }
    }

    /**
     * Vérifie s'il y a assez d'énergie du type indiqué disponible
     *
     * @param type Type de l'Energy
     * @param quantity Quantité d'Energy
     */
    public boolean hasEnoughEnergy(EnergyType type, int quantity) {
        return energyList[type.ordinal()] >= quantity && quantity >= 0;
    }

    public boolean hasEnoughEnergy(int[] quantity) {
        for (EnergyType type : EnergyType.values()) {
            if (!(energyList[type.ordinal()] >= quantity[type.ordinal()] && quantity[type.ordinal()] >= 0)) {
                return false;
            }
        }
        return true;
    }

    public static int[] chooseRandomsEnergies(EnergyManager energyManager) {
        int[] randoms = new int[4];
        for (EnergyType type : EnergyType.values()) {
            randoms[type.ordinal()] = Util.getRandomInt(energyManager.getAmountOfEnergyType(type) + 1);
        }

        return randoms;
    }

    public static int[] chooseRandomsEnergies(EnergyManager energyManager, int sum) {
        int[] randoms = chooseRandomsEnergies(energyManager);
        if (sum <= Arrays.stream(randoms).sum()) {
            int[] finalRandom = new int[randoms.length];

            while (Arrays.stream(finalRandom).sum() < sum) {
                for (int i = 0; i < randoms.length; i++) {
                    if (randoms[i] > 0 && Arrays.stream(finalRandom).sum() < sum) {
                        finalRandom[i] += 1;
                    }
                }
            }
            return finalRandom;
        }
        return randoms;
    }

    /**
     * Vérifie la taille d'une des piles
     *
     * @param type Type de l'Energy
     * @return Taille de la pile en question
     */
    public int getAmountOfEnergyType(EnergyType type) {
        return energyList[type.ordinal()];
    }

    public int getAmountofEnergies() {
        int sum = 0;
        for (int j : energyList) {
            sum += j;
        }
        return sum;
    }

    public int[] getAmountOfEnergiesArray() {
        int[] energies = new int[EnergyType.values().length];
        for (int i = 0; i < EnergyType.values().length; i++) {
            energies[i] = getAmountOfEnergyType(EnergyType.values()[i]);
        }
        return energies;
    }

    public static int getEnergyPrice(EnergyType energy, SeasonType seasonType) {
        return energyCost[energy.ordinal()][seasonType.ordinal()];
    }

    public static String displayEnergiesArray(EnergyManager em) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (EnergyType type : EnergyType.values()) {
            sb.append(String.format("%s : %d; ", type.toString(), em.getAmountOfEnergyType(type)));
        }
        sb.append("]");

        return sb.toString();
    }

    public static String displayEnergiesFromAnArray(int[] energies) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < energies.length; i++) {
            sb.append(String.format("%s : %d; ", EnergyType.values()[i], energies[i]));
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * @return Affichage propre des piles et de leurs contenus.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < energyList.length; i++) {
            if (energyList[i] > 0) {
                sb.append(energyList[i]);
                sb.append(" ");
                sb.append(EnergyType.getName(i));
                sb.append(" ");
            }
        }

        if (sb.toString().equals("")) sb.append("EMPTY");
        return sb.toString();
    }

    public void setDebugEnergy(int idx){
        energyList[idx] = 0;
    }

    public static int getMaxGauge() {
        return MAX_GAUGE;
    }

    public void setWaterAmulet(Amulet waterAmulet) {
        this.waterAmulet = waterAmulet;
    }

    public Amulet getWaterAmulet() {
        return waterAmulet;
    }

    public static int getInitialGauge() {
        return INITIAL_GAUGE;
    }

    public void setGauge(int gauge) {
        this.gauge = gauge;
    }

    public void reset() {
        this.gauge = 7;
        waterAmulet = null;
        Arrays.fill(energyList, 0);
    }
}
