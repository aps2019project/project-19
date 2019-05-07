package model.Cards;

import model.AbilityCastTime;
import model.Buff.*;
import model.Cell;
import model.Target.Target;

import java.util.ArrayList;

public abstract class SoldierCard extends Card {
    private int ap;
    private int hp;
    private SoldierTypes type;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private int flagNumber;
    private int attackRange;
    private boolean isAttackedThisTurn;
    private boolean isMovedThisTurn;
    private boolean antiDisArm;
    private boolean antiPoison;
    private boolean antiNegative;
    private boolean strongerAp;
    private boolean notAcceptingHolyBuff;
    private Target target;
    private AbilityCastTime abilityCastTime;

    public SoldierCard() {
        super();
    }

    public SoldierCard(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, String description) {
        super(cardId, name, price, mana, description);
        this.ap = ap;
        this.hp = hp;
        this.type = type;
        this.attackRange = attackRange;
        antiDisArm = false;
        antiPoison = false;
        antiNegative = false;
        strongerAp = false;
    }

    public SoldierCard(SoldierCard soldierCard) {
        super(soldierCard);
        this.ap = soldierCard.ap;
        this.hp = soldierCard.hp;
        this.type = soldierCard.type;
        this.attackRange = soldierCard.attackRange;
        this.target = soldierCard.target;
        this.abilityCastTime = soldierCard.getAbilityCastTime();
    }

    public AbilityCastTime getAbilityCastTime() {
        return abilityCastTime;
    }

