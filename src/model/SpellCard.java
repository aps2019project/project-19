package model;

public class SpellCard extends Card{
    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    private Spell spell;
}
