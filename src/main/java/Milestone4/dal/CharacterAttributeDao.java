package Milestone4.dal;

import Milestone4.model.*;
import Milestone4.model.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the CharacterAttribute table in your database.
 * This is used to store and retrieve {@link CharacterAttribute} instances.
 */
public class CharacterAttributeDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static CharacterAttributeDao instance = null;

    protected CharacterAttributeDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterAttributeDao getInstance() {
        if (instance == null) {
            instance = new CharacterAttributeDao();
        }
        return instance;
    }

    /**
     * Create a new CharacterAttribute instance in the database.
     */
    public CharacterAttribute create(CharacterAttribute characterAttribute) throws SQLException {
        String insertCharacterAttribute = "INSERT INTO CharacterAttribute(characterID, attributeID) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterAttribute);
            insertStmt.setInt(1, characterAttribute.getCharacter().getCharacterID());
            insertStmt.setInt(2, characterAttribute.getAttribute().getAttributeID());
            insertStmt.executeUpdate();

            return characterAttribute;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    /**
     * Get the CharacterAttribute record by characterID and attributeID.
     */
    public CharacterAttribute getByIDs(Character character, Attribute attribute) throws SQLException {
        String selectCharacterAttribute = "SELECT characterID, attributeID FROM CharacterAttribute WHERE characterID=? AND attributeID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterAttribute);
            selectStmt.setInt(1, character.getCharacterID());
            selectStmt.setInt(2, attribute.getAttributeID());
            results = selectStmt.executeQuery();

            if (results.next()) {
                return new CharacterAttribute(character, attribute);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    /**
     * Get all CharacterAttribute records for a specific character.
     */
    public List<CharacterAttribute> getAttributesByCharacter(Character character) throws SQLException {
        List<CharacterAttribute> characterAttributes = new ArrayList<>();
        String selectAttributes = "SELECT characterID, attributeID FROM CharacterAttribute WHERE characterID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttributes);
            selectStmt.setInt(1, character.getCharacterID());
            results = selectStmt.executeQuery();

            AttributeDao attributeDao = AttributeDao.getInstance();

            while (results.next()) {
                int attributeID = results.getInt("attributeID");
                Attribute attribute = attributeDao.getAttributeByID(attributeID);
                CharacterAttribute characterAttribute = new CharacterAttribute(character, attribute);
                characterAttributes.add(characterAttribute);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return characterAttributes;
    }

    /**
     * Delete a CharacterAttribute instance from the database.
     */
    public CharacterAttribute delete(CharacterAttribute characterAttribute) throws SQLException {
        String deleteCharacterAttribute = "DELETE FROM CharacterAttribute WHERE characterID=? AND attributeID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacterAttribute);
            deleteStmt.setInt(1, characterAttribute.getCharacter().getCharacterID());
            deleteStmt.setInt(2, characterAttribute.getAttribute().getAttributeID());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
