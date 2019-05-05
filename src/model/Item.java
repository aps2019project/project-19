package model;

import java.util.ArrayList;

import model.Buff.Buff;
import model.Target.Target;

public class Item {
    private int itemId;
    private String name;
    private int price;
    private Cell cell;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ItemTypes type;
    private Target target;
    private String decription;

    public Item() {
    }

    public Item(Item item) {
        this.name = item.name;
        this.price = item.price;
        this.type = item.type;
        this.buffs = item.buffs;
        this.target = item.target;
        this.decription = item.decription;
    }

    public Item(int itemId, String name, int price, String description, ItemTypes type, ArrayList<Buff> buffs, Target target) {
        this.itemId = itemId;
        this.name = name.toLowerCase();
        this.price = price;
        this.type = type;
        this.buffs = buffs;
        this.target = target;
        this.decription = description;
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
        return decription;
    }

//    @Override
//    public String toString() {
//        return "Item{" +
//                "itemId=" + itemId +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                '}';
//    }

    @Override
    public String toString() {
        return "Name : " + getName() + " - "
                + "Desc : " + getDescription();
    }

}
