package model.Game;

public class SinglePlayerGame extends Game {
    private GameKind gameKind;

    public GameKind getGameKind() {
        return gameKind;
    }

    public void setGameKind(GameKind gameKind) {
        this.gameKind = gameKind;
    }
}
