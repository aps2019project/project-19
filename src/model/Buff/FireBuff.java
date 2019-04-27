package model.Buff;

import model.Cards.SoldierCard;

public class FireBuff extends Buff {
    private int hpDecrease;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(-this.hpDecrease);
    }

    public FireBuff(Kind kind, int duration, boolean isContinuous, int hpDecrease){
        super(kind, duration, isContinuous);
        this.hpDecrease = hpDecrease;
    }
}
