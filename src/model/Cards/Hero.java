package model.Cards;

import model.Buff.Buff;
import view.ShowFormat;

public class Hero extends SoldierCard {
    private Buff specialPower;

    public Hero() {
        super();
    }

    public Hero(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, String description, Buff specialPower) {
        super(cardId, name, price, mana, ap, hp, type, attackRange, description);
        this.specialPower = specialPower;
    }

    public Hero(Hero hero) {
        super(hero);
        this.specialPower = hero.specialPower;
    }

    @Override
    public String toString() {
        return "Name : " + getName() + " - "
                + "AP : " + getAp() + " - "
                + "HP : " + getHp() + " - "
                + "Class : " + getType() + " - "
                + "Special power : " + getDescription();
    }

    public String toInfoString() {
        return "Hero:\n"
                + "Name: " + getName() + "\n"
                + "Cost: " + getPrice() + "\n"
                + "Desc: " + getDescription();
    }

    public Buff getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buff specialPower) {
        this.specialPower = specialPower;
    }
}
