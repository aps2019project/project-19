package model.Cards;

import controller.CardInitializer;
import model.AbilityCastTime;
import model.Buff.Buff;
import model.Shop;
import model.Target.Target;

import java.util.ArrayList;

public class CustomCard {
    private String name;
    private String type;
    private Target spellTarget;
    private ArrayList<Buff> spellBuffs = new ArrayList<>();
    private int ap;
    private int hp;
    private SoldierTypes attackType;
    private int attackRange = 0;
    private ArrayList<Buff> specialPowers = new ArrayList<>();
    private AbilityCastTime abilityCastTime;
    private Target heroTarget;
    private int coolDown;
    private int cost;
    private int mana;
    private String desc;

//    public Card createCard() {
//        switch (type) {
//            case "minion":
//                Minion minion = new Minion(Shop.getNewId(), name, cost, mana, ap, hp,
//                        attackType, attackRange, desc, abilityCastTime, specialPowers);
//                CardInitializer.addCustomCardToFile(minion);
//                return minion;
//            case "hero":
//                Hero hero = new Hero(Shop.getNewId(), name, cost, mana, ap, hp,
//                        attackType, attackRange, desc, specialPowers.get(0), coolDown, heroTarget);
//                CardInitializer.addCustomCardToFile(hero);
//                return hero;
//            case "spell":
//                SpellCard spell = new SpellCard(Shop.getNewId(), name, cost, mana, desc, spellBuffs, spellTarget);
//                CardInitializer.addCustomCardToFile(spell);
//                return spell;
//        }
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpellTarget(Target spellTarget) {
        this.spellTarget = spellTarget;
    }

    public void setSpellBuffs(ArrayList<Buff> spellBuffs) {
        this.spellBuffs = spellBuffs;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttackType(SoldierTypes attackType) {
        this.attackType = attackType;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setSpecialPowers(ArrayList<Buff> specialPowers) {
        this.specialPowers = specialPowers;
    }

    public void setAbilityCastTime(AbilityCastTime abilityCastTime) {
        this.abilityCastTime = abilityCastTime;
    }

    public void setHeroTarget(Target heroTarget) {
        this.heroTarget = heroTarget;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}