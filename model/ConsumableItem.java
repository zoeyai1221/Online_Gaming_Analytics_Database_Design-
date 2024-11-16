package pm3.model;

/**
 * Represents a Consumable, a specific type of User applying to a program (Masters's or PhD).
 * This class adds program type and essay fields to the basic user details provided by the User superclass.
 */
public class ConsumableItem extends Item {
	protected int itemLevel;
    protected String itemDescription;

    // Constructor to initialize a Consumable with all fields, including itemID
    public ConsumableItem(int itemID, String itemName, int maxStackSize, double vendorPrice, boolean isSellable, int itemLevel, String itemDescription) {
        super(itemID, itemName, maxStackSize, vendorPrice, isSellable);
        this.itemLevel = itemLevel;
        this.itemDescription = itemDescription;
    }

    // Constructor to initialize a Consumable with only itemID
 	public ConsumableItem(int itemID) {
 		super(itemID);
 	}
 	
 	// Constructor to initialize a Consumable without a itemID
 	public ConsumableItem(String itemName, int maxStackSize, double vendorPrice, boolean isSellable, int itemLevel, String itemDescription) {
        super(itemName, maxStackSize, vendorPrice, isSellable);
        this.itemLevel = itemLevel;
        this.itemDescription = itemDescription;
    }

	/** Getters and setters. */
	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
}
