package dal;
import model.PlayerAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerAccountDao {
    private Connection connection;
    
    public PlayerAccountDao(Connection connection) {
        this.connection = connection;
    }
    
    public PlayerAccount getPlayerByID(int accountID) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE accountID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
    
    public PlayerAccount getPlayerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM PlayerAccount WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    public List<PlayerAccount> getAllPlayers() throws SQLException {
        List<PlayerAccount> players = new ArrayList<>();
        String sql = "SELECT * FROM PlayerAccount";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(new PlayerAccount(
                    rs.getInt("accountID"),
                    rs.getString("userName"),
                    rs.getString("email"),
                    rs.getBoolean("isActive")
                ));
            }
        }
        return players;
    }

    public PlayerAccount create(PlayerAccount player) throws SQLException {
        if (getPlayerByUserName(player.getUserName()) != null) {
            throw new SQLException("Username already exists: " + player.getUserName());
        }
        if (getPlayerByEmail(player.getEmail()) != null) {
            throw new SQLException("Email already exists: " + player.getEmail());
        }
        
        String sql = "INSERT INTO PlayerAccount (userName, email, isActive) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, player.getUserName());
            stmt.setString(2, player.getEmail());
            stmt.setBoolean(3, player.isActive());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                player.setAccountID(rs.getInt(1));
            }
            return player;
        }
    }

    public boolean update(PlayerAccount player) throws SQLException {
        PlayerAccount existingWithUsername = getPlayerByUserName(player.getUserName());
        if (existingWithUsername != null && existingWithUsername.getAccountID() != player.getAccountID()) {
            throw new SQLException("Username already exists: " + player.getUserName());
        }
        
        PlayerAccount existingWithEmail = getPlayerByEmail(player.getEmail());
        if (existingWithEmail != null && existingWithEmail.getAccountID() != player.getAccountID()) {
            throw new SQLException("Email already exists: " + player.getEmail());
        }
        
        String sql = "UPDATE PlayerAccount SET userName = ?, email = ?, isActive = ? WHERE accountID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, player.getUserName());
            stmt.setString(2, player.getEmail());
            stmt.setBoolean(3, player.isActive());
            stmt.setInt(4, player.getAccountID());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean delete(int accountID) throws SQLException {
        if (getPlayerByID(accountID) == null) {
            return false;
        }
        
        String sql = "DELETE FROM PlayerAccount WHERE accountID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
           return stmt.executeUpdate() > 0;
        }
    }
}

