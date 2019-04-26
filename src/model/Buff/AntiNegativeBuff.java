package model.Buff;

import model.Cards.SoldierCard;

public class AntiNegativeBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public AntiNegativeBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
