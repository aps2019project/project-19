package model.Buff;

import model.SoldierCard;

public class PowerBuff extends Buff{
    private int hpIncrease;
    private int apIncrease;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.hpIncrease);
        soldier.changeAp(this.apIncrease);
    }

    public PowerBuff(Kind kind, int duration, boolean isContinuous, int hpIncrease, int apIncrease) {
        super(kind, duration, isContinuous);
        this.hpIncrease = hpIncrease;
        this.apIncrease = apIncrease;
    }
}
