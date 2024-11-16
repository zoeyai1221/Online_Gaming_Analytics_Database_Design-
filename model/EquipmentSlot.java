package pm3.model;

public class EquipmentSlot {
    protected int equipmentSlotID;
    protected String slotName;
    protected boolean isMandatory;

    // Constructor
    public EquipmentSlot(int equipmentSlotID, String slotName, boolean isMandatory) {
        this.equipmentSlotID = equipmentSlotID;
        this.slotName = slotName;
        this.isMandatory = isMandatory;
    }

    // Getters and Setters
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

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    @Override
    public String toString() {
        return "EquipmentSlot{" +
                "equipmentSlotID=" + equipmentSlotID +
                ", slotName='" + slotName + '\'' +
                ", isMandatory=" + isMandatory +
                '}';
    }
}
