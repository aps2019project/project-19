package model;

import javax.imageio.event.IIOReadProgressListener;

public class Item {
    private int itemId;
    private String name;
    private int price;
    private Cell cell;
    private ItemTypes type;

    public Item(){}

    public Item(Item item){
        this.name = item.name;
        this.price = item.price;
        this.type = item.type;
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
}
