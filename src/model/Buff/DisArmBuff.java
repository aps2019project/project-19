package model.Buff;

import model.Cards.SoldierCard;

public class DisArmBuff extends Buff {
    @Override
    public void castBuff(SoldierCard soldier) {
    }

    public DisArmBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
    public DisArmBuff(Buff buff){
        super(buff);
    }
}
