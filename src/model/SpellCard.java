package model;

import model.Buff.Buff;
import model.Target.Target;

public class SpellCard extends Card {
    private Buff buff;
    private Target targetArea;

    public SpellCard() {
        super();
    }

    public SpellCard(SpellCard spellCard) {
        super(spellCard);
        this.buff = spellCard.buff;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public void setTargetArea(Target targetArea) {
        this.targetArea = targetArea;
    }

    public Target getTargetArea() {
        return targetArea;
    }
}
