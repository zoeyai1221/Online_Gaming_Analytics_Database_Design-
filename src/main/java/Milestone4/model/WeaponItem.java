package Milestone4.model;

/**
 * Represents a Weapon, a specific type of User applying to a program (Masters's or PhD).
 * This class adds program type and essay fields to the basic user details provided by the User superclass.
 */
public class WeaponItem extends Item {
	private MainHandSlot mainHandSlot;
	private int itemLevel;
	private int requiredLevel;
	private int damage;
	private Double autoAttack;
	private Double attackDelay;

    // Constructor to initialize a Weapon with all fields, including itemID
    public WeaponItem(int itemID, String itemName, int maxStackSize, Double vendorPrice,
    		boolean isSellable, MainHandSlot mainHandSlot, int itemLevel, int requiredLevel,
    		int damage, Double autoAttack, Double attackDelay, ItemType itemType) {
        super(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
        this.mainHandSlot = mainHandSlot;
        this.itemLevel = itemLevel;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.autoAttack = autoAttack;
        this.attackDelay = attackDelay;
    }

    // Constructor to initialize a Weapon with only itemID
 	public WeaponItem(int itemID) {
 		super(itemID);
 	}
 	
 	// Constructor to initialize a Weapon without a itemID
 	public WeaponItem(String itemName, int maxStackSize, Double vendorPrice,
    		boolean isSellable, MainHandSlot mainHandSlot, int itemLevel, int requiredLevel,
    		int damage, Double autoAttack, Double attackDelay, ItemType itemType) {
        super(itemName, maxStackSize, vendorPrice, isSellable, itemType);
        this.mainHandSlot = mainHandSlot;
        this.itemLevel = itemLevel;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.autoAttack = autoAttack;
        this.attackDelay = attackDelay;
    }

	/** Getters and setters. */
	public MainHandSlot getMainHandSlot() {
		return mainHandSlot;
	}

	public void setMainHandSlot(MainHandSlot mainHandSlot) {
		this.mainHandSlot = mainHandSlot;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Double getAutoAttack() {
		return autoAttack;
	}

	public void setAutoAttack(Double autoAttack) {
		this.autoAttack = autoAttack;
	}

	public Double getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(Double attackDelay) {
		this.attackDelay = attackDelay;
	}	
}
