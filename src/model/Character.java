package model;

public class Character {
    private int characterID;
    private String firstName;
    private String lastName;
    private int playerID;
    
    public Character() {}
    
    public Character(int characterID, String firstName, String lastName, int playerID) {
        this.characterID = characterID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerID = playerID;
    }
    
    public int getCharacterID() {
        return characterID;
    }
    
    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("firstName cannot be null or empty");
        }
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("lastName cannot be null or empty");
        }
        this.lastName = lastName;
    }
    
    public int getPlayerID() {
        return playerID;
    }
    
    public void setPlayerID(int playerID) {
        if (playerID <= 0) {
            throw new IllegalArgumentException("playerID must be a positive number");
        }
        this.playerID = playerID;
    }
}
