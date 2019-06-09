package model.Buff;

import model.Cards.SoldierCard;

public class KillBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.setHp(0);
        increaseNumberOfUsage();
    }

    public KillBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
        this.setOnMoment(true);
    }

    public KillBuff(Buff buff) {
        super(buff);
    }
}
