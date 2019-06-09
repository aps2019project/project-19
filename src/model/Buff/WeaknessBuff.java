package model.Buff;

import model.Cards.SoldierCard;

public class WeaknessBuff extends Buff {
    private int hpDecrease;
    private int apDecrease;
    private boolean hasCasted;

    public WeaknessBuff(WeaknessBuff weaknessBuff) {
        super(weaknessBuff);
        this.hpDecrease = weaknessBuff.hpDecrease;
        this.apDecrease = weaknessBuff.apDecrease;
        this.hasCasted = weaknessBuff.hasCasted;
    }

    @Override
    public void castBuff(SoldierCard soldier) {
        if (!hasCasted) {
            soldier.changeAp(-this.apDecrease);
            soldier.changeHp(-this.hpDecrease);
            hasCasted = true;
            this.increaseNumberOfUsage();
        }
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
