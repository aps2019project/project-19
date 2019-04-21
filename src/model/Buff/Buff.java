package model.Buff;

import model.SoldierCard;

public abstract class Buff {
    private Kind kind;
    private int duration;
    private boolean isContinuous;

    public void setContinuous(boolean continuous) {
        isContinuous = continuous;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public abstract void castBuff(SoldierCard soldier);
}