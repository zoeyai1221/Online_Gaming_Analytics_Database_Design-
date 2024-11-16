package Milestone2.model;

public class EquippedItem {
    protected int equipmentSlotID;
    protected int characterID;
    protected int itemID;

    // Constructor with primary key only (equipmentSlotID, characterID)
    public EquippedItem(int equipmentSlotID, int characterID) {
        this.equipmentSlotID = equipmentSlotID;
        this.characterID = characterID;
    }

    // Constructor with all attributes
    public EquippedItem(int equipmentSlotID, int characterID, int itemID) {
        this.equipmentSlotID = equipmentSlotID;
        this.characterID = characterID;
        this.itemID = itemID;
    }

    public int getEquipmentSlotID() {
        return equipmentSlotID;
    }

    public void setEquipmentSlotID(int equipmentSlotID) {
        this.equipmentSlotID = equipmentSlotID;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
