package Milestone4.dal;
import Milestone4.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PlayerAccountDao {
    private static PlayerAccountDao instance = null;
    protected ConnectionManager connectionManager;

    public PlayerAccountDao() { 
        connectionManager = new ConnectionManager(); 
    }

    public static PlayerAccountDao getInstance() {
        if (instance == null) {
            instance = new PlayerAccountDao();
        }
        return instance;
    }

    
    public PlayerAccount create(PlayerAccount player) throws SQLException {
        String sql = "INSERT INTO PlayerAccount (userName, email, isActive) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 使用 connectionManager 获取连接
            stmt.setString(1, player.getUserName());
            stmt.setString(2, player.getEmail());
            stmt.setBoolean(3, player.getIsActive());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                player.setAccountID(rs.getInt(1));
            }
            return player;
        }
    }

    public PlayerAccount update(int accountID, String userName, String email, boolean isActive) throws SQLException {
        PlayerAccount existingWithUsername = getPlayerByUserName(userName);
        if (existingWithUsername != null && existingWithUsername.getAccountID() != accountID) {
            throw new SQLException("Username already exists: " + userName);
        }
        
        PlayerAccount existingWithEmail = getPlayerByEmail(email);
        if (existingWithEmail != null && existingWithEmail.getAccountID() != accountID) {
            throw new SQLException("Email already exists: " + email);
        }
        
        String sql = "UPDATE PlayerAccount SET userName = ?, email = ?, isActive = ? WHERE accountID = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) { 
            stmt.setString(1, userName);
            stmt.setString(2, email);
            stmt.setBoolean(3, isActive);
            stmt.setInt(4, accountID);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new PlayerAccount(accountID, userName, email, isActive);
            }
            return null;
        }
    }

    
    public PlayerAccount delete(int accountID) throws SQLException {
        PlayerAccount playerToDelete = getPlayerByID(accountID);
        if (playerToDelete == null) {
            return null;
        }
        
        String sql = "DELETE FROM PlayerAccount WHERE accountID = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) { 
            stmt.setInt(1, accountID);
            return stmt.executeUpdate() > 0 ? playerToDelete : null;
        }
    }
    
    public PlayerAccount getPlayerByID(int accountID) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE accountID = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PlayerAccount(
                    rs.getInt("accountID"),
                    rs.getString("userName"),
                    rs.getString("email"),
                    rs.getBoolean("isActive")
                );
            }
            return null;
        }
    }

    public PlayerAccount getPlayerByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE userName = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PlayerAccount(
                    rs.getInt("accountID"),
                    rs.getString("userName"),
                    rs.getString("email"),
                    rs.getBoolean("isActive")
                );
            }
            return null;
        }
    }

    public List<PlayerAccount> getPlayersWithPartialName(String userName) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE userName LIKE ?";
        List<PlayerAccount> playerAccounts = new ArrayList<PlayerAccount>();
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) { 
            stmt.setString(1, "%" + userName + "%"); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PlayerAccount cur = new PlayerAccount(
                    rs.getInt("accountID"),
                    rs.getString("userName"),
                    rs.getString("email"),
                    rs.getBoolean("isActive")
                );
                playerAccounts.add(cur);
            }
            
        }
        return playerAccounts;
    }
    
    public PlayerAccount getPlayerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE email = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) { 
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PlayerAccount(
                    rs.getInt("accountID"),
                    rs.getString("userName"),
                    rs.getString("email"),
                    rs.getBoolean("isActive")
                );
            }
            return null;
        }
    }
}