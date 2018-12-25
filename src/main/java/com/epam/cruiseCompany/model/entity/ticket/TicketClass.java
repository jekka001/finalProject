package com.epam.cruiseCompany.model.entity.ticket;

import com.epam.cruiseCompany.model.entity.ticket.bonus.Bonus;

import java.util.Arrays;
import java.util.List;

public enum TicketClass {
    ECONOMIC(Bonus.CINEMA_HALL),
    STANDART(Bonus.CINEMA_HALL, Bonus.SPORTS_HALL),
    STANDART_PLUS(Bonus.CINEMA_HALL, Bonus.SPORTS_HALL, Bonus.COSMETIC_SALON),
    LUX(Bonus.CINEMA_HALL, Bonus.SPORTS_HALL, Bonus.COSMETIC_SALON, Bonus.POOL),
    NO_CLASS(Bonus.NO_BONUS);

    private List<Bonus> bonuses;

    TicketClass(Bonus... bonusList){this.bonuses = Arrays.asList(bonusList);}

    public List<Bonus> getBonuses(){return bonuses;}
}
