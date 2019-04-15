package model;

public class Minion extends SoldierCard{
    private Spell ability;

    public Spell getAbility() {
        return ability;
    }

    public void setAbility(Spell ability) {
        this.ability = ability;
    }
}
