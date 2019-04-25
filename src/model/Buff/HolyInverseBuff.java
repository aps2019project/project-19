package model.Buff;

import model.SoldierCard;

public class HolyInverseBuff extends Buff {
    private int moreDamage;
    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(-this.moreDamage);
    }

    public HolyInverseBuff(Kind kind, int duration, boolean isContinuous, int moreDamage) {
        super(kind, duration, isContinuous);
        this.moreDamage = moreDamage;
    }
}
