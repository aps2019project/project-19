package model.Buff;

import model.SoldierCard;

public class HolyBuff extends Buff {
    private int lessDamage;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.lessDamage);
    }
}
