package model;

import model.Buff.Buff;
import model.Target.Target;

import java.util.ArrayList;

public class SpellCard extends Card {
    private ArrayList<Buff> buffs = new ArrayList<>();
    private Target targetArea;

    public SpellCard(int cardId, String name, int price, int mana, ArrayList<Buff> buffs, Target targetArea) {
        super(cardId, name, price, mana);
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
}
