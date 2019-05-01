package model.Cards;

import model.Buff.Buff;
import model.Target.Target;

public class Hero extends SoldierCard {
    private Buff specialPower;
    private int coolDown;
    private Target target;


    public Hero() {
        super();
    }

    public Hero(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type,
                int attackRange, String description, Buff specialPower, int coolDown, Target target) {
        super(cardId, name, price, mana, ap, hp, type, attackRange, description);
        this.specialPower = specialPower;
        this.coolDown = coolDown;
        this.target = target;
    }

    public Hero(Hero hero) {
        super(hero);
        this.specialPower = hero.specialPower;
        this.coolDown = hero.coolDown;
        this.target = hero.target;
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

    public Target getTarget() {
        return target;
    }

    public int getCoolDown() {
        return coolDown;
    }
    @Override
    public void setInBattleCardId(String accountName) {
        String id = accountName+"_"+this.getName()+"_1";
        super.setInBattleCardId(id);
    }

    public Buff getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buff specialPower) {
        this.specialPower = specialPower;
    }
}
