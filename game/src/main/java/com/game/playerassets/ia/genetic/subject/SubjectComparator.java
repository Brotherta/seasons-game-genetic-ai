package com.game.playerassets.ia.genetic.subject;

import java.util.Comparator;

public class SubjectComparator implements Comparator<Subject> {

    public SubjectComparator() {
    }

    @Override
    public int compare(Subject o1, Subject o2) {
        if (o1.getWinAmount() == o1.getWinAmount()) {
            if(o1.getScore() < o2.getScore() ){
                return 1;
            }
            else if (o1.getScore() == o2.getScore()){
                return 0;
            }
            return -1;
        }

        else {
            if(o1.getWinAmount() < o2.getWinAmount() ){
                return 1;
            }
            else {
                return -1;
            }
        }        
    }
}
