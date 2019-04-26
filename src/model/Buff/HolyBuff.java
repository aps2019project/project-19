package model.Buff;

import model.Cards.SoldierCard;

public class HolyBuff extends Buff {
    private int lessDamage;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.lessDamage);
    }

    public HolyBuff(Kind kind, int duration, boolean isContinuous, int lessDamage) {
        super(kind, duration, isContinuous);
        this.lessDamage = lessDamage;
    }
}
