package model.Cards;

import model.Buff.Buff;
import model.Cell;

import java.util.ArrayList;

public abstract class SoldierCard extends Card {
    private int ap;
    private int hp;
    private Cell cell;
    private SoldierTypes type;
    private ArrayList<Buff> buffs = new ArrayList<>();
    boolean hasFlag;
    private int attackRange;

    public SoldierCard() {
        super();
    }

    public SoldierCard(int cardId, String name, int price, int mana, int ap, int hp, SoldierTypes type, int attackRange, String description) {
        super(cardId, name, price, mana, description);
        this.ap = ap;
        this.hp = hp;
        this.type = type;
        this.attackRange = attackRange;
    }

    public SoldierCard(SoldierCard soldierCard) {
        super(soldierCard);
        this.ap = soldierCard.ap;
        this.hp = soldierCard.hp;
        this.type = soldierCard.type;
        this.attackRange = soldierCard.attackRange;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
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

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public void changeAp(int number) {
        this.ap += number;
    }

    public void changeHp(int number) {
        this.hp += number;
    }

    public void attack(Card opponentCard) {
    }

    public void counterAttack(Card opponentCard) {
    }

    public String toBattleFormatString() {
        return getInBattleCardId() + " : "
                + getName() + ", "
                + "health : " + getHp() + ", "
                + "location : " + "(" + getCell().getxCoordinate() + ", " + getCell().getyCoordinate() + "), "
                + "Power : " + getAp();
    }

    public abstract String toInfoString();
}
