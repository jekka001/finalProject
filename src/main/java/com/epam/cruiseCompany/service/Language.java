package com.epam.cruiseCompany.service;

import java.util.Locale;

public enum Language {
    ENG(new Locale("en", "UK")),
    RUS(new Locale("ru", "RU")),
    UA(new Locale("uk", "UA"));

    private Locale locale;

    Language(Locale locale){this.locale = locale;}

    public Locale getLocale(){return locale;}
}
