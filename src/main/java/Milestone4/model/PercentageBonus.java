package Milestone4.model;

/**
 * PercentageBonus extends AttributeBonus and represents a percentage bonus value for an item-attribute pair.
 */
public class PercentageBonus extends AttributeBonus {
    private double percentageBonusValue; // Between 0 and 1
    private int bonusCap;

    public PercentageBonus(Item item, Attribute attribute, double percentageBonusValue, int bonusCap) {
        super(item, attribute);
        setPercentageBonusValue(percentageBonusValue);
        this.bonusCap = bonusCap;
    }
    
    public PercentageBonus(Item item, Attribute attribute) {
        super(item, attribute);
    }

    /** Getters and Setters */

    public double getPercentageBonusValue() {
        return percentageBonusValue;
    }

    public void setPercentageBonusValue(double percentageBonusValue) {
        if (percentageBonusValue < 0 || percentageBonusValue > 1) {
            throw new IllegalArgumentException("PercentageBonusValue must be between 0 and 1.");
        }
        this.percentageBonusValue = percentageBonusValue;
    }

    public int getBonusCap() {
        return bonusCap;
    }

    public void setBonusCap(int bonusCap) {
        this.bonusCap = bonusCap;
    }
}
