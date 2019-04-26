package model.Buff;

import model.Cards.SoldierCard;

public class WeaknessBuff extends Buff {
    private int hpDecrease;
    private int apDecrease;
    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeAp(-this.apDecrease);
        soldier.changeHp(-this.hpDecrease);
    }

    public WeaknessBuff(Kind kind, int duration, boolean isContinuous, int hpDecrease, int apDecrease) {
        super(kind, duration, isContinuous);
        this.hpDecrease = hpDecrease;
        this.apDecrease = apDecrease;
    }
}
