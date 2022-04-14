package com.game.playerassets.ia.strategy.dice;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

/**
 * Privilégie un dé en prenant en compte tout ses faces
 * pour obtenir de l'invocation en le relançant
 * si aucun dé d'invocation n'est disponible.
 */
public class DiceStrategyDIcesOfMaliceInvoke extends AbstractDiceStrategy{
    private final static int ID = 429;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    private final static int MALICE_NB = 15;

    public DiceStrategyDIcesOfMaliceInvoke(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }

    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(MALICE_NB, canActivateCards);
        if(card!= null){
            List<Dice> dices = facadeIA.getRolledDicesF();
            boolean invokeDice = false;
            for(Dice d : dices){
                if(d.getActualFace().isInvocation()){
                    invokeDice = true;
                    break;
                }
            }
            if(!invokeDice){
                return dices.get(0);
            }
        }
        return getNextStrategy().canAct(facadeIA);
    }
}
