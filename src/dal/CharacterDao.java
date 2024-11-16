package dal;
import model.Character;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDao {
    private Connection connection;
    private PlayerAccountDao playerAccountDao;
    
    public CharacterDao(Connection connection) {
        this.connection = connection;
        this.playerAccountDao = new PlayerAccountDao(connection);
    }
    
    public Character getCharacterByID(int characterID) throws SQLException {
        String sql = "SELECT * FROM `Character` WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Character(
                    rs.getInt("characterID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getInt("playerID")
                );
            }
            return null;
        }
    }

    public List<Character> getAllCharacters() throws SQLException {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM `Character`";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                characters.add(new Character(
                    rs.getInt("characterID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getInt("playerID")
                ));
            }
            return characters;
        }
    }
    
    public Character create(Character character) throws SQLException {
        if (playerAccountDao.getPlayerByID(character.getPlayerID()) == null) {
            throw new SQLException("Referenced PlayerAccount does not exist");
        }
        if (isNameCombinationExists(character.getFirstName(), character.getLastName())) {
            throw new SQLException("Character with this name combination already exists");
        }

        String sql = "INSERT INTO `Character` (firstName, lastName, playerID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, character.getFirstName());
            stmt.setString(2, character.getLastName());
            stmt.setInt(3, character.getPlayerID());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                character.setCharacterID(rs.getInt(1));
            }
            return character;
        }
    }
    
    public Character updateName(Character character, String newFirstName, String newLastName) throws SQLException {
        if (!character.getFirstName().equals(newFirstName) || !character.getLastName().equals(newLastName)) {
            if (isNameCombinationExists(newFirstName, newLastName)) {
                throw new SQLException("Character with this name combination already exists");
            }
        }
        
        String sql = "UPDATE `Character` SET firstName = ?, lastName = ? WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setInt(3, character.getCharacterID());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Character not found, no update performed");
            }
            
            character.setFirstName(newFirstName);
            character.setLastName(newLastName);
            return character;
        }
    }

    public boolean delete(Character character) throws SQLException {
        Character existingCharacter = getCharacterByID(character.getCharacterID());
        if (existingCharacter == null) {
            return false;  // Character doesn't exist
        }
        
        String sql = "DELETE FROM `Character` WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, character.getCharacterID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private boolean isNameCombinationExists(String firstName, String lastName) throws SQLException {
        String sql = "SELECT 1 FROM `Character` WHERE firstName = ? AND lastName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
