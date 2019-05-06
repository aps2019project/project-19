package model.Target;

import model.Cell;
import model.Player;

public class Target {
    private Type type;
    private int areaSize;
    private SoldierTargetType soldierTargetType;

    public Target(Type type, int areaSize, SoldierTargetType soldierTargetType) {
        this.type = type;
        this.areaSize = areaSize;
        this.soldierTargetType = soldierTargetType;
    }

    public Type getType() {
        return type;
    }

    public int getAreaSize() {
        return areaSize;
    }

    public SoldierTargetType getSoldierTargetType() {
        return soldierTargetType;
    }

    public boolean checkTargetValidation(Cell[][] cells, Player activePlayer, Player deactivePlayer, int x, int y) {
        return true;
    }
}
