package com.game.engine.effects.effects;

import com.game.engine.card.Card;
import com.game.engine.effects.AbstractEffect;
import com.game.engine.effects.EffectType;
import com.game.engine.gamemanager.GameEngine;
import com.game.playerassets.PersonalBoard;
import com.game.playerassets.ia.Player;

import java.util.ArrayList;

/**
 * Au moment où vous mettez en jeu le Calice divin, piochez 4 cartes
 * pouvoir et invoquez-en une gratuitement, sans payer son coût
 * d’invocation. Cette carte pouvoir ainsi mise en jeu ne déclenche
 * pas l’effet de l’Arcano Sangsue, du Bâton du printemps et du Vase
 * oublié d’Yjang. Placez les 3 cartes restantes dans la défausse.
 *
 * Vous  devez posséder une jauge d’invocation suffisante pour pouvoir
 * mettre en jeu cette nouvelle carte. Dans le cas contraire, la carte piochée
 * est défaussée.
 */
public class DivinEffect extends AbstractEffect {
    public static final int DRAW = 4;

    public DivinEffect(String name, EffectType effectType, GameEngine engine) {
        super(name, effectType, engine);
        setSingleEffect(true);
    }

    @Override
    public void applyEffect(GameEngine gameEngine, Player player) {
        PersonalBoard pb = gameEngine.getPlayersCentralManager().getPersonalBoardOfPlayerByID(player.getNumPlayer());
        StringBuilder description = player.getDescription();

        boolean canInvoke = (player.getFacadeIA().getInvocationGaugeF() - player.getFacadeIA().getInvokedCardsF().size()) >= 1
                && !pb.getCardManager().getCards().isEmpty();

        if (canInvoke) {
            //TODO deck empty exception des fois ... // peut etre réglé
            ArrayList<Card> cardDrawn = new ArrayList<>();
            int security = DRAW;
            int lastingCards  = gameEngine.getDeck().getCards().size() + gameEngine.getDeck().dispileCardsSize();
            if( lastingCards < DRAW){
                security = lastingCards;
            }

            for (int i = 0; i < security ; i++) {
                cardDrawn.add(gameEngine.getDeck().drawCard());
            }

            player.getFacadeIA().setChoosableCardsF(cardDrawn);
            Card c = player.chooseCardToInvokeForFree();
            cardDrawn.remove(c);

            for (Card card : cardDrawn) {
                gameEngine.getDeck().addCardToDispileCards(card);
            }

            description.append(String.format(" draws %d cards and chooses one, he can invoke it for free", DRAW));
            pb.getCardManager().invoke(c, true);
        } else {
            description.append(" he doesn't have enough invocation gauge he can't draw a card.");
        }
    }
}
