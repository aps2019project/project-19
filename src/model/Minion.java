package model;

import model.Buff.Buff;

import java.util.ArrayList;

public class Minion extends SoldierCard {
    private ArrayList<Buff> abilities = new ArrayList<>();
    private AbilityCastTime abilityCastTime;

    public Minion() {
        super();
    }

    public Minion(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, AbilityCastTime abilityCastTime, ArrayList<Buff> abilities) {
        super(cardId, name, price, mana, ap, hp, type, attackRange);
        this.abilities = abilities;
        this.abilityCastTime = abilityCastTime;
    }

    public Minion(Minion minion) {
        super(minion);
        this.abilities = minion.abilities;
    }

    @Override
    public ArrayList<Buff> getBuffs() {
        return abilities;
    }

}
