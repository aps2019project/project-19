package model.Buff;

import model.Cards.SoldierCard;

public class PoisonBuff extends Buff {
    private int hpDecrease;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeAp(-this.hpDecrease);
        this.increaseNumberOfUsage();
    }

    public PoisonBuff(Kind kind, int duration, boolean isContinuous, int hpDecrease) {
        super(kind, duration, isContinuous);
        this.hpDecrease = hpDecrease;
    }
}
