package Milestone4.dal;

import Milestone4.model.*;

import java.sql.*;

/**
 * Data access object (DAO) class to interact with the FlatBonus table in the database.
 * This is used to store and retrieve {@link FlatBonus} instances.
 */
public class FlatBonusDao extends AttributeBonusDao {
    protected ConnectionManager connectionManager;

    // Singleton pattern: Only one instance of FlatBonusDao is created.
    private static FlatBonusDao instance = null;

    protected FlatBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static FlatBonusDao getInstance() {
        if (instance == null) {
            instance = new FlatBonusDao();
        }
        return instance;
    }

    /**
     * Create a new FlatBonus in the database.
     */
    public FlatBonus create(FlatBonus flatBonus) throws SQLException {
        // Insert into the AttributeBonus superclass table first.
        super.create((AttributeBonus) flatBonus);

        String insertFlatBonus = "INSERT INTO FlatBonus (itemID, attributeID, flatBonusValue) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertFlatBonus);
            insertStmt.setInt(1, flatBonus.getItem().getItemID()); 
            insertStmt.setInt(2, flatBonus.getAttribute().getAttributeID());
            insertStmt.setInt(3, flatBonus.getFlatBonusValue());
            insertStmt.executeUpdate();
            return flatBonus;
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
     * Retrieve a FlatBonus by its related item and attribute.
     */
    public FlatBonus getFlatBonusByItemAndAttribute(Item item, Attribute attribute) throws SQLException {
        String selectFlatBonus = "SELECT flatBonusValue FROM FlatBonus WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFlatBonus);
            selectStmt.setInt(1, item.getItemID()); 
            selectStmt.setInt(2, attribute.getAttributeID());
            results = selectStmt.executeQuery();

            if (results.next()) {
                int flatBonusValue = results.getInt("flatBonusValue");
                return new FlatBonus(item, attribute, flatBonusValue); 
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
     * Update the flatBonusValue of a FlatBonus in the database.
     */
    public FlatBonus updateFlatBonusValue(FlatBonus flatBonus, int newFlatBonusValue) throws SQLException {
        String updateFlatBonus = "UPDATE FlatBonus SET flatBonusValue = ? WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateFlatBonus);
            updateStmt.setInt(1, newFlatBonusValue);
            updateStmt.setInt(2, flatBonus.getItem().getItemID()); 
            updateStmt.setInt(3, flatBonus.getAttribute().getAttributeID());
            updateStmt.executeUpdate();

            flatBonus.setFlatBonusValue(newFlatBonusValue);
            return flatBonus;
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
     * Delete a FlatBonus from the database.
     */
    public FlatBonus delete(FlatBonus flatBonus) throws SQLException {
        String deleteFlatBonus = "DELETE FROM FlatBonus WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteFlatBonus);
            deleteStmt.setInt(1, flatBonus.getItem().getItemID());
            deleteStmt.setInt(2, flatBonus.getAttribute().getAttributeID());
            deleteStmt.executeUpdate();

            super.delete(flatBonus);

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