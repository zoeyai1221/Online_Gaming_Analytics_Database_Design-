package pm3.model;

public class MainHandSlot extends EquipmentSlot {

    // Constructor
    public MainHandSlot(int equipmentSlotID, String slotName, boolean isMandatory) {
        super(equipmentSlotID, slotName, isMandatory);
    }

    // Additional methods or properties specific to MainHandSlot can go here
    @Override
    public String toString() {
        return "MainHandSlot{" + super.toString() + "}";
    }
}
