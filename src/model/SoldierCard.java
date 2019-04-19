package model;

public class SoldierCard extends Card {
    private int ap;
    private int hp;
    private Cell cell;
    private SoldierTypes type;

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

    public void attack(Card opponentCard) {
    }

    public void counterAttack(Card opponentCard) {
    }
}
