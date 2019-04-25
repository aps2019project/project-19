package model;

import model.Buff.Buff;

import java.util.ArrayList;

public class Minion extends SoldierCard {
    private ArrayList<Buff> buffs;
    private int attackRange;

    public Minion(ArrayList<Buff> buffs, int attackRange) {
        this.buffs = buffs;
        this.attackRange = attackRange;
    }

    public Minion(SoldierCard soldierCard, ArrayList<Buff> buffs, int attackRange) {
        super(soldierCard);
        this.buffs = buffs;
        this.attackRange = attackRange;
        this.buffs = new ArrayList<>();
    }

    @Override
    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }
}
