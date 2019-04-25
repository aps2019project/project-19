package model;

import model.Buff.Buff;

import java.util.ArrayList;

public class Minion extends SoldierCard {
    private ArrayList<Buff> buffs = new ArrayList<>();

    public Minion() {
        super();
    }

    public Minion(Minion minion) {
        super(minion);
        this.buffs = minion.buffs;
    }

    @Override
    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

}
