package model;

import model.Buff.Buff;

import java.util.ArrayList;

public class SoldierCard extends Card {
    private int ap;
    private int hp;
    private Cell cell;
    private SoldierTypes type;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private int attackRange;

    public SoldierCard() {
        super();
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
}
