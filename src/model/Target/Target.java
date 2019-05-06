package model.Target;

import model.Cards.Minion;
import model.Cell;
import model.Game.Game;
import model.Player;

public class Target {
    private Type type;
    private int areaSize;
    private SoldierTargetType soldierTargetType;
    private boolean isaffectingCell;

    public Target(Type type, int areaSize, SoldierTargetType soldierTargetType) {
        this.type = type;
        this.areaSize = areaSize;
        this.soldierTargetType = soldierTargetType;
        this.isaffectingCell = false;
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

    public boolean isIsaffectingCell() {
        return isaffectingCell;
    }

    public void setIsaffectingCell(boolean isaffectingCell) {
        this.isaffectingCell = isaffectingCell;
    }

    public boolean checkTargetValidation(Game game, Player activePlayer, Player deactivePlayer, int x, int y) {
        if (this.getType().equals(Type.SOLDIER)) {
            switch (this.getSoldierTargetType()) {
                //case ALL_FRIENDLY_MINIONS_AROUND_AND_ITSELF:
                //case ALL_MINIONS_TO_2_CELLS_FURTHER:
                // case ALL_MINIONS_AROUND:
                case ONE_FRIENDLY_SOLDIER:
                    return (game.getCell(x, y) != null) && (game.getCell(x, y).getCard() != null) &&
                            (activePlayer.getInBattleCards().containsKey(game.getCell(x, y).getCard()));
                case ONE_FRIENDLY_MINION:
                    return (game.getCell(x, y) != null) && (game.getCell(x, y).getCard() != null) &&
                            (activePlayer.getInBattleCards().containsKey(game.getCell(x, y).getCard())) &&
                            (game.getCell(x, y).getCard() instanceof Minion);
                case ONE_ENEMY_MINION:
                    return (game.getCell(x, y) != null) && (game.getCell(x, y).getCard() != null) &&
                            (game.getCell(x, y).getCard() instanceof Minion) &&
                            (deactivePlayer.getInBattleCards().containsKey(game.getCell(x, y).getCard()));
                case ONE_SOLDIER:
                    return (game.getCell(x, y) != null) && (game.getCell(x, y).getCard() != null);
                case ONE_ENEMY:
                    return (game.getCell(x, y) != null) &&
                            (deactivePlayer.getInBattleCards().containsKey(game.getCell(x, y).getCard()));
            }
        }
        return true;
    }
}
