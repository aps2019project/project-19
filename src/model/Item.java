package model;

import java.util.ArrayList;

import controller.Controller;
import model.Buff.Buff;
import model.Cards.SoldierCard;
import model.Target.Target;

public class Item {
    private int itemId;
    private String name;
    private int price;
    private Cell cell;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private int hpChanges;
    private int apChanges;
    private ItemTypes type;
    private Target target;
    private String description;
    private WhenToUse whenToUse;
    public Item() {
    }

    public Item(Item item) {
        this.name = item.name;
        this.price = item.price;
        this.type = item.type;
        this.buffs = item.buffs;
        this.apChanges = item.apChanges;
        this.hpChanges = item.hpChanges;
        this.whenToUse = item.whenToUse;
        this.target = item.target;
        this.description = item.description;
    }
    public Item(Item item,int itemId) {
        this.name = item.name;
        this.price = item.price;
        this.type = item.type;
        this.buffs = item.buffs;
        this.target = item.target;
        this.description = item.description;
        this.itemId = itemId;
    }

    public Item(int itemId, String name, int price, String description,
                ItemTypes type,int hpChanges, int apChanges,WhenToUse whenToUse, ArrayList<Buff> buffs, Target target) {
        this.itemId = itemId;
        this.name = name.toLowerCase();
        this.price = price;
        this.type = type;
        this.hpChanges = hpChanges;
        this.apChanges = apChanges;
        this.whenToUse = whenToUse;
        this.buffs = buffs;
        this.target = target;
        this.description = description;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public int getHpChanges() {
        return hpChanges;
    }

    public int getApChanges() {
        return apChanges;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Name : " + getName() + " - "
                + "Desc : " + getDescription();
    }

    public void useCollectable(SoldierCard target) {
        target.getBuffs().addAll(getBuffs());
        target.changeHp(getHpChanges());
        target.changeAp(getApChanges());
    }

}
