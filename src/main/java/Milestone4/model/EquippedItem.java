package Milestone4.model;

public class EquippedItem {
	// pass reference to foreign object 
	
    private EquipmentSlot equipmentSlot;
    private Character character;
    private Item item;

    // Constructor with primary key only (equipmentSlot, character)
    public EquippedItem(EquipmentSlot equipmentSlot, Character character) {
        this.equipmentSlot = equipmentSlot;
        this.character = character;
    }

    // Constructor with all attributes
    public EquippedItem(EquipmentSlot equipmentSlot, Character character, Item itemID) {
        this.equipmentSlot = equipmentSlot;
        this.character = character;
        this.item = itemID;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(EquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item itemID) {
        this.item = itemID;
    }
}
