package model.Buff;

import model.Cards.SoldierCard;

public class AntiPoisonBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public AntiPoisonBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
