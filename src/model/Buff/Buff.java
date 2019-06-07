package model.Buff;

import model.Cards.SoldierCard;

public abstract class Buff {
    private Kind kind;
    private int duration;
    private boolean isContinuous;
    private int numberOfUsage;
    private boolean onMoment;
    private int currentTurn;
    private int castTurn;
    private boolean isForCell;

    public Buff(Kind kind, int duration, boolean isContinuous) {
        this.kind = kind;
        this.duration = duration;
        this.isContinuous = isContinuous;
        this.numberOfUsage = 0;
        this.onMoment = false;
        this.currentTurn = 1;
        this.castTurn = 1;
        this.isForCell = false;
    }

    public void setForCell(boolean forCell) {
        isForCell = forCell;
    }

    public boolean isForCell() {
        return isForCell;
    }

    public void increaseCurrentTurn() {
        this.currentTurn++;
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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getCastTurn() {
        return castTurn;
    }
}