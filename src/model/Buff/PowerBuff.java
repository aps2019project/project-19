package model.Buff;

import model.Cards.SoldierCard;

public class PowerBuff extends Buff {
    private int hpIncrease;
    private int apIncrease;
    private boolean hasCasted;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(this.hpIncrease);
        soldier.changeAp(this.apIncrease);
    }

    public void cancelEffect(SoldierCard soldier) {
        soldier.changeHp(-this.hpIncrease);
        soldier.changeAp(-this.apIncrease);
    }

    public PowerBuff(Kind kind, int duration, boolean isContinuous, int hpIncrease, int apIncrease) {
        super(kind, duration, isContinuous);
        this.hpIncrease = hpIncrease;
        this.apIncrease = apIncrease;
        this.hasCasted = false;
        this.setOnMoment(true);
    }

    public void setHasCasted(boolean hasCasted) {
        this.hasCasted = hasCasted;
    }

    public boolean isHasCasted() {
        return hasCasted;
    }
}
