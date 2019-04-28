package model.Cards;

import model.Buff.Buff;
import model.Target.Target;
import view.ShowFormat;

import java.util.ArrayList;

public class SpellCard extends Card {
    private ArrayList<Buff> buffs = new ArrayList<>();
    private Target targetArea;

    public SpellCard(int cardId, String name, int price, int mana, String description, ArrayList<Buff> buffs, Target targetArea) {
        super(cardId, name, price, mana, description);
        this.buffs = buffs;
        this.targetArea = targetArea;
    }

    public SpellCard() {
        super();
    }


    public SpellCard(SpellCard spellCard) {
        super(spellCard);
        this.buffs = spellCard.buffs;
        this.targetArea = spellCard.targetArea;
    }

    public ArrayList<Buff> getBuff() {
        return buffs;
    }

    public void setBuff(Buff buff) {
        this.buffs = buffs;
    }

    public void setTargetArea(Target targetArea) {
        this.targetArea = targetArea;
    }

    public Target getTargetArea() {
        return targetArea;
    }

    @Override
    public String toString() {
        return "Type : Spell - "
                + "Name : " + getName() + " - "
                + "MP : " + getMana() + " - "
                + "Desc : " + getDescription();
    }

    public String toInfoString(){
        return "Spell:\n"
                + "Name: " + getName() + "\n"
                + "Mp: " + getMana() + "\n"
                + "Cost: " + getPrice() + "\n"
                + "Desc: " + getDescription();
    }
}
