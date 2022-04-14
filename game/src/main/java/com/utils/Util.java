package com.utils;

import com.game.engine.SeasonType;
import com.game.engine.card.Card;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.Player;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    private static final SecureRandom r = new SecureRandom();
    public static final int MAX_AMOUNT_OF_DICE = 5;
    public static final String DISCONNECT = "disconnect";
    public static final String CONNECT = "connect";

    public static final String P_ARG = "-p";
    public static final String V_ARG = "-v";
    public static final String G_ARG = "-g";

    public static final String DNA_ARG = "-dna";
    public static final String LDNA_ARG = "-dnalist";
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean checkArgsVerbose(String[] args) {
        for (String arg : args) {
            if (arg.compareTo(V_ARG) == 0) {
                return true;
            }
        }
        return false;
    }

    public static int checkArgsPlayer(String[] args) {
        int numberPlayer = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo(P_ARG) == 0) {
                try {
                    numberPlayer = Integer.parseInt(args[i + 1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Not a number of player");

                }

                if (numberPlayer < 2 || numberPlayer > 4) {
                    throw new IllegalArgumentException("Illegal number of player");
                }
            }
        }
        if (numberPlayer == -1) {
            numberPlayer = 4;
        }
        return numberPlayer;
    }


    public static String checkArgsDNA(String[] args){
        String filepath ="";
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo(DNA_ARG) == 0) {
                try {
                    filepath = args[i + 1];
                    File fd = new File(filepath);
                    if(!fd.exists()){
                        throw new IllegalArgumentException("Not a file");
                    }
                    else{
                        return filepath;
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Not a file");

                }

            }
        }
        return null;
    }

    public static List<String> checkListArgsDNA(String[] args){
        String path = "./genetics_data/genomes/";
        String filepath ="";
        List<String> dnaList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo(LDNA_ARG) == 0) {
                try {

                    if(args[i + 1].compareTo("[") == 0){
                        int idx = i+2;
                        while(idx < args.length && args[idx].compareTo("]") != 0){

                            filepath = args[idx];
                            String elemPath = path + filepath;
                            File fd = new File(elemPath);
                            if(!fd.exists()){
                                throw new IllegalArgumentException("Not a file");
                            }
                            else{
                                dnaList.add(elemPath);
                            }
                            idx ++;
                        }
                        if(args[idx].compareTo("]") == 0){
                            if(dnaList.size() > 4){
                                throw new IllegalArgumentException("TOO MUCH FILE");
                            }
                            return dnaList;
                        }
                        else{
                            throw new IllegalArgumentException("BROKEN LIST");
                        }
                    }

                } catch (Exception e) {
                    throw new IllegalArgumentException("Not a file");

                }

            }
        }
        return null;
    }


    public static int checkArgsGame(String[] args) {
        int numberGames = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo(G_ARG) == 0) {
                try {
                    numberGames = Integer.parseInt(args[i + 1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Not a number of games");

                }
                if (numberGames < 1 || numberGames > 210000) {
                    throw new IllegalArgumentException("Illegal number of games");
                }
            }
        }

        if (numberGames == -1) {
            numberGames = 1;
        }
        return numberGames;
    }

    public static ArrayList<Player> otherPlayers(ArrayList<Player> players, Player player) {
        ArrayList<Player> otherP = new ArrayList<>();
        for (Player p : players) {
            if (!player.equals(p)) {
                otherP.add(p);
            }
        }
        return otherP;
    }

    public static String cardListToString(List<Card> tmpCards) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int j = 0; j < tmpCards.size(); j++) {
            if (j == tmpCards.size() - 1) {
                sb.append(tmpCards.get(j).getName());
            } else {
                sb.append(tmpCards.get(j).getName()).append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void checkPermanentEffect(ArrayList<PersonalBoard> personalBoards, GameEngine engine, EffectType type) {
        for (PersonalBoard personalBoard : personalBoards) {
            Util.checkPermanentEffect(personalBoard, engine, type);
        }
    }

    public static SecureRandom getR() {
        return r;
    }

    public static int getRandomInt(int bound) {
        return r.nextInt(bound);
    }

    public static int getRandomInt() {
        return r.nextInt();
    }

    public static boolean getRandomBoolean() {
        return r.nextBoolean();
    }

    public static int getRandomInt(int from, int to) {
        return r.nextInt(from, to);
    }

    public static void checkPermanentEffect(PersonalBoard personalBoard, GameEngine engine, EffectType type) {
        List<Card> invokedCards = personalBoard.getCardManager().getInvokeDeck().getCards();
        for (Card cardToCheck : invokedCards) {
            EffectTemplate effect = cardToCheck.getEffect();
            if (effect.getIsPermanentEffect() && effect.getEffectType().equals(type) && effect.canActivate(engine, personalBoard.getPlayer())) {

                StringBuilder description;
                if (effect.getEffectType().equals(EffectType.CRYSTAL) || effect.getEffectType().equals(EffectType.INVOKE)) {
                    description = personalBoard.getPlayer().getDescription();
                } else {
                    description = engine.getDescription();
                }

                description.append(String.format("%n\t-> %s of %s has been triggered by the %s :", cardToCheck.getName(), personalBoard.getPlayer(), type));
                effect.applyEffect(personalBoard.getGameEngine(), personalBoard.getPlayer());
            }
        }
    }

    public static SeasonType getNextSeasons(SeasonType s) {
        return getSeasonsInNSeasons(s, 1);
    }

    public static SeasonType getPreviousSeasons(SeasonType s){return getSeasonsInNSeasons(s,-1);
    }

    public static SeasonType getSeasonsInNSeasons(SeasonType s, int n) {

            return SeasonType.values()[((s.ordinal() + n) +4) % 4];

    }

    public static SeasonType getSeasonsFromDateInNmonth(int month, int nmonth) {
        int newMonth = ((month + nmonth) % 12)+1  ;
        int numSeasons = newMonth / 4;

        SeasonType se;
        switch (numSeasons) {
            case 1 -> se = SeasonType.SPRING;
            case 2 -> se = SeasonType.SUMMER;
            case 3 -> se = SeasonType.AUTUMN;
            default -> se = SeasonType.WINTER;
        }

        return se;

    }

    public static Player mostEnergyOfAType(EnergyType MostValuableType, List<Player> players){
        Player richest = null;
        int max = 0;
        for (Player p : players) {
            int[] energies = p.getFacadeIA().getAmountOfEnergiesArrayF();
            if (energies[MostValuableType.ordinal()] > max) {
                richest = p;
                max = energies[MostValuableType.ordinal()];
            }
        }
        return richest;
    }

    public static Player getRichest(FacadeIA facadeIA) {
        List<Player> players = facadeIA.getPlayersF();
        int max = 0;
        Player richest = players.get(0);
        for (Player p : players) {
            int amount = Arrays.stream(p.getFacadeIA().getAmountOfEnergiesArrayF()).sum();
            if (amount > max) {
                max = amount;
                richest = p;
            }
        }
        return richest;
    }

    /**
     * @param energies
     * @return retourne le premier type présent en majorité dans un tableau d'énergie quelconque
     */
    public static EnergyType maxTypeOwned(int[] energies) {
        int max = 0;
        int maxIndex = 0;
        for(int i = 0 ; i < EnergyType.values().length; i++){
            if(energies[i] > max){
                maxIndex = i;
                max = energies[i];
            }
        }
        return EnergyType.values()[maxIndex];
    }

}
