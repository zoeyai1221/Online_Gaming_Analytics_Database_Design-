package Milestone2.model;

public class EquipmentSlot {
	protected int equipmentSlotID;
	protected String slotName;
	protected boolean isMandatory;

	// constructor with all attributes
    public EquipmentSlot(int equipmentSlotID, String slotName, boolean isMandatory) {
        this.equipmentSlotID = equipmentSlotID;
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

    public String getSlotName() {
        return slotName;
    }
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }
}
