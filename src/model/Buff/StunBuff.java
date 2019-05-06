package model.Buff;

import model.Cards.SoldierCard;

public class StunBuff extends Buff{
    @Override
    public void castBuff(SoldierCard soldier) {
        this.increaseNumberOfUsage();
    }

    public StunBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
