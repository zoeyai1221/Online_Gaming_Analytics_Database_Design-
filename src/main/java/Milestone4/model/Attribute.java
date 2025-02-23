package Milestone4.model;

public class Attribute {
    private int attributeID;
    private String attributeName;
    private int attributeValue;
    
    public Attribute(int attributeID, String attributeName, int attributeValue) {
        this.attributeID = attributeID;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }
    
    public Attribute(String attributeName, int attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }
    
    public Attribute(int attributeID) {
    	this.attributeID = attributeID;
    }
    
    
    public int getAttributeID() {
        return attributeID;
    }
    
    public void setAttributeID(int attributeID) {
        if (attributeID <= 0) {
            throw new IllegalArgumentException("AttributeID must be positive");
        }
        this.attributeID = attributeID;
    }
    
    public String getAttributeName() {
        return attributeName;
    }
    
    public void setAttributeName(String attributeName) {
        if (attributeName == null || attributeName.trim().isEmpty()) {
            throw new IllegalArgumentException("AttributeName cannot be null or empty");
        }
        if (attributeName.length() > 255) {
            throw new IllegalArgumentException("AttributeName cannot be longer than 255 characters");
        }
        this.attributeName = attributeName;
    }
    
    public int getAttributeValue() {
        return attributeValue;
    }
    
    public void setAttributeValue(int attributeValue) {
        this.attributeValue = attributeValue;
    }
    @Override
    public String toString() {
        return String.format("Attribute: attributeName='%s', attributeValue=%d", attributeName, attributeValue);
    }

}