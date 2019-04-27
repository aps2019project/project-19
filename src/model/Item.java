package model;

import javax.imageio.event.IIOReadProgressListener;
import java.util.ArrayList;

import model.Buff.Buff;
import model.Target.Target;
import model.Target.Type;

public class Item {
    private int itemId;
    private String name;
    private int price;
    private Cell cell;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ItemTypes type;
    private Target target;

    public Item() {
    }

    public Item(Item item) {
        this.itemId = item.itemId;
        this.name = item.name;
        this.price = item.price;
        this.type = item.type;
        this.buffs = item.buffs;
        this.target = item.target;
    }

    public Item(int itemId, String name, int price, ItemTypes type, ArrayList<Buff> buffs, Target target){
        this.itemId =itemId;
        this.name = name.toLowerCase();
        this.price = price;
        this.type = type;
        this.buffs = buffs;
        this.target = target;
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

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
