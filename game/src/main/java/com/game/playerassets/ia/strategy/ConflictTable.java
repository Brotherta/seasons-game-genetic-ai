package com.game.playerassets.ia.strategy;

public class ConflictTable {

    /**
     * Type of conflicts
     * <p>
     * 0 = Ishtar Activation
     * 1 = Temporal Boot
     * 2 = Kairn Activation
     * 3 = Power Sacrifice
     * 4 = Knowledge Sacrifice
     * 5 = Life Sacrifice
     * 6 = Dream Sacrifice
     * 7 = Greatness Invocation
     * 8 = Syllas Invocation
     * 9 = Naria Invocation
     * 10 = Amsung Invocation
     * 11 = Crystallization Bonus
     * 12 = Draw Bonus
     * 13 = Gauge Bonus
     * 14 = Exchange Bonus
     * 15 = Grismine Invocation
     * 16 = Crystallization
     * 17 = Reroll
     * 18 = ??? a compléter si nécessaire (update LEN !!)
     */

    private final static int LEN = 18;
    private final static int NB_CARD = 30;
    private final boolean[] conflicts;
    private final boolean[] cardsWait;

    public ConflictTable() {
        this.conflicts = new boolean[LEN];
        this.cardsWait = new boolean[NB_CARD];
        initTable();
    }

    public void initWait() {
        for (int i = 0; i < LEN; i++) {
            cardsWait[i] = false;
        }
    }

    public void initTable() {
        for (int i = 0; i < LEN; i++) {
            conflicts[i] = false;
        }
        initWait();
    }

    public void updateWait(int id) {
        cardsWait[id - 1] = true;
    }

    public boolean hasToWait(int id) {
        return cardsWait[id - 1];
    }

    public void updateConflicts(int id) {
        conflicts[id] = true;
    }

    public void updateConflictsFalse(int id) {
        conflicts[id] = false;
    }

    public boolean hasConflicts(int id) {
        return conflicts[id];
    }
}
