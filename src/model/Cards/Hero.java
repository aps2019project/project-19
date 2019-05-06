package model.Cards;

import model.AbilityCastTime;
import model.Buff.Buff;
import model.Target.Target;

public class Hero extends SoldierCard {
    private Buff specialPower;
    private int coolDown;


    public Hero() {
        super();
    }

    public Hero(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type,
                int attackRange, String description, Buff specialPower, int coolDown, Target target) {
        super(cardId, name, price, mana, ap, hp, type, attackRange, description);
        this.specialPower = specialPower;
        this.coolDown = coolDown;
        this.setTarget(target);
    }

    public Hero(Hero hero) {
        super(hero);
        this.specialPower = hero.specialPower;
        this.coolDown = hero.coolDown;
    }
    public Hero(Hero hero, int cardId) {
        super(hero);
        this.specialPower = hero.specialPower;
        this.coolDown = hero.coolDown;
        this.setCardId(cardId);
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

    public int getCoolDown() {
        return coolDown;
    }

    @Override
    public void setInBattleCardId(String accountName) {
        String id = accountName + "_" + this.getName() + "_1";
        id = id.replaceAll(" ", "_");
        super.setInBattleCardId(id);
    }

    public Buff getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buff specialPower) {
        this.specialPower = specialPower;
    }

}
