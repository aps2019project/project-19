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

    public Buff(Buff buff) {
        this.kind = buff.kind;
        this.duration = buff.duration;
        this.isContinuous = buff.isContinuous;
        this.numberOfUsage = 0;
        this.onMoment = buff.onMoment;
        this.currentTurn = 1;
        this.castTurn = 1;
        this.isForCell = buff.isForCell;
    }

    public static Buff getNewBuff(Buff buff) {
        if (buff instanceof AttackBuff)
            return new AttackBuff((AttackBuff) buff);
        if (buff instanceof DisArmBuff)
            return new DisArmBuff(buff);
        if (buff instanceof DispellBuff)
            return new DispellBuff((DispellBuff) buff);
        if (buff instanceof FireBuff)
            return new FireBuff((FireBuff) buff);
        if (buff instanceof HolyBuff)
            return new HolyBuff((HolyBuff) buff);
        if (buff instanceof KillBuff)
            return new KillBuff(buff);
        if (buff instanceof PoisonBuff)
            return new PoisonBuff((PoisonBuff) buff);
        if (buff instanceof PowerBuff)
            return new PowerBuff((PowerBuff) buff);
        if (buff instanceof StunBuff)
            return new StunBuff(buff);
        if (buff instanceof WeaknessBuff)
            return new WeaknessBuff((WeaknessBuff) buff);
        return null;
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