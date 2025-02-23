package Milestone4.model;

/**
 * AttributeBonus is the superclass representing a bonus associated with an item and an attribute.
 */
public class AttributeBonus {
    private Item item;
    private Attribute attribute;

    public AttributeBonus(Item item, Attribute attribute) {
        this.item = item;
        this.attribute = attribute;
    }
    
    /** Getters and Setters */
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

    
}
