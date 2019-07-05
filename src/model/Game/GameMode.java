package model.Game;

public enum GameMode {
    DEATH_MATCH,
    CAPTURE_THE_FLAGS,
    KEEP_THE_FLAG;

    @Override
    public String toString() {
        if (this.equals(DEATH_MATCH))
            return "Death Match";
        if (this.equals(CAPTURE_THE_FLAGS))
            return "capture The Flags";
        if (this.equals(KEEP_THE_FLAG))
            return "keep The Flag";
        return null;
    }
}
