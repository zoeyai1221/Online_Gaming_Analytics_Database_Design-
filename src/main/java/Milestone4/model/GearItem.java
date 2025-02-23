package Milestone4.model;

/**
 * Represents a Gear, a specific type of User applying to a program (Masters's or PhD).
 * This class adds program type and essay fields to the basic user details provided by the User superclass.
 */
public class GearItem extends Item {
	private int itemLevel;
	private OptionalSlot optionalSlot;
	private int requiredLevel;
	private int defense;
	private int magicDefense;

    // Constructor to initialize a Gear with all fields, including itemID
    public GearItem(int itemID, String itemName, int maxStackSize, Double vendorPrice,
    		boolean isSellable, int itemLevel, OptionalSlot optionalSlot, int requiredLevel,
    		int defense, int magicDefense, ItemType itemType) {
        super(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
        this.itemLevel = itemLevel;
        this.optionalSlot = optionalSlot;
        this.requiredLevel = requiredLevel;
        this.defense = defense;
        this.magicDefense = magicDefense;
    }

    // Constructor to initialize a Gear with only itemID
 	public GearItem(int itemID) {
 		super(itemID);
 	}
 	
 	// Constructor to initialize a Gear without a itemID
 	public GearItem(String itemName, int maxStackSize, Double vendorPrice, boolean isSellable,
 			int itemLevel, OptionalSlot optionalSlot, int requiredLevel, int defense, int magicDefense, ItemType itemType) {
        super(itemName, maxStackSize, vendorPrice, isSellable, itemType);
        this.itemLevel = itemLevel;
        this.optionalSlot = optionalSlot;
        this.requiredLevel = requiredLevel;
        this.defense = defense;
        this.magicDefense = magicDefense;
    }

	/** Getters and setters. */
	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public OptionalSlot getOptionalSlot() {
		return optionalSlot;
	}

	public void setOptionalSlot(OptionalSlot optionalSlot) {
		this.optionalSlot = optionalSlot;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getMagicDefense() {
		return magicDefense;
	}

	public void setMagicDefense(int magicDefense) {
		this.magicDefense = magicDefense;
	}	
}
