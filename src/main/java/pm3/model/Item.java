package pm3.model;

/**
 * Represents an item in the system with basic personal information.
 * The Item class serves as a base class for different types of items,
 * including Applicants, Reviewers, and Letter Writers.
 */

public class Item {
    protected int itemID;
    protected String itemName;
    protected int maxStackSize;
    protected double vendorPrice;
    protected boolean isSellable;

    // Constructor to initialize an item with all fields, including itemID.
    public Item(int itemID, String itemName, int maxStackSize, double vendorPrice, boolean isSellable) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.vendorPrice = vendorPrice;
        this.isSellable = isSellable;
    }

    // Constructor to initialize an item with only itemID.
 	public Item(int itemID) {
 		this.itemID = itemID;
 	}
 	
    // Constructor without itemID for creation
    public Item(String itemName, int maxStackSize, double vendorPrice, boolean isSellable) {
    	this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.vendorPrice = vendorPrice;
        this.isSellable = isSellable;
    }

	/** Getters and setters. */
	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}

	public double getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(double vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public boolean isSellable() {
		return isSellable;
	}

	public void setSellable(boolean isSellable) {
		this.isSellable = isSellable;
	}
}
