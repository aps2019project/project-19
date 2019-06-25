package model.Cards;

import controller.CardInitializer;
import model.AbilityCastTime;
import model.Buff.Buff;
import model.Shop;
import model.Target.SoldierTargetType;
import model.Target.Target;
import model.Target.Type;

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
    private Type targetType;
    private int areaSize = 0;
    private SoldierTargetType soldierTargetType;

    public Card createCard() {
        switch (type) {
            case "minion":
                if (!attackType.equals(SoldierTypes.RANGED))
                    attackRange = 0;
                Minion minion = new Minion(Shop.getNewId(), name, cost, mana, ap, hp,
                        attackType, attackRange, desc, abilityCastTime, specialPowers);
                //CardInitializer.addCustomCardToFile(minion);
                return minion;
            case "hero":
                if (!attackType.equals(SoldierTypes.RANGED))
                    attackRange = 0;
                if (!targetType.equals(Type.AREA))
                    areaSize = 0;
                heroTarget = new Target(targetType, areaSize, soldierTargetType);
                Hero hero = new Hero(Shop.getNewId(), name, cost, mana, ap, hp,
                        attackType, attackRange, desc, specialPowers.get(0), coolDown, heroTarget);
                //CardInitializer.addCustomCardToFile(hero);
                return hero;
            case "spell":
                if (!targetType.equals(Type.AREA))
                    areaSize = 0;
                spellTarget = new Target(targetType, areaSize, soldierTargetType);
                SpellCard spell = new SpellCard(Shop.getNewId(), name, cost, mana, desc, spellBuffs, spellTarget);
                //CardInitializer.addCustomCardToFile(spell);
                return spell;
        }
        return null;
    }

    public boolean isTargetAvailable() {
        if (targetType != null) {
            if (targetType.equals(Type.SOLDIER) && soldierTargetType != null)
                return true;
            if (targetType.equals(Type.AREA))
                return true;
        }
        return false;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
    }

    public void setAreaSize(int areaSize) {
        this.areaSize = areaSize;
    }

    public void setSoldierTargetType(SoldierTargetType soldierTargetType) {
        this.soldierTargetType = soldierTargetType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpellTarget(Target spellTarget) {
        this.spellTarget = spellTarget;
    }

    public ArrayList<Buff> getSpellBuffs() {
        return spellBuffs;
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

    public ArrayList<Buff> getSpecialPowers() {
        return specialPowers;
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