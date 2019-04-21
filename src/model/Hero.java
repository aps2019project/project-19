package model;

import model.Buff.Buff;

public class Hero extends SoldierCard {
    private Buff specialPower;

    public Hero() {
        super();
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
