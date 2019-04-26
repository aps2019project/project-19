package model.Buff;

import model.Cards.SoldierCard;

public class AntiDisArmBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public AntiDisArmBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
