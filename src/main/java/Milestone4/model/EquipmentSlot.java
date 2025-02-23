package Milestone4.model;

public class EquipmentSlot {
	private int equipmentSlotID;
	private SlotName slotName;
	private boolean isMandatory;
	
	public enum SlotName {
        mainHand, head, body, hands, legs, feet, offhand, earring, wrist, ring
    }
	
	// constructor with all attributes
    public EquipmentSlot(int equipmentSlotID, SlotName slotName, boolean isMandatory) {
        this.equipmentSlotID = equipmentSlotID;
        this.slotName = slotName;
        this.isMandatory = isMandatory;
    }
    
    public EquipmentSlot(SlotName slotName, boolean isMandatory) {
        this.slotName = slotName;
        this.isMandatory = isMandatory;
    }
    
    // constructor with primary key only
    public EquipmentSlot(int equipmentSlotID) {
    	this.equipmentSlotID = equipmentSlotID;
    }

    public int getEquipmentSlotID() {
        return equipmentSlotID;
    }

    public void setEquipmentSlotID(int equipmentSlotID) {
        this.equipmentSlotID = equipmentSlotID;
    }

    public SlotName getSlotName() {
        return slotName;
    }
    public void setSlotName(SlotName slotName) {
        this.slotName = slotName;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }
}
