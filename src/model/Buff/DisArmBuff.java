package model.Buff;

import model.SoldierCard;

public class DisArmBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public DisArmBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
