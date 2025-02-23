package Milestone4.model;

/**
 * InventorySlot represents a slot in a character's inventory, including its position, 
 * the item it holds, and the quantity of the item.
 */

public class InventorySlot {
    private int inventorySlotID;
    private Character character; // Reference to Character (FK)
    private int position;
    private Item item; // Reference to Item (FK)
    private int quantity;

    public InventorySlot(int inventorySlotID, Character character, int position, Item item, int quantity) {
        this.inventorySlotID = inventorySlotID;
        this.character = character;
        this.position = position;
        this.item = item;
        this.quantity = quantity;
    }

    public InventorySlot(Character character, int position, Item item, int quantity) {
        this.character = character;
        this.position = position;
        this.item = item;
        this.quantity = quantity;
    }

    public InventorySlot(int inventorySlotID) {
        this.inventorySlotID = inventorySlotID;
    }

    /** Getters and Setters */

    public int getInventorySlotID() {
        return inventorySlotID;
    }

    public void setInventorySlotID(int inventorySlotID) {
        this.inventorySlotID = inventorySlotID;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}