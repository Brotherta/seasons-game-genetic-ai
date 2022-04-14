package com.game.engine.effects;

import com.game.engine.effects.effects.*;
import com.game.engine.gamemanager.GameEngine;

public class EffectFactory {

    private static final EffectFactory INSTANCE = new EffectFactory();

    private EffectFactory() {}

    public static EffectFactory getInstance() {
        return INSTANCE;
    }

    public AbstractEffect generate(String cardName, EffectType type, GameEngine engine) {
        return switch (cardName) {
            case "Amulet of Air" -> new AirEffect(cardName, type, engine);
            case "Amulet of Fire" -> new FireEffect(cardName, type, engine);
            case "Amulet of Earth" -> new EarthEffect(cardName, type, engine);
            case "Amulet of Water" -> new WaterEffect(cardName, type, engine);
            case "Balance of Ishtar" -> new IshtarEffect(cardName, type, engine);
            case "Staff of Spring" -> new SpringEffect(cardName, type, engine);
            case "Temporal Boots" -> new TemporalEffect(cardName, type, engine);
            case "Purse of Io" -> new IoEffect(cardName, type, engine);
            case "Divine Chalice" -> new DivinEffect(cardName, type, engine);
            case "Syllas the Faithful" -> new SyllasEffect(cardName, type, engine);
            case "Figirm the Avaricious" -> new FigrimEffect(cardName, type, engine);
            case "Naria the Prophetess" -> new NariaEffect(cardName, type, engine);
            case "Wondrous Chest" -> new ChestEffect(cardName, type, engine);
            case "Beggar's Horn" -> new MendiantEffect(cardName, type, engine);
            case "Dice of Malice" -> new MaliceEffect(cardName, type, engine);
            case "Kairn the Destroyer" -> new KairnEffect(cardName, type, engine);
            case "Amsung Longneck" -> new AmsungEffect(cardName, type, engine);
            case "Bespelled Grimoire" -> new GrimoirEffect(cardName, type, engine);
            case "Ragfield's Helm" -> new RagfieldEffect(cardName, type, engine);
            case "Hand of Fortune" -> new FortuneEffect(cardName, type, engine);
            case "Lewis Grisemine" -> new GrismineEffect(cardName, type, engine);
            case "Potion of Power" -> new PowerEffect(cardName, type, engine);
            case "Potion of Dreams" -> new DreamEffect(cardName, type, engine);
            case "Potion of Knowledge" -> new KnowledgeEffect(cardName, type, engine);
            case "Potion of Life" -> new LifeEffect(cardName, type, engine);
            case "Hourglass of Time" -> new TimeEffect(cardName, type, engine);
            case "Scepter of Greatness" -> new GreatnessEffect(cardName, type, engine);
            case "Olad's Blessed Statue" -> new OlafEffect(cardName, type, engine);
            case "Yjang's Forgotten Vase" -> new YjangEffect(cardName, type, engine);
            default -> new EmptyEffect(cardName, type, engine);
        };
    }
}
