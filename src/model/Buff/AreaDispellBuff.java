package model.Buff;

import model.SoldierCard;

public class AreaDispellBuff extends Buff {

    @Override
    public void castBuff(SoldierCard soldier) {

    }

    public AreaDispellBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }
}
