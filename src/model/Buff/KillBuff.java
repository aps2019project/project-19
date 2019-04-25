package model.Buff;

import model.SoldierCard;

public class KillBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public KillBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
