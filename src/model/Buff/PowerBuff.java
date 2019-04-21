package model.Buff;

import model.SoldierCard;

public class PowerBuff extends Buff{
    private int hpIncrease;
    private int apIncrease;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.hpIncrease);
        soldier.changeAp(this.apIncrease);
    }
}
