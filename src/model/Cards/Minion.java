package model.Cards;

import model.AbilityCastTime;
import model.Buff.Buff;

import java.util.ArrayList;

public class Minion extends SoldierCard {
    private ArrayList<Buff> abilities = new ArrayList<>();
    private AbilityCastTime abilityCastTime;

    public Minion() {
        super();
    }

    public Minion(int cardId, String name, int price, int mana, int ap, int hp,
                  SoldierTypes type, int attackRange, String descrption,
                  AbilityCastTime abilityCastTime, ArrayList<Buff> abilities) {
        super(cardId, name, price, mana, ap, hp, type, attackRange, descrption);
        this.abilities = abilities;
        this.abilityCastTime = abilityCastTime;
    }

    public Minion(Minion minion) {
        super(minion);
        this.abilities = minion.abilities;
    }

    @Override
    public String toString() {
        return "Type : Minion - "
                + "Name : " + getName() + " - "
                + "Class : " + getType() + " - "
                + "AP : " + getAp() + " - "
                + "HP : " + getHp() + " - "
                + "MP : " + getMana() + " - "
                + "Special power : " + getDescription();
    }

    public String toInfoString() {
        return "Minion:\n"
                + "Name: " + getName() + "\n"
                + "HP: " + getHp() + " Ap: " + getAp() + " MP: " + getMana() + "\n"
                + "Range: " + getAttackRange() + "\n"
                + "Combo-ability: " + getDescription() + "\n"
                + "Cost: " + getPrice() + "\n"
                + "Desc: " + getDescription();
    }

    @Override
    public ArrayList<Buff> getBuffs() {
        return abilities;
    }

    public AbilityCastTime getAbilityCastTime() {
        return abilityCastTime;
    }
}
