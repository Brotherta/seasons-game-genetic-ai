package com.game.playerassets.observer;

import com.game.playerassets.ia.Player;

import java.util.HashMap;
import java.util.List;

public class Container {

    private final HashMap<Player, PlayerContainer> dictPlayerContainer;

    public Container() {
        dictPlayerContainer = new HashMap<>();
    }

    public void registerPlayerContainer(Player p) {
        dictPlayerContainer.put(p, new PlayerContainer());
    }

    public HashMap<Player, PlayerContainer> getDictPlayerContainer() {
        return dictPlayerContainer;
    }

    public List<Player> getPlayers(){return dictPlayerContainer.keySet().stream().toList();}

    public PlayerContainer getPlayerContainer(Player p){return dictPlayerContainer.get(p);}

    public void reset() {
        dictPlayerContainer.replaceAll((p, v) -> new PlayerContainer());
    }
}