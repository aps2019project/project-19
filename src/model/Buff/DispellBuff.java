package model.Buff;

import model.Cards.SoldierCard;

import java.util.ArrayList;

public class DispellBuff extends Buff {
    private boolean negative;
    private boolean positive;

    public DispellBuff(Kind kind, int duration, boolean isContinuous, boolean negative, boolean positive) {
        super(kind, duration, isContinuous);
        this.negative = negative;
        this.positive = positive;
        this.setOnMoment(true);
    }

    @Override
    public void castBuff(SoldierCard soldier) {
        if (this.isPositive()) {
            cancelPositiveBuffs(soldier);
        }
        if (this.isNegative()) {
            cancelNegativeBuffs(soldier);
        }
        increaseNumberOfUsage();
    }

    public void cancelNegativeBuffs(SoldierCard soldier) {
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

    public void cancelPositiveBuffs(SoldierCard soldier) {
        ArrayList<Buff> buffs = soldier.getBuffs();
        for (int i = buffs.size() - 1; i >= 0; i--) {
            if (buffs.get(i).getKind().equals(Kind.POSITIVE)) {
                if (buffs.get(i) instanceof PowerBuff) {
                    ((PowerBuff) buffs.get(i)).cancelEffect(soldier);
                }
                if (buffs.get(i) instanceof HolyBuff) {
                    Buff buff = buffs.get(i);
                    if (((HolyBuff) buff).isHasCasted()) {
                        ((HolyBuff) buff).cancelEffect(soldier);
                    }
                }
                if (buffs.get(i).getDuration() == buffs.get(i).getNumberOfUsage()) {
                    buffs.remove(i);
                }
            }
        }
    }

    public boolean isNegative() {
        return negative;
    }

    public boolean isPositive() {
        return positive;
    }
}
