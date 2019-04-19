package model;

public class Hero extends SoldierCard {
    private Buff specialPower;

    public Buff getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buff specialPower) {
        this.specialPower = specialPower;
    }
}
