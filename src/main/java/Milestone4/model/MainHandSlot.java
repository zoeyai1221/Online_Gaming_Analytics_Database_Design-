package Milestone4.model;

public class MainHandSlot extends EquipmentSlot {

    public MainHandSlot(int equipmentSlotID, SlotName slotName, boolean isMandatory) {
        super(equipmentSlotID, slotName, isMandatory);
    }

    public MainHandSlot(SlotName slotName, boolean isMandatory) {
        super(slotName, isMandatory);
    }
    
    public MainHandSlot(int equipmentSlotID) {
    	super(equipmentSlotID);
    }
    
}