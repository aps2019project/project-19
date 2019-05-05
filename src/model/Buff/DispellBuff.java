package model.Buff;

import model.Cards.SoldierCard;

import java.util.ArrayList;

public class DispellBuff extends Buff {

    public DispellBuff(Kind kind, int duration, boolean isContinuous) {
        super(kind, duration, isContinuous);
        this.setOnMoment(true);
    }

    @Override
    public void castBuff(SoldierCard soldier) {
        ArrayList<Buff> buffs = soldier.getBuffs();
        for (int i = buffs.size() - 1; i >= 0; i--) {
            if (buffs.get(i).getKind().equals(Kind.NEGATIVE)) {
                if (buffs.get(i) instanceof WeaknessBuff) {
                    ((WeaknessBuff) buffs.get(i)).cancelEffect(soldier);
                }
                buffs.remove(i);
            }
        }
    }
}
