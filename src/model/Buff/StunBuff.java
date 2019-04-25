package model.Buff;

import model.SoldierCard;

public class StunBuff extends Buff{
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public StunBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
