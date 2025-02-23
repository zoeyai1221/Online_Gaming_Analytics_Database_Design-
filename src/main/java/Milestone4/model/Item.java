package Milestone4.model;
/**
 * Represents an item in the system with basic personal information.
 * The Item class serves as a base class for different types of items,
 * including Applicants, Reviewers, and Letter Writers.
 */

public class Item {
    private int itemID;
    private String itemName;
    private int maxStackSize;
    private Double vendorPrice;
    private boolean isSellable;
    private ItemType itemType;
    
    public enum ItemType {
        WEAPON,
        GEAR,
        CONSUMABLE
    }

    // Constructor to initialize an item with all fields, including itemID.
    public Item(int itemID, String itemName, int maxStackSize, Double vendorPrice, boolean isSellable, ItemType itemType) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.vendorPrice = vendorPrice;
        this.isSellable = isSellable;
        this.itemType = itemType;
    }

    // Constructor to initialize an item with only itemID.
 	public Item(int itemID) {
 		this.itemID = itemID;
 	}
 	
    // Constructor without itemID for creation
    public Item(String itemName, int maxStackSize, Double vendorPrice, boolean isSellable, ItemType itemType) {
    	this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.vendorPrice = vendorPrice;
        this.isSellable = isSellable;
        this.itemType = itemType;
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

	public Double getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(Double vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public boolean isSellable() {
		return isSellable;
	}

	public void setSellable(boolean isSellable) {
		this.isSellable = isSellable;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

}
