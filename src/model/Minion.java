package model;

public class Minion extends SoldierCard {
    private Buff ability;

    public void setAbility(Buff ability) {
        this.ability = ability;
    }

    public Buff getAbility() {
        return ability;
    }
}
