package model;

import model.Buff.Buff;

public class SpellCard extends Card {
    private Buff buff;

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
}
