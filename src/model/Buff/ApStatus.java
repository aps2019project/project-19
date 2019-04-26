package model.Buff;

import model.Cards.SoldierCard;

public class ApStatus extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public ApStatus(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
