package model.Buff;

import model.Cards.SoldierCard;

public abstract class Buff {
    private Kind kind;
    private int duration;
    private boolean isContinuous;
    private int numberOfUsage;

    public Buff(Kind kind, int duration, boolean isContinuous) {
        this.kind = kind;
        this.duration = duration;
        this.isContinuous = isContinuous;
        this.numberOfUsage = 0;
    }

    public int getNumberOfUsage() {
        return numberOfUsage;
    }

    public void increaseNumberOfUsage() {
        this.numberOfUsage++;
    }

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