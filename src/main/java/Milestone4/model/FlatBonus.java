package Milestone4.model;

/**
 * FlatBonus extends AttributeBonus and represents a flat bonus value for an item-attribute pair.
 */

public class FlatBonus extends AttributeBonus {
    private int flatBonusValue;

    public FlatBonus(Item item, Attribute attribute, int flatBonusValue) {
        super(item, attribute);
        this.flatBonusValue = flatBonusValue;
    }
    
    public FlatBonus(Item item, Attribute attribute) {
        super(item, attribute);
    }

    /** Getters and Setters */

    public int getFlatBonusValue() {
        return flatBonusValue;
    }

    public void setFlatBonusValue(int flatBonusValue) {
        this.flatBonusValue = flatBonusValue;
    }
}
