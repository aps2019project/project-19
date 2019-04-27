package model.Cards;

import model.AbilityCastTime;
import model.Buff.Buff;
import view.ShowFormat;

import java.util.ArrayList;

public class Minion extends SoldierCard implements ShowFormat {
    private ArrayList<Buff> abilities = new ArrayList<>();
    private AbilityCastTime abilityCastTime;

    public Minion() {
        super();
    }

    public Minion(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, String descrption, AbilityCastTime abilityCastTime, ArrayList<Buff> abilities) {
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


    @Override
    public String toBattleFormat() {
        return getInBattleCardId() + " : "
                + getName() + ", "
                + "health : " + getHp() + ", "
                + "location : " + "(" + getCell().getxCoordinate() + ", " + getCell().getyCoordinate() + "), "
                + "Power : " + getAp();
    }

    @Override
    public ArrayList<Buff> getBuffs() {
        return abilities;
    }

}
