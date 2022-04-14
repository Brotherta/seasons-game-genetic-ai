package com.utils.loaders.cards;

import com.game.engine.card.Card;
import com.game.engine.card.Deck;
import com.game.engine.effects.EffectFactory;
import com.game.engine.effects.EffectTemplate;
import com.game.engine.effects.EffectType;
import com.game.engine.energy.EnergyType;
import com.game.engine.gamemanager.GameEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CardsLoader {

    private JSONObject cardsRoot;
    private static CardsLoader instance;

    private CardsLoader(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            cardsRoot = (JSONObject) parser.parse(new FileReader(filePath));
            cardsRoot = (JSONObject) cardsRoot.get("cards");
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

    public Deck loadDeck(int nbBots, GameEngine engine) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardsRoot.size(); i++) {
            JSONObject cardRoot = (JSONObject) cardsRoot.get("card" + (i + 1));

            String name = (String) cardRoot.get("name");
            int points = ((Long) cardRoot.get("points")).intValue();

            int[] energyCost = new int[4];
            if ((boolean) cardRoot.get("energyCost")) {
                JSONObject energiesRoot = (JSONObject) cardRoot.get("energiesCost");
                for (EnergyType type : EnergyType.values()) {
                    energyCost[type.ordinal()] = ((Long) energiesRoot.get(type.name())).intValue();
                }
            }

            int crystalCost = 0;
            if ((boolean) cardRoot.get("crystalCost")) {
                JSONObject crystalRoot = (JSONObject) cardRoot.get("crystalsCost");
                crystalCost = ((Long) crystalRoot.get(nbBots + "P")).intValue();
            }

            String effectType = (String) cardRoot.get("effectType");
            EffectType type = EffectType.valueOf(effectType);

            String sType = (String) cardRoot.get("type");
            Type cardType = Type.fromString(sType);

            Card card = new Card(name, i + 1, points, crystalCost, energyCost, cardType);
            Card cardCopy = new Card(name, i + 1, points, crystalCost, energyCost, cardType);
            EffectTemplate effect = EffectFactory.getInstance().generate(card.getName(), type, engine);
            EffectTemplate effectCopy = EffectFactory.getInstance().generate(cardCopy.getName(), type, engine);
            card.setEffect(effect);
            effect.setCard(card);
            cardCopy.setEffect(effectCopy);
            effectCopy.setCard(cardCopy);
            cards.add(card);
            cards.add(cardCopy);
        }
        return new Deck(cards);
    }

    public static CardsLoader getCardsLoader(String filePath) {
        if (instance == null) {
            instance = new CardsLoader(filePath);
        }
        return instance;
    }
}
