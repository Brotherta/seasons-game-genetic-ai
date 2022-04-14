package com.game.playerassets.ia.strategy.dice;

import com.game.engine.card.Card;
import com.game.engine.dice.Dice;
import com.game.engine.dice.DiceFace;
import com.game.playerassets.ia.FacadeIA;
import com.game.playerassets.ia.strategy.Strategy;
import com.game.playerassets.ia.strategy.TypeStrategy;

import java.util.List;

public class DiceStrategySiceOfMaliceMaximiseCrystal extends AbstractDiceStrategy {
    private final static int ID = 433;
    private final static TypeStrategy TYPE = TypeStrategy.DEFAULT;
    private final static int MALICE_NB = 15;
    private final static int MAX_CRYSTAL = 6;

    public DiceStrategySiceOfMaliceMaximiseCrystal(Strategy nextStrategy) {
        super(nextStrategy, ID, TYPE);
    }


    @Override
    public Object act(FacadeIA facadeIA) {
        List<Card> canActivateCards = facadeIA.getActivableCardsF();
        Card card = Card.getCardInList(MALICE_NB, canActivateCards);
        if (card != null) {
            boolean dieCrystal = false;
            Dice diceCrystal = null;
            List<Dice> dices = facadeIA.getRolledDicesF();
            for (Dice d : dices) {
                if (diceCrystal == null) {
                    for (DiceFace face : d.getFaces()) {
                        if (face.getCrystal() == 6) {
                            diceCrystal = d;
                            break;
                        }
                    }
                }

                if (d.getActualFace().getCrystal() == MAX_CRYSTAL) {
                    dieCrystal = true;
                    break;
                }

            }
            if (!dieCrystal && diceCrystal != null) {
                return diceCrystal;
            }

        }
        return getNextStrategy().canAct(facadeIA);
    }
    }
