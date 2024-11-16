package pm3.model;

/**
 * Represents a Weapon, a specific type of User applying to a program (Masters's or PhD).
 * This class adds program type and essay fields to the basic user details provided by the User superclass.
 */
public class WeaponItem extends Item {
    protected MainHandSlot mainHandSlot;
    protected int itemLevel;
    protected int requiredLevel;
    protected int damage;
    protected double autoAttack;
    protected double attackDelay;

    // Constructor to initialize a Weapon with all fields, including itemID
    public WeaponItem(int itemID, String itemName, int maxStackSize, double vendorPrice,
    		boolean isSellable, MainHandSlot mainHandSlot, int itemLevel, int requiredLevel,
    		int damage, double autoAttack, double attackDelay) {
        super(itemID, itemName, maxStackSize, vendorPrice, isSellable);
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
 	public WeaponItem(String itemName, int maxStackSize, double vendorPrice,
    		boolean isSellable, MainHandSlot mainHandSlot, int itemLevel, int requiredLevel,
    		int damage, double autoAttack, double attackDelay) {
        super(itemName, maxStackSize, vendorPrice, isSellable);
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

	public double getAutoAttack() {
		return autoAttack;
	}

	public void setAutoAttack(double autoAttack) {
		this.autoAttack = autoAttack;
	}

	public double getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(double attackDelay) {
		this.attackDelay = attackDelay;
	}	
}
