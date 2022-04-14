package com.game.playerassets.ia.strategy;

import com.game.playerassets.ia.FacadeIA;

public class AbstractStrategy implements Strategy, Cloneable {
    private final Strategy nextStrategy;
    private final int id;
    private final TypeStrategy type;

    public AbstractStrategy(Strategy nextStrategy, int id, TypeStrategy type) {
        this.nextStrategy = nextStrategy;
        this.id = id;
        this.type = type;
    }

    @Override
    public Object canAct(FacadeIA facadeIA) {
        int conflict = getConflicts();
        if (conflict != -1) {
            if (facadeIA.hasConflictsF(conflict)) {
                return getNextStrategy().canAct(facadeIA);
            } else {
                facadeIA.updateConflictsF(conflict);
                return act(facadeIA);
            }
        }
        return act(facadeIA);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        return null;
    }

    @Override
    public Strategy getNextStrategy() {
        return nextStrategy;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public TypeStrategy getType() {
        return type;
    }

    @Override
    public int getConflicts() {
        return -1;
    }

    @Override
    public AbstractStrategy clone() {
        try {
            AbstractStrategy clone = (AbstractStrategy) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