    public void setAbilityCastTime(AbilityCastTime abilityCastTime) {
        this.abilityCastTime = abilityCastTime;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setNotAcceptingHolyBuff(boolean notAcceptingHolyBuff) {
        this.notAcceptingHolyBuff = notAcceptingHolyBuff;
    }

    public boolean isNotAcceptingHolyBuff() {
        return notAcceptingHolyBuff;
    }

    public boolean isAntiDisArm() {
        return antiDisArm;
    }

    public boolean isAntiPoison() {
        return antiPoison;
    }

    public boolean isAntiNegative() {
        return antiNegative;
    }

    public boolean isStrongerAp() {
        return strongerAp;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getHp() {
        return hp;
    }

    public void decreaseHP(int amount) {
        this.hp -= amount;
    }

    public SoldierTypes getType() {
        return type;
    }

    public void setType(SoldierTypes type) {
        this.type = type;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getFlagNumber() {
        return flagNumber;
    }

    public void setFlagNumber(int flagNumber) {
        this.flagNumber = flagNumber;
    }

    public void changeAp(int number) {
        this.ap += number;
    }

    public void changeHp(int number) {
        this.hp += number;
    }

    public void setAntiDisArm(boolean antiDisArm) {
        this.antiDisArm = antiDisArm;
    }

    public void setAntiPoison(boolean antiPoison) {
        this.antiPoison = antiPoison;
    }

    public void setAntiNegative(boolean antiNegative) {
        this.antiNegative = antiNegative;
    }

    public void setStrongerAp(boolean strongerAp) {
        this.strongerAp = strongerAp;
    }

    public boolean isAttackedThisTurn() {
        return isAttackedThisTurn;
    }

    public SoldierCard setAttackedThisTurn(boolean attackedThisTurn) {
        isAttackedThisTurn = attackedThisTurn;
        return this;
    }

    public boolean isMovedThisTurn() {
        return isMovedThisTurn;
    }

    public SoldierCard setMovedThisTurn(boolean movedThisTurn) {
        isMovedThisTurn = movedThisTurn;
        return this;
    }

    public void counterAttack(SoldierCard target) {
        if (!this.isAntiDisArm() || !this.isDirarmed()) {
            target.decreaseHP(this.getAp());
            if (this.getAbilityCastTime() != null && this.abilityCastTime.equals(AbilityCastTime.ON_DEFEND)) {
                if (this instanceof Minion) {
                    castBuffsOnTarget(this, target);
                } else {
                    castBuffsOnTargetWithTargets(this, target);
                }
                castOnMomentBUffs(target);
                checkBUffTiming(target, false);
            }
        }
    }


    public void attack(SoldierCard target) {
        if (!target.isAntiNegative()) {
            boolean hasAttacked = true;
            if (!target.isStrongerAp()) {
                target.decreaseHP(this.getAp());
            } else {
                if (this.getAp() > target.getAp()) {
                    target.decreaseHP(this.getAp());
                } else {
                    hasAttacked = false;
                }
            }
            //casting buff
            if (hasAttacked) {
                checkHolyBuff(target);
                if (this.getAbilityCastTime() != null && this.getAbilityCastTime().equals(AbilityCastTime.ON_ATTACK)) {
                    if (this instanceof Hero) {
                        castBuffsOnTargetWithTargets(this, target);
                    } else {
                        castBuffsOnTarget(this, target);
                    }
                    castOnMomentBUffs(target);
                    checkBUffTiming(target, false);
                }
            }
        }
    }

    private void castBuffsOnTargetWithTargets(SoldierCard hero, SoldierCard target) {
        switch (hero.getTarget().getSoldierTargetType()) {
            case FRIENDLY_HERO:
                hero.addBuffToTarget(((Hero) hero).getSpecialPower(), hero);
                break;
            case ONE_ENEMY:
                hero.addBuffToTarget(((Hero) hero).getSpecialPower(), target);
                break;
        }
    }

    public void castFirstTurnBuffs() {
        for (Buff buff : this.getBuffs()) {
            if (buff.getCurrnetTurn() == buff.getCastTurn() && buff.getDuration() > buff.getNumberOfUsage()) {
                buff.castBuff(this);
            }
            if (buff instanceof KillBuff) {
                break;
            }
        }
    }

    public void checkHolyBuff(SoldierCard target) {
        for (Buff buff : target.getBuffs()) {
            if (buff instanceof HolyBuff) {
                buff.castBuff(target);
            }
        }
    }

    public void checkBUffTiming(SoldierCard soldier, boolean isEndOfTurn) {
        for (int i = soldier.getBuffs().size() - 1; i >= 0; i--) {
            Buff buff = soldier.getBuffs().get(i);
            if (buff instanceof StunBuff || buff instanceof DisArmBuff) {
                if (buff.getDuration() + 1 == buff.getCurrnetTurn()) {
                    soldier.getBuffs().remove(i);
                } else {
                    buff.increaseCurrentTurn();
                }
            } else if (buff.getNumberOfUsage() == buff.getDuration()) {
                if (buff instanceof WeaknessBuff) {
                    ((WeaknessBuff) buff).cancelEffect(soldier);
                }
                if (buff.getKind().equals(Kind.NEGATIVE)) {
                    soldier.getBuffs().remove(i);
                }
            } else if (isEndOfTurn) {
                buff.increaseCurrentTurn();
            }
        }
    }

    public void castOnMomentBUffs(SoldierCard soldier) {
        for (Buff buff : soldier.getBuffs()) {
            if (buff.getCastTurn() == buff.getCurrnetTurn() &&
                    buff.isOnMoment() && buff.getDuration() > buff.getNumberOfUsage()) {
                buff.castBuff(soldier);
            }
            if (buff instanceof KillBuff) {
                break;
            }
        }
    }

    public void castBuffsOnTarget(SoldierCard attacker, SoldierCard target) {
        if (attacker instanceof Minion) {
            for (Buff buff : ((Minion) attacker).getAbilities()) {
                addBuffToTarget(buff, target);
            }
        }
        if (attacker instanceof Hero) {
            addBuffToTarget(((Hero) attacker).getSpecialPower(), target);
        }
    }

    public void addBuffToTarget(Buff buff, SoldierCard target) {
        boolean isPermitted = true;
        if (buff instanceof PoisonBuff && target.isAntiPoison()) {
            isPermitted = false;
        } else if (buff instanceof HolyBuff && target.isNotAcceptingHolyBuff()) {
            isPermitted = false;
        }
        if (target.isAntiNegative()) {
            isPermitted = false;
        }
        if (isPermitted) {
            target.getBuffs().add(buff);
        }
    }

    public boolean isDirarmed() {
        for (Buff buff : this.getBuffs()) {
            if (buff instanceof DisArmBuff)
                return true;
        }
        return false;
    }

    public String toBattleFormat(int x, int y) {
        return getInBattleCardId() + " : "
                + getName() + ", "
                + "health : " + getHp() + ", "
                + "location : " + "(" + x + ", " + y + "), "
                + "Power : " + getAp();
    }

    public abstract String toInfoString();

    public boolean targetIsInRange(Cell attackerCell, Cell targetCell) {
        switch (this.getType()) {
            case MELEE:
                return (Math.abs(attackerCell.getYCoordinate() - targetCell.getYCoordinate()) <= 1 &&
                        Math.abs(attackerCell.getXCoordinate() - targetCell.getXCoordinate()) <= 1);
            case RANGED:
                if (Math.abs(attackerCell.getYCoordinate() - targetCell.getYCoordinate()) <= 1 &&
                        Math.abs(attackerCell.getXCoordinate() - targetCell.getXCoordinate()) <= 1)
                    return false;
                else
                    return (Math.abs(attackerCell.getYCoordinate() - targetCell.getYCoordinate()) <= this.getAttackRange() &&
                            Math.abs(attackerCell.getXCoordinate() - targetCell.getXCoordinate()) <= this.getAttackRange());
            case HYBRID:
                return (Math.abs(attackerCell.getYCoordinate() - targetCell.getYCoordinate()) <= this.getAttackRange() &&
                        Math.abs(attackerCell.getXCoordinate() - targetCell.getXCoordinate()) <= this.getAttackRange());
        }
        return false;
    }

    public void pickUpFlags(Cell cell) {
        flagNumber += cell.getFlagNumber();
        cell.setFlagNumber(0);
    }

    public void dropFlags(Cell cell) {
        cell.setFlagNumber(this.flagNumber);
        this.setFlagNumber(0);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
