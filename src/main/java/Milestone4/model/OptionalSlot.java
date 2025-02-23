package Milestone4.model;

public class OptionalSlot extends EquipmentSlot {
	
    public OptionalSlot(int equipmentSlotID, SlotName slotName, boolean isMandatory) {
        super(equipmentSlotID, slotName, isMandatory);
    }
    
    public OptionalSlot(int equipmentSlotID) {
    	super(equipmentSlotID);
    }
    
    public OptionalSlot(SlotName slotName, boolean isMandatory) {
    	super(slotName, isMandatory);
    }
}