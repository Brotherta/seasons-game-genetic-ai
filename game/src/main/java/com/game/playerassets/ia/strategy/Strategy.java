package com.game.playerassets.ia.strategy;

import com.game.playerassets.ia.FacadeIA;

public interface Strategy {
    Object act(FacadeIA facadeIA);

    Object canAct(FacadeIA facadeIA);

    Strategy getNextStrategy();

    int getId();

    TypeStrategy getType();

    int getConflicts();

    AbstractStrategy clone();

}
