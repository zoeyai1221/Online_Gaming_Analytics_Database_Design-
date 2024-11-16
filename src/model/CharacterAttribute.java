package model;

public class CharacterAttribute {
    private int characterID;
    private int attributeID;
    
    public CharacterAttribute() {}
    
    public CharacterAttribute(int characterID, int attributeID) {
        this.characterID = characterID;
        this.attributeID = attributeID;
    }
    
    public int getCharacterID() {
        return characterID;
    }
    
    public void setCharacterID(int characterID) {
        if (characterID <= 0) {
            throw new IllegalArgumentException("CharacterID must be positive");
        }
        this.characterID = characterID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterAttribute that = (CharacterAttribute) o;
        return characterID == that.characterID && attributeID == that.attributeID;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + characterID;
        result = 31 * result + attributeID;
        return result;
    }
}