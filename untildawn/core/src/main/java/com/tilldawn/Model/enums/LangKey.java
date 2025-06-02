package com.tilldawn.Model.enums;

import java.util.HashMap;

public enum LangKey {
    
    ;

    private final HashMap<Language, String> translation;

    LangKey(HashMap<Language, String> translation) {
        this.translation = translation;
    }

    public HashMap<Language, String> getTranslation() {
        return translation;
    }
}
