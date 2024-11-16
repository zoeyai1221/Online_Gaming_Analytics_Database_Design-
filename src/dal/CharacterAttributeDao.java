package dal;
import model.CharacterAttribute;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterAttributeDao {
    private Connection connection;
    // For foreign key validation
    private CharacterDao characterDao;
    private AttributeDao attributeDao;  
    
    public CharacterAttributeDao(Connection connection) {
        this.connection = connection;
        this.characterDao = new CharacterDao(connection);
        this.attributeDao = new AttributeDao(connection);
    }
    
    public CharacterAttribute getByIDs(int characterID, int attributeID) throws SQLException {
        String sql = "SELECT * FROM CharacterAttribute WHERE characterID = ? AND attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterID);
            stmt.setInt(2, attributeID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CharacterAttribute(
                    rs.getInt("characterID"),
                    rs.getInt("attributeID")
                );
            }
            return null;
        }
    }
    
    public CharacterAttribute create(CharacterAttribute characterAttribute) throws SQLException {
        if (characterDao.getCharacterByID(characterAttribute.getCharacterID()) == null) {
            throw new SQLException("Referenced Character does not exist: " + characterAttribute.getCharacterID());
        }
        if (attributeDao.getAttributeByID(characterAttribute.getAttributeID()) == null) {
            throw new SQLException("Referenced Attribute does not exist: " + characterAttribute.getAttributeID());
        }
        if (getByIDs(characterAttribute.getCharacterID(), characterAttribute.getAttributeID()) != null) {
            throw new SQLException("Character-Attribute combination already exists");
        }

        String sql = "INSERT INTO CharacterAttribute (characterID, attributeID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterAttribute.getCharacterID());
            stmt.setInt(2, characterAttribute.getAttributeID());
            stmt.executeUpdate();
            return characterAttribute;
        }
    }

    public List<CharacterAttribute> getAttributesByCharacter(int characterID) throws SQLException {
        List<CharacterAttribute> attributes = new ArrayList<>();
        String sql = "SELECT * FROM CharacterAttribute WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attributes.add(new CharacterAttribute(
                    rs.getInt("characterID"),
                    rs.getInt("attributeID")
                ));
            }
        }
        return attributes;
    }

    public List<CharacterAttribute> getCharactersByAttribute(int attributeID) throws SQLException {
        List<CharacterAttribute> characters = new ArrayList<>();
        String sql = "SELECT * FROM CharacterAttribute WHERE attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attributeID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                characters.add(new CharacterAttribute(
                    rs.getInt("characterID"),
                    rs.getInt("attributeID")
                ));
            }
        }
        return characters;
    }

    public void delete(CharacterAttribute characterAttribute) throws SQLException {
        if (getByIDs(characterAttribute.getCharacterID(), characterAttribute.getAttributeID()) == null) {
            throw new SQLException("Character-Attribute combination does not exist");
        }
        
        String sql = "DELETE FROM CharacterAttribute WHERE characterID = ? AND attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterAttribute.getCharacterID());
            stmt.setInt(2, characterAttribute.getAttributeID());
            stmt.executeUpdate();
        }
    }
}
