package com.game.engine.effects;

import com.game.engine.card.Card;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.ia.Player;

public abstract class AbstractEffect implements EffectTemplate {
    String name;
    Boolean isSingleEffect;
    Boolean isActivationEffect;
    Boolean isPermanentEffect;
    EffectType type;
    Card card;
    GameEngine engine;

    public AbstractEffect(String name, EffectType effectType, GameEngine engine) {
        this.name = name;
        this.isSingleEffect = false;
        this.isActivationEffect = false;
        this.isPermanentEffect = false;
        this.type = effectType;
        this.engine = engine;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean getIsSingleEffect() {
        return isSingleEffect;
    }

    @Override
    public Boolean getIsActivationEffect() {
        return isActivationEffect;
    }

    @Override
    public Boolean getIsPermanentEffect() {
        return isPermanentEffect;
    }

    @Override
    public void setSingleEffect(boolean b) {
        this.isSingleEffect = b;
    }

    @Override
    public void setActivationEffect(boolean b) {
        this.isActivationEffect = b;
    }

    @Override
    public void setPermanentEffect(boolean b) {
        this.isPermanentEffect = b;
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        // nothing
    }

    @Override
    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public void unapplyEffect(GameEngine gameEngine, Player player) {
        // nothing in the air...
    }

    @Override
    public boolean canActivate(GameEngine gameEngine, Player player) {
        return true;
    }

    @Override
    public EffectType getEffectType() {
        return type;
    }
}
