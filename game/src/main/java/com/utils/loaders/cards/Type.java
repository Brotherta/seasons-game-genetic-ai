package com.utils.loaders.cards;

public enum Type {
    OBJECT("OBJECT"),
    FAMILIAR("FAMILIAR");

    private final String text;

    Type(String s ){
        this.text = s ;
    }

    public static Type fromString(String text) {
        for (Type t : Type.values()) {
            if (t.text.equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null;
    }
}
