package model;

import model.Buff.Buff;

public class Minion extends SoldierCard {
    private Buff ability;

    public Minion() {
        super();
    }

    public Minion(Minion minion) {
        super(minion);
        this.ability = minion.ability;
    }

    public void setAbility(Buff ability) {
        this.ability = ability;
    }

    public Buff getAbility() {
        return ability;
    }
}
