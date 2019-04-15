package model;

public class Hero extends SoldierCard{
    private Spell specialPower;

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }
}
