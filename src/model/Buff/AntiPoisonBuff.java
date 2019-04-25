package model.Buff;

import model.SoldierCard;

public class AntiPoisonBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public AntiPoisonBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
