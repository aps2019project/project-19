package model.Buff;

import model.SoldierCard;

public class ApStatus extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public ApStatus(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
