package Milestone4.model;

public class Character {
    private int characterID;
    private String firstName;
    private String lastName;
    private PlayerAccount playerAccount;
    
    public Character(int characterID, String firstName, String lastName, PlayerAccount playerAccount) {
        this.characterID = characterID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerAccount = playerAccount;
    }
    
    public Character(String firstName, String lastName, PlayerAccount playerAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerAccount = playerAccount;
    }
    
    public Character(int characterID) {
        this.characterID = characterID;
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
    
    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }
    
    public void setPlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount == null) {
            throw new IllegalArgumentException("playerAccount cannot be null");
        }
        this.playerAccount = playerAccount;
    }
    @Override
    public String toString() {
        return String.format("Character: firstName='%s', lastName='%s', playerAccount=%s", firstName, lastName, playerAccount.getUserName());
    }

}
