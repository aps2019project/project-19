package model.Buff;

import model.SoldierCard;

public class WeaknessBuff extends Buff {
    private int hpDecrease;
    private int apDecrease;
    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeAp(-this.apDecrease);
        soldier.changeHp(-this.hpDecrease);
    }
}
