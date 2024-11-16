package Milestone2.model;

public class OptionalSlot extends EquipmentSlot {

    public OptionalSlot(int equipmentSlotID, String slotName, boolean isMandatory) {
        super(equipmentSlotID, slotName, isMandatory);
    }
    
    public OptionalSlot(int equipmentSlotID) {
    	super(equipmentSlotID);
    }
}