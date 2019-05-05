package model.Buff;

import model.Cards.SoldierCard;

public abstract class Buff {
    private Kind kind;
    private int duration;
    private boolean isContinuous;
    private int numberOfUsage;
    private boolean onMoment;
    private int currnetTurn;
    private int castTurn;

    public Buff(Kind kind, int duration, boolean isContinuous) {
        this.kind = kind;
        this.duration = duration;
        this.isContinuous = isContinuous;
        this.numberOfUsage = 0;
        this.onMoment = false;
        this.currnetTurn = 1;
        this.castTurn = 1;
    }

    public void increaseCurrentTurn() {
        this.currnetTurn++;
    }

    public void setCastTurn(int castTurn) {
        this.castTurn = castTurn;
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

    public boolean isOnMoment() {
        return onMoment;
    }

    public void setOnMoment(boolean onMoment) {
        this.onMoment = onMoment;
    }

    public int getCurrnetTurn() {
        return currnetTurn;
    }

    public int getCastTurn() {
        return castTurn;
    }
}