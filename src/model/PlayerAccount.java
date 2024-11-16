package model;

public class PlayerAccount {
    private int accountID;
    private String userName;
    private String email;
    private boolean isActive;
    
    public PlayerAccount() {}

    public PlayerAccount(int accountID, String userName, String email, boolean isActive) {
        this.accountID = accountID;
        this.userName = userName;
        this.email = email;
        this.isActive = isActive;
    }

    public PlayerAccount(String userName, String email, boolean isActive) {
        this.setUserName(userName);
        this.setEmail(email);
        this.setActive(isActive);
    }

    public int getAccountID() {
        return accountID;
    }
    
    public void setAccountID(int accountID) {
        if (accountID <= 0) {
            throw new IllegalArgumentException("AccountID must be positive");
        }
        this.accountID = accountID;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (userName.length() > 255) {
            throw new IllegalArgumentException("Username cannot be longer than 255 characters");
        }
        this.userName = userName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (email.length() > 255) {
            throw new IllegalArgumentException("Email cannot be longer than 255 characters");
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
}