package model.Buff;

import model.Cards.SoldierCard;

public class HolyBuff extends Buff {
    private int lessDamage;
    private boolean hasCasted;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.lessDamage);
    }
    public void cancelEffect(SoldierCard soldier) {
        soldier.changeHp(-this.lessDamage);
    }
    public HolyBuff(Kind kind, int duration, boolean isContinuous, int lessDamage) {
        super(kind, duration, isContinuous);
        this.lessDamage = lessDamage;
        this.hasCasted = false;
    }

    public boolean isHasCasted() {
        return hasCasted;
    }

    public void setHasCasted(boolean hasCasted) {
        this.hasCasted = hasCasted;
    }
}
