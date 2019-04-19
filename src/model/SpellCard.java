package model;

public class SpellCard extends Card {
    private Buff buff;

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
}
