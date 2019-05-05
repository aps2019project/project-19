package model.Buff;

import model.Cards.SoldierCard;

public class WeaknessBuff extends Buff {
    private int hpDecrease;
    private int apDecrease;
    private boolean hasCasted;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeAp(-this.apDecrease);
        soldier.changeHp(-this.hpDecrease);
    }

    public void cancelEffect(SoldierCard soldier) {
        soldier.changeAp(this.apDecrease);
        soldier.changeHp(this.hpDecrease);
    }

    public boolean isHasCasted() {
        return hasCasted;
    }

    public void setHasCasted(boolean hasCasted) {
        this.hasCasted = hasCasted;
    }

    public WeaknessBuff(Kind kind, int duration, boolean isContinuous, int hpDecrease, int apDecrease) {
        super(kind, duration, isContinuous);
        this.hpDecrease = hpDecrease;
        this.apDecrease = apDecrease;
        this.hasCasted = false;
        this.setOnMoment(true);
    }
}
