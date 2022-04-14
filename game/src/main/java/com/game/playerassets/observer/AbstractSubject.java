package com.game.playerassets.observer;

import com.utils.Event;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSubject {

    private final List<GameObserver> observerAttached;

    public AbstractSubject() {
        observerAttached = new ArrayList<>();
    }

    public void notifyAllObserver(GameObserver goWhoDid, Event event, Object o) {
        for (GameObserver go : observerAttached) {
            if (!go.equals(goWhoDid)) {
                go.update(goWhoDid, event, o);
            }
        }
    }

    public void attach(GameObserver go) {
        observerAttached.add(go);
    }
}