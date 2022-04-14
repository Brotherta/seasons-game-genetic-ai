package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;
import com.utils.Event;
import com.utils.Util;

import java.util.ArrayList;

public class SyllasEffect extends AbstractEffect {
    public static final int SACRIFICE = 1;

    public SyllasEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        StringBuilder description = player.getDescription();
        ArrayList<Player> others = Util.otherPlayers(gameEngine.getPlayersCentralManager().getPlayerList(), player);

        for (Player p : others) {
            if (!p.getFacadeIA().getInvokedCardsF().isEmpty()) {
                p.getFacadeIA().setChoosableCardsF(p.getFacadeIA().getInvokedCardsF());
                Card c = p.chooseCardPlayedToSacrifice();
                PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(p.getNumPlayer());
                pb.getCardManager().sacrificeCard(c);
                gameEngine.notifyAllObserver(pb.getGameObserver(), Event.SACRIFICE, p);
                description.append(String.format(" %s sacrificed %s", p.getName(), c.getName()));
            } else {
                description.append(String.format(" %s has no card to sacrifice", p.getName()));
            }
        }
    }
}