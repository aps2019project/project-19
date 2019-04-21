package model.Buff;

import model.SoldierCard;

public class PoisonBuff extends Buff {
    private int hpDecrease;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeAp(-this.hpDecrease);
    }
}
