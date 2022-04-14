package com.game.engine.effects;

import com.game.engine.card.Card;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

public interface EffectTemplate {
    String getName();
    Boolean getIsSingleEffect();
    Boolean getIsActivationEffect();
    Boolean getIsPermanentEffect();

    void setSingleEffect(boolean b);
    void setActivationEffect(boolean b);
    void setPermanentEffect(boolean b);

    void applyEffect(GameEngine gameEngine, Player player);
    void unapplyEffect(GameEngine gameEngine, Player player);
    boolean canActivate(GameEngine gameEngine, Player player);

    EffectType getEffectType();

    void setCard(Card card);
    Card getCard();
}
