package Milestone4.model;

public class CharacterAttribute {
	// reference data model 
    private Character character;
    private Attribute attribute;

	public CharacterAttribute(Character character, Attribute attribute) {
        this.character = character;
        this.attribute = attribute;
    }
    
    public int getCharacterID() {
        return character.getCharacterID();
    }
    
    public int getAttributeID() {
        return attribute.getAttributeID();
    }
    
    public Character getCharacter() {
        return character;
    }
    
    public Attribute getAttribute() {
        return attribute;
    }
    public void setCharacter(Character character) {
		this.character = character;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
}