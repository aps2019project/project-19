package model.Buff;

import model.Cards.SoldierCard;

public class DispellBuff extends Buff{

    public DispellBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
    }

    @Override
    public void castBuff(SoldierCard soldier) {

    }
}
