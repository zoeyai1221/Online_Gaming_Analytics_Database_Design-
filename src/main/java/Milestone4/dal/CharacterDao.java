package Milestone4.dal;

import Milestone4.model.Character;
import Milestone4.model.PlayerAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CharacterDao {
    protected ConnectionManager connectionManager;
    private static CharacterDao instance = null;

    protected CharacterDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterDao getInstance() {
        if (instance == null) {
            instance = new CharacterDao();
        }
        return instance;
    }

    /**
     * Create a new Character instance in the database.
     */
    public Character create(Character character) throws SQLException {
        String insertCharacter = "INSERT INTO `Character` (firstName, lastName, playerID) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacter, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, character.getFirstName());
            insertStmt.setString(2, character.getLastName());
            insertStmt.setInt(3, character.getPlayerAccount().getAccountID());
            insertStmt.executeUpdate();

            try (ResultSet resultSet = insertStmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    character.setCharacterID(resultSet.getInt(1));
                    return character;
                } else {
                    throw new SQLException("Creating character failed, no ID obtained.");
                }
            }
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
     * Update the name of an existing Character in the database.
     */
    public Character updateName(Character character, String newFirstName, String newLastName) throws SQLException {
        String updateCharacter = "UPDATE `Character` SET firstName = ?, lastName = ? WHERE characterID = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateCharacter);
            updateStmt.setString(1, newFirstName);
            updateStmt.setString(2, newLastName);
            updateStmt.setInt(3, character.getCharacterID());
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Character not found, no update performed.");
            }

            character.setFirstName(newFirstName);
            character.setLastName(newLastName);
            return character;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    /**
     * Delete a Character instance from the database.
     */
    public Character delete(Character character) throws SQLException {
        String deleteCharacter = "DELETE FROM `Character` WHERE characterID = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacter);
            deleteStmt.setInt(1, character.getCharacterID());
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

    /**
     * Retrieve a Character by its ID.
     */
    public Character getCharacterByID(int characterID) throws SQLException {
        String selectCharacter = "SELECT c.*, p.* FROM `Character` c " +
                "JOIN PlayerAccount p ON c.playerID = p.accountID " +
                "WHERE c.characterID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacter);
            selectStmt.setInt(1, characterID);
            results = selectStmt.executeQuery();

            if (results.next()) {
                PlayerAccount playerAccount = new PlayerAccount(
                        results.getInt("accountID"),
                        results.getString("userName"),
                        results.getString("email"),
                        results.getBoolean("isActive")
                );

                return new Character(
                        results.getInt("characterID"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        playerAccount
                );
            }
            return null;
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
    }

    /**
     * Retrieve all Characters associated with a specific PlayerAccount.
     */
    public List<Character> getCharacterByPlayerID(PlayerAccount playerAccount) throws SQLException {
        String selectCharacters = "SELECT c.*, p.* FROM `Character` c " +
                "JOIN PlayerAccount p ON c.playerID = p.accountID " +
                "WHERE c.playerID = ?;";
        List<Character> characters = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacters);
            selectStmt.setInt(1, playerAccount.getAccountID());
            results = selectStmt.executeQuery();

            while (results.next()) {
                PlayerAccount player = new PlayerAccount(
                        results.getInt("accountID"),
                        results.getString("userName"),
                        results.getString("email"),
                        results.getBoolean("isActive")
                );

                Character character = new Character(
                        results.getInt("characterID"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        player
                );
                characters.add(character);
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
        return characters;
    }

    /**
     * Retrieve a Character by both first name and last name.
     */
    public Character getCharacterByName(String firstName, String lastName) throws SQLException {
        String selectCharacter = "SELECT c.*, p.* FROM `Character` c " +
                "JOIN PlayerAccount p ON c.playerID = p.accountID " +
                "WHERE c.firstName = ? AND c.lastName = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacter);
            selectStmt.setString(1, firstName);
            selectStmt.setString(2, lastName);
            results = selectStmt.executeQuery();

            if (results.next()) {
                PlayerAccount playerAccount = new PlayerAccount(
                        results.getInt("accountID"),
                        results.getString("userName"),
                        results.getString("email"),
                        results.getBoolean("isActive")
                );

                return new Character(
                        results.getInt("characterID"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        playerAccount
                );
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
}