package model;

public class Buff {
    private String name;
    private Effect effect;

    public String getName() {
        return name;
    }

    public Effect getKind() {
        return effect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(Effect effect) {
        this.effect = effect;
    }
}

enum Effect {
    POSITIVE, NEGATIVE
}
