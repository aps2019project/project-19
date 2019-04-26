package model.Cards;

import model.Buff.Buff;

public class Hero extends SoldierCard {
    private Buff specialPower;

    public Hero() {
        super();
    }

    public Hero(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, Buff specialPower) {
        super(cardId, name, price, mana, ap, hp, type, attackRange);
        this.specialPower = specialPower;
    }

    public Hero(Hero hero) {
        super(hero);
        this.specialPower = hero.specialPower;
    }

    public Buff getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buff specialPower) {
        this.specialPower = specialPower;
    }
}
