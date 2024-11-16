package pm3.model;

public class OptionalSlot extends EquipmentSlot {

    // Constructor
    public OptionalSlot(int equipmentSlotID, String slotName, boolean isMandatory) {
        super(equipmentSlotID, slotName, isMandatory);
    }
    
    

    // Additional methods or properties specific to OptionalSlot can go here
    @Override
    public String toString() {
        return "OptionalSlot{" + super.toString() + "}";
    }
}

