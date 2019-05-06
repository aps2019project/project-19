package model.Buff;

import model.Cards.SoldierCard;

public class AttackBuff extends Buff {
    private int damage;

    @Override
    public void castBuff(SoldierCard soldier) {
        soldier.changeHp(-this.damage);
        increaseNumberOfUsage();
    }

    public AttackBuff(Kind kind, int duration, boolean isContinuous, int damage) {
        super(kind, duration, isContinuous);
        this.damage = damage;
        this.setOnMoment(true);
    }
}